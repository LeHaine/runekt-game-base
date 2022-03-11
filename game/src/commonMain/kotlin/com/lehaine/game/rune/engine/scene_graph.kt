package com.lehaine.game.rune.engine

import com.lehaine.game.rune.engine.node.EntityNode
import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graph.node.Node
import kotlin.time.Duration

fun Node.postUpdate(dt: Duration) {
    if (this is EntityNode) {
        this.postUpdate(dt)
    }
    children.forEach {
        it.postUpdate(dt)
    }
}

fun SceneGraph<*>.postUpdate(dt: Duration) {
    root.postUpdate(dt)
}