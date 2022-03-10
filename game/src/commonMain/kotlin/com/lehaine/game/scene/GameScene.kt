package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.Fx
import com.lehaine.game.Game
import com.lehaine.game.engine.*
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.graphics.TextureAtlas
import com.lehaine.littlekt.graphics.use
import com.lehaine.littlekt.util.fastForEach
import com.lehaine.littlekt.util.viewport.ExtendViewport


class GameScene(private val batch: SpriteBatch, context: Context) : BaseScene(context) {
    private val entities = mutableListOf<Entity>()
    private val atlas: TextureAtlas get() = Assets.atlas

    private val camera = GameCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT).apply {
        viewport = ExtendViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT)
    }
    private val fx = Fx(Assets.atlas)

    private var fixedProgressionRatio = 1f

    init {
        addTmodUpdater(60) { dt, tmod ->
            fx.update(dt, tmod)

            entities.fastForEach {
                it.fixedProgressionRatio = fixedProgressionRatio
                it.update(dt)
            }

            entities.fastForEach {
                it.postUpdate(dt)
            }

            camera.update(dt)
            camera.viewport.apply(context)
            batch.use(camera.viewProjection) {
                fx.render(it)
                entities.fastForEach { entity ->
                    entity.render(it)
                }

                Assets.pixelFont.draw(it, "TODO: Implement game logic", 0f, 0f)
            }
        }

        addFixedInterpUpdater(
            30f,
            interpolate = { ratio -> fixedProgressionRatio = ratio },
            updatable = { entities.fastForEach { it.fixedUpdate() } }
        )
    }

    override suspend fun Context.show() {
        initLevel()
    }

    override suspend fun Context.hide() {
        updateComponents.clear()
        entities.fastForEach {
            it.destroy()
        }
        entities.clear()
    }


    override suspend fun Context.resize(width: Int, height: Int) {
        camera.update(width, height, this)
    }

    private fun initLevel() {
        // instantiate entities and setup level here
    }
}