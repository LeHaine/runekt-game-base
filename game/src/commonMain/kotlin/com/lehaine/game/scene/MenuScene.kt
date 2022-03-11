package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.GameCore
import com.lehaine.game.rune.engine.RuneScene
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.async.KtScope
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graph.node.component.HAlign
import com.lehaine.littlekt.graph.node.node2d.ui.button
import com.lehaine.littlekt.graph.node.node2d.ui.centerContainer
import com.lehaine.littlekt.graph.node.node2d.ui.label
import com.lehaine.littlekt.graph.node.node2d.ui.vBoxContainer
import com.lehaine.littlekt.util.viewport.ExtendViewport
import kotlinx.coroutines.launch


class MenuScene(
    context: Context
) : RuneScene(context, ExtendViewport(GameCore.VIRTUAL_WIDTH, GameCore.VIRTUAL_HEIGHT)) {

    override fun Node.initialize() {
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
                            changeTo(GameScene(context))
                        }
                    }
                }

                button {
                    text = "Settings"
                }
            }
        }
    }
}