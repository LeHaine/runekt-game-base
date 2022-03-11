package com.lehaine.game

import com.lehaine.game.scene.MenuScene
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.Game
import com.lehaine.littlekt.Scene
import com.lehaine.littlekt.async.KtScope
import com.lehaine.littlekt.graphics.Color
import com.lehaine.littlekt.graphics.SpriteBatch
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.log.Logger
import kotlinx.coroutines.launch

class GameProcess(context: Context) : Game<Scene>(context) {

    private val batch = SpriteBatch(context)

    init {
        Logger.setLevels(Logger.Level.DEBUG)
    }

    override suspend fun Context.start() {
        Assets.createInstance(this) {
            KtScope.launch {
                addScene(MenuScene(this@GameProcess, batch, context))
                setScene<MenuScene>()
            }
        }

        onRender {
            gl.clearColor(Color.DARK_GRAY)
            gl.clear(ClearBufferMask.COLOR_DEPTH_BUFFER_BIT)

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
            Assets.dispose()
        }

        setSceneCallbacks(this)
    }

    companion object {
        val VIRTUAL_WIDTH = 480
        val VIRTUAL_HEIGHT = 270
    }
}