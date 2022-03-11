package com.lehaine.game.rune.engine

import com.lehaine.game.rune.engine.node.EntityNode
import com.lehaine.littlekt.math.castRay
import com.lehaine.littlekt.math.dist
import kotlin.math.atan2
import kotlin.time.Duration


fun EntityNode.castRayTo(tcx: Int, tcy: Int, canRayPass: (Int, Int) -> Boolean) =
    castRay(cx, cy, tcx, tcy, canRayPass)

fun EntityNode.castRayTo(target: EntityNode, canRayPass: (Int, Int) -> Boolean) =
    castRay(cx, cy, target.cx, target.cy, canRayPass)

fun EntityNode.toGridPosition(cx: Int, cy: Int, xr: Float = 0.5f, yr: Float = 1f) {
    this.cx = cx
    this.cy = cy
    this.xr = xr
    this.yr = yr
    onPositionManuallyChanged()
}

fun EntityNode.toPixelPosition(x: Float, y: Float) {
    this.cx = (x / gridCellSize).toInt()
    this.cy = (y / gridCellSize).toInt()
    this.xr = (x - cx * gridCellSize) / gridCellSize
    this.yr = (y - cy * gridCellSize) / gridCellSize
    onPositionManuallyChanged()
}

fun EntityNode.dirTo(target: EntityNode) = dirTo(target.centerX)

fun EntityNode.dirTo(targetX: Float) = if (targetX > centerX) 1 else -1

fun EntityNode.distGridTo(tcx: Int, tcy: Int, txr: Float = 0.5f, tyr: Float = 0.5f) =
    dist(cx + xr, cy + yr, tcx + txr, tcy + tyr)

fun EntityNode.distGridTo(target: EntityNode) = distGridTo(target.cx, target.cy, target.xr, target.yr)

fun EntityNode.distPxTo(x: Float, y: Float) = dist(px, py, x, y)
fun EntityNode.distPxTo(targetGridPosition: EntityNode) = distPxTo(targetGridPosition.px, targetGridPosition.py)

fun EntityNode.angleTo(x: Float, y: Float) = atan2(y - py, x - px)
fun EntityNode.angleTo(target: EntityNode) = angleTo(target.centerX, target.centerY)

val EntityNode.cd get() = this.cooldown

fun EntityNode.cooldown(name: String, time: Duration, callback: () -> Unit = {}) =
    this.cooldown.timeout(name, time, callback)

fun EntityNode.cd(name: String, time: Duration, callback: () -> Unit = {}) =
    cooldown(name, time, callback)