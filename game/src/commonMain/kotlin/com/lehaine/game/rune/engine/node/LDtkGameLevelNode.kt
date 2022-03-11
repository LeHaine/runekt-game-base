package com.lehaine.game.rune.engine.node

import com.lehaine.game.rune.engine.GameLevel
import com.lehaine.littlekt.graph.SceneGraph
import com.lehaine.littlekt.graph.node.Node
import com.lehaine.littlekt.graph.node.addTo
import com.lehaine.littlekt.graph.node.annotation.SceneGraphDslMarker
import com.lehaine.littlekt.graph.node.node2d.Node2D
import com.lehaine.littlekt.graphics.Batch
import com.lehaine.littlekt.graphics.Camera
import com.lehaine.littlekt.graphics.tilemap.ldtk.LDtkIntGridLayer
import com.lehaine.littlekt.graphics.tilemap.ldtk.LDtkLevel
import com.lehaine.littlekt.math.clamp
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <LevelMark> Node.ldtkLevel(
    level: LDtkLevel,
    callback: @SceneGraphDslMarker LDtkGameLevelNode<LevelMark>.() -> Unit = {}
): LDtkGameLevelNode<LevelMark> {
    contract { callsInPlace(callback, InvocationKind.EXACTLY_ONCE) }
    return LDtkGameLevelNode<LevelMark>(level).also(callback).addTo(this)
}

@OptIn(ExperimentalContracts::class)
inline fun <LevelMark> SceneGraph<*>.ldtkLevel(
    level: LDtkLevel,
    callback: @SceneGraphDslMarker LDtkGameLevelNode<LevelMark>.() -> Unit = {}
): LDtkGameLevelNode<LevelMark> {
    contract { callsInPlace(callback, InvocationKind.EXACTLY_ONCE) }
    return root.ldtkLevel(level, callback)
}

open class LDtkGameLevelNode<LevelMark>(var level: LDtkLevel) : Node2D(), GameLevel<LevelMark> {
    override var gridSize: Int = 16

    val levelWidth get() = level["Collisions"].gridWidth
    val levelHeight get() = level["Collisions"].gridHeight

    protected val marks = mutableMapOf<LevelMark, MutableMap<Int, Int>>()

    // a list of collision layers indices from LDtk world
    protected val collisionLayers = intArrayOf(1)
    protected val collisionLayer = level["Collisions"] as LDtkIntGridLayer

    override fun isValid(cx: Int, cy: Int) = collisionLayer.isCoordValid(cx, cy)
    override fun getCoordId(cx: Int, cy: Int) = collisionLayer.getCoordId(cx, cy)

    override fun hasCollision(cx: Int, cy: Int): Boolean {
        return if (isValid(cx, cy)) {
            collisionLayers.contains(collisionLayer.getInt(cx, cy))
        } else {
            true
        }
    }

    override fun hasMark(cx: Int, cy: Int, mark: LevelMark, dir: Int): Boolean {
        return marks[mark]?.get(getCoordId(cx, cy)) == dir && isValid(cx, cy)
    }

    override fun setMarks(cx: Int, cy: Int, marks: List<LevelMark>) {
        marks.forEach {
            setMark(cx, cy, it)
        }
    }

    override fun setMark(cx: Int, cy: Int, mark: LevelMark, dir: Int) {
        if (isValid(cx, cy) && !hasMark(cx, cy, mark)) {
            if (!marks.contains(mark)) {
                marks[mark] = mutableMapOf()
            }

            marks[mark]?.set(getCoordId(cx, cy), dir.clamp(-1, 1))
        }
    }

    // set level marks at start of level creation to react to certain tiles
    protected open fun createLevelMarks() = Unit

    override fun render(batch: Batch, camera: Camera) {
        level.render(batch, camera, globalX, globalY)
    }

}