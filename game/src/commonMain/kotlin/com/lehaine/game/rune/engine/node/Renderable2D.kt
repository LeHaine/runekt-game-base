package com.lehaine.game.rune.engine.node

import com.lehaine.game.rune.engine.BlendMode
import com.lehaine.littlekt.graph.node.node2d.Node2D
import com.lehaine.littlekt.graphics.Color

abstract class Renderable2D : Node2D() {
    var blendMode: BlendMode = BlendMode.Alpha
    var color = Color.WHITE
}