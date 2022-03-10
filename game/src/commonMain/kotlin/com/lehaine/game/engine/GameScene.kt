package com.lehaine.game.engine

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.Scene
import com.lehaine.littlekt.graphics.gl.ClearBufferMask
import kotlin.time.Duration


open class GameScene(context: Context) : Scene(context) {
    val updateComponents = mutableListOf<UpdateComponent>()

    override suspend fun Context.render(dt: Duration) {
        gl.clear(ClearBufferMask.COLOR_DEPTH_BUFFER_BIT)
        updateComponents.forEach {
            it.update(dt)
        }
    }
}

fun <T : GameScene> T.addFixedInterpUpdater(
    timesPerSecond: Float,
    initial: Boolean = true,
    interpolate: (ratio: Float) -> Unit,
    updatable: T.() -> Unit
) = createFixedInterpUpdater(timesPerSecond, initial, interpolate, updatable).also { updateComponents += it }

fun <T : GameScene> T.addFixedInterpUpdater(
    time: Duration,
    initial: Boolean = true,
    interpolate: (ratio: Float) -> Unit,
    updatable: T.() -> Unit
) = createFixedInterpUpdater(time, initial, interpolate, updatable).also { updateComponents += it }


fun <T : GameScene> T.addTmodUpdater(
    targetFPS: Int,
    updatable: T.(dt: Duration, tmod: Float) -> Unit
) = createTmodUpdater(targetFPS, updatable).also { updateComponents += it }