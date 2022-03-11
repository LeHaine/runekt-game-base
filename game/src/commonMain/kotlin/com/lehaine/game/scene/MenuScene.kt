package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.GameProcess
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.Scene
import com.lehaine.littlekt.async.KtScope
import com.lehaine.littlekt.graph.node.component.HAlign
import com.lehaine.littlekt.graph.node.node2d.ui.button
import com.lehaine.littlekt.graph.node.node2d.ui.centerContainer
import com.lehaine.littlekt.graph.node.node2d.ui.label
import com.lehaine.littlekt.graph.node.node2d.ui.vBoxContainer
import com.lehaine.littlekt.graph.sceneGraph
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.util.viewport.ExtendViewport
import kotlinx.coroutines.launch
import kotlin.time.Duration


class MenuScene(
    private val game: GameProcess,
    private val batch: SpriteBatch,
    context: Context
) : Scene(context) {
    private val graph = sceneGraph(context, ExtendViewport(GameProcess.VIRTUAL_WIDTH, GameProcess.VIRTUAL_HEIGHT), batch) {
        centerContainer {
            anchorRight = 1f
            anchorBottom = 1f

            vBoxContainer {
                separation = 10

                label {
                    text = "Main Menu"
                    font = Assets.pixelFont
                    horizontalAlign = HAlign.CENTER
                    fontScaleX = 2f
                    fontScaleY = 2f
                }

                button {
                    var startingGame = false
                    text = "Start Game"

                    onPressed += {
                        if (!startingGame) {
                            startingGame = true
                            KtScope.launch {
                                game.addScene(GameScene(this@MenuScene.batch, context))
                                game.setScene<GameScene>()
                                game.removeScene<MenuScene>()
                            }
                        }
                    }
                }

                button {
                    text = "Settings"
                }
            }
        }
    }.also { it.initialize() }

    override suspend fun Context.resize(width: Int, height: Int) {
        graph.resize(width, height, true)
    }

    override suspend fun Context.render(dt: Duration) {
        graph.update(dt)
        graph.render()
    }

    override suspend fun Context.hide() {
        dispose()
    }
}