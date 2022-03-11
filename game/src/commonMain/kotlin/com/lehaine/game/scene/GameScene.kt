package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.Fx
import com.lehaine.game.Game
import com.lehaine.game.engine.BaseScene
import com.lehaine.game.engine.GameCamera
import com.lehaine.game.engine.addTmodUpdater
import com.lehaine.game.engine.nodes.entity
import com.lehaine.game.engine.nodes.fixedUpdater
import com.lehaine.game.engine.postUpdate
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graph.node.component.HAlign
import com.lehaine.littlekt.graph.node.component.VAlign
import com.lehaine.littlekt.graph.node.node2d.ui.control
import com.lehaine.littlekt.graph.node.node2d.ui.label
import com.lehaine.littlekt.graph.sceneGraph
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.graphics.TextureAtlas
import com.lehaine.littlekt.graphics.use
import com.lehaine.littlekt.util.viewport.ExtendViewport


class GameScene(private val batch: SpriteBatch, context: Context) : BaseScene(context) {
    private val atlas: TextureAtlas get() = Assets.atlas

    private val camera = GameCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT).apply {
        viewport = ExtendViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT)
    }
    private val fx = Fx(Assets.atlas)
    private val sceneGraph = sceneGraph(context, ExtendViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT), batch) {
        fixedUpdater {
            timesPerSecond = 30

            entity(16) {
                onFixedUpdate += {
                    // ..
                }
            }
        }

        control {
            name = "UI"
            anchorRight = 1f
            anchorBottom = 1f

            label {
                text = "TODO: Implement game logic"
                font = Assets.pixelFont
                anchorRight = 1f
                anchorBottom = 1f
                verticalAlign = VAlign.CENTER
                horizontalAlign = HAlign.CENTER
            }
        }
    }.also { it.initialize() }

    init {
        addTmodUpdater(60) { dt, tmod ->
            fx.update(dt, tmod)

            camera.update(dt)
            camera.viewport.apply(context)
            batch.use(camera.viewProjection) {
                fx.render(it)
            }

            sceneGraph.update(dt)
            sceneGraph.postUpdate(dt)
            sceneGraph.render()
        }

    }

    override suspend fun Context.show() {
        initLevel()
    }

    override suspend fun Context.hide() {
        updateComponents.clear()
    }

    override suspend fun Context.resize(width: Int, height: Int) {
        camera.update(width, height, this)
        sceneGraph.resize(width, height, true)
    }

    private fun initLevel() {
        // instantiate entities and setup level here
    }
}