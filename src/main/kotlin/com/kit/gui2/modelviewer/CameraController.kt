package com.kit.gui2.modelviewer

import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate

/**
 * Created by Matt on 15/02/2017.
 */
class CameraController: Group() {

    private val translate = Translate()
    private val pivot = Translate()
    private val inversePivot = Translate()
    private val pitch = Rotate()
    private val yaw = Rotate()
    private val roll = Rotate()

    val camera = PerspectiveCamera(false)

    init {
        pitch.axis = Rotate.X_AXIS
        yaw.axis = Rotate.Y_AXIS
        roll.axis = Rotate.Z_AXIS
        transforms.setAll(pivot, yaw, pitch, roll, inversePivot, translate)
        children.add(camera)
    }

    fun setTranslate(x: Double, y: Double, z: Double) {
        translate.x = x
        translate.y = y
        translate.z = z
    }

    fun setRotate(pitchAngle: Double, yawAngle: Double, rollAngle: Double) {
        pitch.angle = pitchAngle
        yaw.angle = yawAngle
        roll.angle = rollAngle
    }

    fun setPivot(x: Double, y: Double, z: Double) {
        pivot.x = x
        pivot.y = y
        pivot.z = z
        inversePivot.x = -x
        inversePivot.y = -y
        inversePivot.z = -z
    }

    fun reset() {
        translate.x = 0.0
        translate.y = 0.0
        translate.z = 0.0
        pitch.angle = 0.0
        yaw.angle = 0.0
        roll.angle = 0.0
        pivot.x = 0.0
        pivot.y = 0.0
        pivot.z = 0.0
        inversePivot.x = 0.0
        inversePivot.y = 0.0
        inversePivot.z = 0.0
    }

}