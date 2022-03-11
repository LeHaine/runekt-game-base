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
        SpriteBatch(context, 8196),
        UiInputSignals(),
        InputMapController(context.input)
    ) {


    private val renderers = mutableListOf<Renderer>()


    override fun initialize() {
        if (renderers.isEmpty()) {
            renderers.add(DefaultRenderer().also { it.onAddedToScene(this) })
        }
        super.initialize()
    }

    override fun  render() {
        renderers.forEach {
            it.render(this)
        }
    }

    override fun dispose() {
        renderers.forEach {
            it.dispose()
        }
        super.dispose()
    }
}