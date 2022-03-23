package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.Fx
import com.lehaine.game.GameCore
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graph.node.canvasLayer
import com.lehaine.littlekt.graph.node.component.HAlign
import com.lehaine.littlekt.graph.node.component.VAlign
import com.lehaine.littlekt.graph.node.node
import com.lehaine.littlekt.graph.node.ui.Control
import com.lehaine.littlekt.graph.node.ui.label
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.util.viewport.ExtendViewport
import com.lehaine.rune.engine.RuneScene
import com.lehaine.rune.engine.node.EntityCamera2D
import com.lehaine.rune.engine.node.entityCamera2D
import com.lehaine.rune.engine.node.pixelPerfectSlice
import com.lehaine.rune.engine.node.pixelSmoothFrameBuffer
import com.lehaine.rune.engine.node.renderable.ldtkLevel
import kotlin.time.Duration


class GameScene(context: Context) :
    RuneScene(context, ExtendViewport(GameCore.VIRTUAL_WIDTH, GameCore.VIRTUAL_HEIGHT)) {
    val background: Node = Node()
    val fxBackground: Node = Node()
    val main: Node = Node()
    val foreground: Node = Node()
    val fxForeground: Node = Node()
    val top: Node = Node()
    val ui: Control = Control()

    val fx = Fx(this)

    override suspend fun Node.initialize() {
        canvasLayer {
            node(background) {
                name = "Background"
            }

            node(fxBackground) {
                name = "FX Background"
            }

            node(main) {
                name = "Main"
                val entityCamera: EntityCamera2D

                val fbo = pixelSmoothFrameBuffer {
                    // TODO load level here

                    // TODO load player here
                    entityCamera = entityCamera2D {
                        camera = fboCamera

                        onUpdate += {
                            if (input.isKeyJustPressed(Key.Z)) {
                                targetZoom = 0.5f
                            }
                            if (input.isKeyJustPressed(Key.X)) {
                                targetZoom = 1f
                            }
                        }
                    }
                }

                pixelPerfectSlice {
                    this.fbo = fbo
                    onUpdate += {
                        scaledDistX = entityCamera.scaledDistX
                        scaledDistY = entityCamera.scaledDistY
                    }
                }
            }

            node(foreground) {
                name = "Foreground"
            }

            node(fxForeground) {
                name = "FX Foreground"
            }

            node(top) {
                name = "Top"
            }

        }

        node(ui) {
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
    }

    override fun update(dt: Duration) {
        fx.update(dt)
        super.update(dt)
    }
}