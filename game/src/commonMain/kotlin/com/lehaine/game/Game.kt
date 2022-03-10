package com.lehaine.game

import com.lehaine.game.engine.GameScene
import com.lehaine.game.scene.MenuScene
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.Game
import com.lehaine.littlekt.async.KtScope
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.log.Logger
import kotlinx.coroutines.launch

class Game(context: Context) : Game<GameScene>(context) {

    private val batch = SpriteBatch(context)

    init {
        Logger.setLevels(Logger.Level.DEBUG)
    }

    override suspend fun Context.start() {
        setSceneCallbacks(this)
        Assets.createInstance(this) {
            KtScope.launch {
                addScene(MenuScene(batch, context))
                setScene<MenuScene>()
            }
        }

        onRender {
            if (input.isKeyJustPressed(Key.ESCAPE)) {
                close()
            }
        }

        onPostRender {
            if (input.isKeyJustPressed(Key.P)) {
                logger.info { stats }
            }
        }

        onDispose {
            batch.dispose()
        }
    }

    companion object {
        val VIRTUAL_WIDTH = 480
        val VIRTUAL_HEIGHT = 270
    }
}