package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.Fx
import com.lehaine.game.GameProcess
import com.lehaine.game.rune.engine.BaseScene
import com.lehaine.game.rune.engine.GameCamera
import com.lehaine.game.rune.engine.addTmodUpdater
import com.lehaine.game.rune.engine.node.fixedUpdater
import com.lehaine.game.rune.engine.postUpdate
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graph.node.component.HAlign
import com.lehaine.littlekt.graph.node.component.VAlign
import com.lehaine.littlekt.graph.node.node
import com.lehaine.littlekt.graph.node.node2d.ui.Control
import com.lehaine.littlekt.graph.node.node2d.ui.control
import com.lehaine.littlekt.graph.node.node2d.ui.label
import com.lehaine.littlekt.graph.sceneGraph
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.graphics.use
import com.lehaine.littlekt.util.viewport.ExtendViewport


class GameScene(private val batch: SpriteBatch, context: Context) : BaseScene(context) {
    val camera = GameCamera(GameProcess.VIRTUAL_WIDTH, GameProcess.VIRTUAL_HEIGHT).apply {
        viewport = ExtendViewport(GameProcess.VIRTUAL_WIDTH, GameProcess.VIRTUAL_HEIGHT)
    }
    val background: Node
    val fxBackground: Node
    val main: Node
    val foreground: Node
    val fxForeground: Node
    val top: Node
    val ui: Control

    private val sceneGraph =
        sceneGraph(context, ExtendViewport(GameProcess.VIRTUAL_WIDTH, GameProcess.VIRTUAL_HEIGHT), batch) {
            fixedUpdater {
                timesPerSecond = 30

                background = node {
                    name = "Background"
                }

                fxBackground = node {
                    name = "FX Background"
                }

                main = node {
                    name = "Main"
                }

                foreground = node {
                    name = "Foreground"
                }

                fxForeground = node {
                    name = "FX Foreground"
                }

                top = node {
                    name = "Top"
                }
            }

            ui = control {
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

    val fx = Fx(this)

    init {
        addTmodUpdater(60) { dt, tmod ->
            fx.update(dt, tmod)

            camera.update(dt)
            camera.viewport.apply(context)
            batch.use(camera.viewProjection) {
               // render
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