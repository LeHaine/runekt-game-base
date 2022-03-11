package com.lehaine.game.rune.engine.render

import com.lehaine.game.rune.engine.BlendMode
import com.lehaine.game.rune.engine.RuneScene
import com.lehaine.game.rune.engine.node.Renderable2D
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.Disposable
import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graphics.Batch
import com.lehaine.littlekt.graphics.Camera
import com.lehaine.littlekt.graphics.Color
import com.lehaine.littlekt.graphics.GL
import com.lehaine.littlekt.graphics.gl.BlendEquationMode
import com.lehaine.littlekt.graphics.gl.BlendFactor
import com.lehaine.littlekt.graphics.gl.ClearBufferMask

/**
 * @author Colton Daily
 */
abstract class Renderer(protected val context: Context) : Disposable {
    protected val gl: GL get() = context.gl

    var clearColor: Color = Color.CLEAR
    var blendMode = BlendMode.Alpha
    protected var currentBlendMode: BlendMode? = null

    /**
     * Called when the [Renderer] is added to the [RuneScene]
     * @param scene the scene which this renderer was added to
     */
    open fun onAddedToScene(scene: RuneScene) = Unit

    /**
     * Called when a [RuneScene] is ended or this [Renderer] is removed from the [RuneScene]. Use this for cleaning up.
     */
    override fun dispose() = Unit

    protected fun begin(batch: Batch, camera: Camera) {
        currentBlendMode = blendMode
        gl.clearColor(clearColor)
        gl.clear(ClearBufferMask.COLOR_BUFFER_BIT)
        currentBlendMode?.setBlendFunctions(batch)
        batch.begin(camera.viewProjection)
    }

    private fun BlendMode.setBlendFunctions(batch: Batch) {
        gl.blendEquationSeparate(colorBlendFunction, alphaBlendFunction)
        batch.setBlendFunctionSeparate(colorSourceBlend, colorDestinationBlend, alphaSourceBlend, alphaDestinationBlend)
    }

    abstract fun render(batch: Batch, scene: SceneGraph<*>)

    protected fun renderAfterStateCheck(renderable2D: Renderable2D, batch: Batch) {
        if (renderable2D.blendMode != blendMode) {
            currentBlendMode = renderable2D.blendMode
            flush(batch)
        }
    }

    fun flush(batch: Batch) {
        batch.end()
        currentBlendMode?.setBlendFunctions(batch)
        batch.begin()
    }

    fun end(batch: Batch) {
        batch.end()
        gl.blendEquation(BlendEquationMode.FUNC_ADD)
        batch.setBlendFunctionSeparate(
            BlendFactor.SRC_ALPHA,
            BlendFactor.ONE_MINUS_SRC_ALPHA,
            BlendFactor.SRC_ALPHA,
            BlendFactor.ONE_MINUS_SRC_ALPHA
        )
    }
}