package com.lehaine.game.rune.engine

import com.lehaine.littlekt.graphics.gl.BlendEquationMode
import com.lehaine.littlekt.graphics.gl.BlendFactor

/**
 * @author Colton Daily
 * @date 3/10/2022
 */
sealed class BlendMode(
    val colorSourceBlend: BlendFactor = BlendFactor.ONE,
    val alphaSourceBlend: BlendFactor = BlendFactor.ONE,
    val colorDestinationBlend: BlendFactor = BlendFactor.ZERO,
    val alphaDestinationBlend: BlendFactor = BlendFactor.ZERO,
    val alphaBlendFunction: BlendEquationMode = BlendEquationMode.FUNC_ADD,
    val colorBlendFunction: BlendEquationMode = BlendEquationMode.FUNC_ADD
) {
    object Alpha : BlendMode(
        colorDestinationBlend = BlendFactor.ONE_MINUS_SRC_ALPHA,
        alphaDestinationBlend = BlendFactor.ONE_MINUS_SRC_ALPHA
    )

    object Opaque : BlendMode()

    object NonPreMultiplied : BlendMode(
        colorSourceBlend = BlendFactor.SRC_ALPHA,
        alphaSourceBlend = BlendFactor.SRC_ALPHA,
        colorDestinationBlend = BlendFactor.ONE_MINUS_SRC_ALPHA,
        alphaDestinationBlend = BlendFactor.ONE_MINUS_SRC_ALPHA
    )

    object Add : BlendMode(
        colorSourceBlend = BlendFactor.SRC_ALPHA,
        alphaSourceBlend = BlendFactor.SRC_ALPHA,
        colorDestinationBlend = BlendFactor.ONE,
        alphaDestinationBlend = BlendFactor.ONE
    )

    object Subtract : BlendMode(
        colorSourceBlend = BlendFactor.SRC_ALPHA,
        alphaSourceBlend = BlendFactor.SRC_ALPHA,
        colorDestinationBlend = BlendFactor.ONE,
        alphaDestinationBlend = BlendFactor.ONE,
        colorBlendFunction = BlendEquationMode.FUNC_REVERSE_SUBTRACT,
        alphaBlendFunction = BlendEquationMode.FUNC_REVERSE_SUBTRACT
    )

    object Difference : BlendMode(
        colorSourceBlend = BlendFactor.ONE_MINUS_DST_COLOR,
        colorDestinationBlend = BlendFactor.ONE_MINUS_SRC_COLOR,
        colorBlendFunction = BlendEquationMode.FUNC_ADD
    )

    object Multiply : BlendMode(
        colorSourceBlend = BlendFactor.DST_COLOR,
        alphaSourceBlend = BlendFactor.DST_ALPHA,
        colorDestinationBlend = BlendFactor.ZERO,
        alphaDestinationBlend = BlendFactor.ZERO,
        colorBlendFunction = BlendEquationMode.FUNC_ADD,
        alphaBlendFunction = BlendEquationMode.FUNC_ADD
    )

    object Lighten : BlendMode(
        colorSourceBlend = BlendFactor.ONE,
        alphaSourceBlend = BlendFactor.ONE,
        colorDestinationBlend = BlendFactor.ONE,
        alphaDestinationBlend = BlendFactor.ONE,
        colorBlendFunction = BlendEquationMode.MAX,
        alphaBlendFunction = BlendEquationMode.MAX
    )

    object Darken : BlendMode(
        colorSourceBlend = BlendFactor.ONE,
        alphaSourceBlend = BlendFactor.ONE,
        colorDestinationBlend = BlendFactor.ONE,
        alphaDestinationBlend = BlendFactor.ONE,
        colorBlendFunction = BlendEquationMode.MIN,
        alphaBlendFunction = BlendEquationMode.MIN
    )

    object Screen : BlendMode(
        colorSourceBlend = BlendFactor.ONE_MINUS_DST_COLOR,
        colorDestinationBlend = BlendFactor.ONE,
        colorBlendFunction = BlendEquationMode.FUNC_ADD
    )

    object LinearDodge : BlendMode(
        colorSourceBlend = BlendFactor.ONE,
        colorDestinationBlend = BlendFactor.ONE,
        colorBlendFunction = BlendEquationMode.FUNC_ADD
    )

    object LinearBurn : BlendMode(
        colorSourceBlend = BlendFactor.ONE,
        colorDestinationBlend = BlendFactor.ONE,
        colorBlendFunction = BlendEquationMode.FUNC_REVERSE_SUBTRACT
    )

}