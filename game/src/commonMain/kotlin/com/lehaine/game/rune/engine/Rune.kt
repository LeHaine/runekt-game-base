package com.lehaine.game.rune.engine

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.ContextListener

/**
 * @author Colton Daily
 * @date 3/10/2022
 */
open class Rune(context: Context) : ContextListener(context) {

    /**
     * The internally handled scene
     */
    private var _scene: RuneScene? = null

    /**
     * The currently active [RuneScene].
     * Note: if set, the [RuneScene] will not actually change until the end of the [render]
     */
    var scene: RuneScene?
        get() = _scene
        set(value) {
            check(value != null) { "Scene can not be set to null!" }
            if (_scene == null) {
                _scene = value
                _scene?.apply {
                    root.initialize()
                }
                onSceneChanged()
                value.begin()
            } else {
                nextScene = value
            }
        }

    private var nextScene: RuneScene? = null

    override suspend fun Context.start() {

    }
    /**
     * Called after a [RuneScene] ends, before the next [RuneScene] begins.
     */
    open fun onSceneChanged() = Unit
}