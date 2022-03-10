package com.lehaine.game.scene

import com.lehaine.game.Assets
import com.lehaine.game.Game
import com.lehaine.game.engine.BaseScene
import com.lehaine.game.engine.GameCamera
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.graphics.use
import com.lehaine.littlekt.util.viewport.ExtendViewport
import kotlin.time.Duration


class GameScene(private val batch: SpriteBatch, context: Context) : BaseScene(context) {
    private val camera = GameCamera(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT).apply {
        viewport = ExtendViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT)
    }

    override suspend fun Context.resize(width: Int, height: Int) {
        camera.update(width, height, this)
    }

    override suspend fun Context.render(dt: Duration) {
        batch.use(camera.viewProjection) {
            Assets.pixelFont.draw(it, "Hello Game Scene", 50f, 50f)
        }
    }
}