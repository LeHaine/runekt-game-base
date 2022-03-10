package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.Game
import com.lehaine.game.engine.GameScene
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graph.node.node2d.ui.label
import com.lehaine.littlekt.graph.sceneGraph
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.util.viewport.ExtendViewport
import kotlin.time.Duration

/**
 * @author Colton Daily
 * @date 3/9/2022
 */
class MenuScene(private val batch: SpriteBatch, context: Context) : GameScene(context) {
    private val graph = sceneGraph(context, ExtendViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch) {
        label {
            text = "Main Menu"
            font = Assets.pixelFont
        }
    }.also { it.initialize() }

    override suspend fun Context.resize(width: Int, height: Int) {
        graph.resize(width, height, true)
    }

    override suspend fun Context.render(dt: Duration) {
        graph.update(dt)
        graph.render()
    }
}