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
                    rune = this@Rune
                }
                onSceneChanged()
                value.initialize()
                value.resize(context.graphics.width, context.graphics.height, true)
            } else {
                nextScene = value
            }
        }

    private var nextScene: RuneScene? = null

    final override suspend fun Context.start() {
        create()
        onResize { width, height ->
            scene?.resize(width, height, true)
        }

        onRender { dt ->
            scene?.let { _scene ->

                _scene.update(dt)

                nextScene?.let { _nextScene ->
                    _scene.dispose()
                    this@Rune._scene = _nextScene
                    nextScene = null
                    _nextScene.apply {
                        rune = this@Rune
                    }
                    onSceneChanged()
                    _nextScene.initialize()
                    _nextScene.resize(context.graphics.width, context.graphics.height, true)
                }
            }
            scene?.render()
        }
    }

    open suspend fun Context.create() = Unit

    /**
     * Called after a [RuneScene] ends, before the next [RuneScene] begins.
     */
    open fun onSceneChanged() = Unit
}