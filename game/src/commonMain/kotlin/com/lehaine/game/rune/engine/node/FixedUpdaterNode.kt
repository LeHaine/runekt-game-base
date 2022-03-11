package com.lehaine.game.rune.engine.node

import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graph.node.addTo
import com.lehaine.littlekt.graph.node.annotation.SceneGraphDslMarker
import com.lehaine.littlekt.util.milliseconds
import com.lehaine.littlekt.util.seconds
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalContracts::class)
inline fun Node.fixedUpdater(callback: @SceneGraphDslMarker FixedUpdaterNode.() -> Unit = {}): FixedUpdaterNode {
    contract { callsInPlace(callback, InvocationKind.EXACTLY_ONCE) }
    return FixedUpdaterNode().also(callback).addTo(this)
}

@OptIn(ExperimentalContracts::class)
inline fun SceneGraph<*>.fixedUpdater(callback: @SceneGraphDslMarker FixedUpdaterNode.() -> Unit = {}): FixedUpdaterNode {
    contract { callsInPlace(callback, InvocationKind.EXACTLY_ONCE) }
    return root.fixedUpdater(callback)
}


class FixedUpdaterNode : Node() {
    val fixedProgressionRatio: Float get() = _fixedProgressionRatio
    var timesPerSecond: Int = 30
        set(value) {
            field = value
            time = (1f / timesPerSecond).seconds
        }

    private var accum = 0.milliseconds
    private var _fixedProgressionRatio = 1f
    private var time = (1f / timesPerSecond).seconds

    override fun update(dt: Duration) {
        accum += dt
        while (accum >= time * 0.75) {
            accum -= time
            children.forEach {
                it.fixedUpdate()
            }
        }

        _fixedProgressionRatio = accum.milliseconds / time.milliseconds
    }

    private fun Node.fixedUpdate() {
        if (this is EntityNode) {
            this.fixedUpdate()
        }
        children.forEach {
            it.fixedUpdate()
        }
    }
}