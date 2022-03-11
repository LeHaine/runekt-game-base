package com.lehaine.game.rune.engine

import com.lehaine.game.rune.engine.render.DefaultRenderer
import com.lehaine.game.rune.engine.render.Renderer
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.input.InputMapController
import com.lehaine.littlekt.util.viewport.Viewport

/**
 * @author Colton Daily
 */
open class RuneScene(context: Context, viewport: Viewport) :
    SceneGraph<String>(
        context,
        viewport,
        SpriteBatch(context, 8191),
        UiInputSignals(),
        InputMapController(context.input)
    ) {

    var rune: Rune? = null

    private val renderers = mutableListOf<Renderer>()

    override fun initialize() {
        if (renderers.isEmpty()) {
            renderers.add(DefaultRenderer(context).also { it.onAddedToScene(this) })
        }
        super.initialize()
    }

    override fun render() {
        renderers.forEach {
            it.render(batch, this)
        }
    }

    fun addRenderer(renderer: Renderer) {
        renderers += renderer
        renderer.onAddedToScene(this)
    }

    fun removeRenderer(renderer: Renderer) {
        renderers -= renderer
        renderer.dispose()
    }

    fun changeTo(scene: RuneScene) {
        val rune = rune
        check(rune != null) { "This scenes `Rune` property is null. This is most likely because the scene isn't set!" }
        rune.scene = scene
    }

    override fun dispose() {
        renderers.forEach {
            it.dispose()
        }
        super.dispose()
    }
}