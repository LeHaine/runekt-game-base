package com.lehaine.game.engine.nodes

import com.lehaine.littlekt.graph.node.node2d.Node2D
import com.lehaine.littlekt.graphics.Batch
import com.lehaine.littlekt.graphics.Camera
import com.lehaine.littlekt.graphics.ParticleSimulator
import com.lehaine.littlekt.graphics.TextureSlice
import com.lehaine.littlekt.math.Rect
import com.lehaine.littlekt.util.calculateViewBounds
import com.lehaine.littlekt.util.fastForEach

class ParticleNode : Node2D() {

    var maxParticles = 2048

    private val simulator by lazy { ParticleSimulator(maxParticles) }

    fun alloc(slice: TextureSlice, x: Float, y: Float) = simulator.alloc(slice, x, y)

    override fun render(batch: Batch, camera: Camera) {
        viewBounds.calculateViewBounds(camera)
        simulator.particles.fastForEach {
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