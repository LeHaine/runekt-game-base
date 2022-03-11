package com.lehaine.game.rune.engine.render

import com.lehaine.game.rune.engine.node.Renderable2D
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graphics.Batch
import com.lehaine.littlekt.graphics.Camera
import com.lehaine.littlekt.math.Rect
import com.lehaine.littlekt.util.calculateViewBounds

/**
 * @author Colton Daily
 */
class DefaultRenderer(context: Context) : Renderer(context) {
    private val viewBounds: Rect = Rect()

    override fun render(batch: Batch, scene: SceneGraph<*>) {
        val camera = scene.camera
        camera.update()
        viewBounds.calculateViewBounds(camera)

        begin(batch, camera)
        scene.root.render(batch, camera, ::onRenderNode)
        end(batch)
    }

    private fun onRenderNode(node: Node, batch: Batch, camera: Camera) {
        if (node is Renderable2D && viewBounds.intersects(
                node.globalX,
                node.globalY,
                node.globalX + node.width,
                node.globalY + node.height
            )
        ) {
            renderAfterStateCheck(node, batch)
        } else {
            node.render(batch, camera)
        }
    }
}

private fun Node.render(batch: Batch, camera: Camera, callback: (Node, Batch, Camera) -> Unit) {
    if (!enabled || !visible) return
    callback(this, batch, camera)
    render(batch, camera)
    onRender.emit(batch, camera)
    children.forEach {
        it.render(batch, camera, callback)
    }
}