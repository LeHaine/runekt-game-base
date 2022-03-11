package com.lehaine.game.engine.nodes

import com.lehaine.littlekt.graph.node.node2d.Node2D


abstract class GameLevel<LevelMark> : Node2D() {
    abstract var gridSize: Int

    abstract fun hasCollision(cx: Int, cy: Int): Boolean
    abstract fun hasMark(cx: Int, cy: Int, mark: LevelMark, dir: Int = 0): Boolean
    abstract fun setMark(cx: Int, cy: Int, mark: LevelMark, dir: Int = 0)
    abstract fun setMarks(cx: Int, cy: Int, marks: List<LevelMark>)
    abstract fun isValid(cx: Int, cy: Int): Boolean
    abstract fun getCoordId(cx: Int, cy: Int): Int
}