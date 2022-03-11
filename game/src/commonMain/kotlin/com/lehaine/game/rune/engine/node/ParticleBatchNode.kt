package com.lehaine.game.rune.engine.node

import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graph.node.addTo
import com.lehaine.littlekt.graph.node.annotation.SceneGraphDslMarker
import com.lehaine.littlekt.graph.node.node2d.Node2D
import com.lehaine.littlekt.graphics.Batch
import com.lehaine.littlekt.graphics.Camera
import com.lehaine.littlekt.graphics.Particle
import com.lehaine.littlekt.math.Rect
import com.lehaine.littlekt.util.calculateViewBounds
import com.lehaine.littlekt.util.fastForEach
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.time.Duration

@OptIn(ExperimentalContracts::class)
inline fun Node.particleBatch(callback: @SceneGraphDslMarker ParticleBatchNode.() -> Unit = {}): ParticleBatchNode {
    contract { callsInPlace(callback, InvocationKind.EXACTLY_ONCE) }
    return ParticleBatchNode().also(callback).addTo(this)
}

@OptIn(ExperimentalContracts::class)
inline fun SceneGraph<*>.particleBatch(callback: @SceneGraphDslMarker ParticleBatchNode.() -> Unit = {}): ParticleBatchNode {
    contract { callsInPlace(callback, InvocationKind.EXACTLY_ONCE) }
    return root.particleBatch(callback)
}

class ParticleBatchNode : Node2D() {

    private val particles = mutableListOf<Particle>()



    fun add(particle: Particle) {
        particles += particle
    }

    override fun update(dt: Duration) {
        particles.fastForEach {
            if (it.killed || !it.alive) {
                particles -= it
            }
        }
    }

    override fun render(batch: Batch, camera: Camera) {
        viewBounds.calculateViewBounds(camera)
        particles.fastForEach {
            if (!it.visible || !it.alive) return@fastForEach

            if (viewBounds.intersects(
                    it.x + globalX,
                    it.y + globalY,
                    it.slice.width.toFloat(),
                    it.slice.height.toFloat()
                )
            ) {

                batch.draw(
                    it.slice,
                    it.x + globalX,
                    it.y + globalY,
                    it.anchorX * it.slice.width,
                    it.anchorY * it.slice.height,
                    scaleX = it.scaleX * globalScaleX,
                    scaleY = it.scaleY * globalScaleY,
                    rotation = it.rotation + globalRotation,
                    colorBits = it.colorBits
                )
            }
        }
    }

    companion object {
        private val viewBounds: Rect = Rect()
    }
}