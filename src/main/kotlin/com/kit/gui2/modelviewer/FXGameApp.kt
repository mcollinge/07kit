package com.kit.gui2.modelviewer

import com.kit.core.Session
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.scene.*
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import javafx.scene.transform.Translate
import javafx.stage.Stage

/**
 * Created by Matt on 15/02/2017.
 */

class FXGameApp : Application() {

    private val cameraController = CameraController()
    private val playerMesh = TriangleMesh()
    private val playerMeshView = MeshView()
    private val npcMesh = TriangleMesh()
    private val npcMeshView = MeshView()
    private val clientAnimator = ClientAnimator()

    override fun start(primaryStage: Stage?) {
        cameraController.camera.nearClip = 0.1
        cameraController.camera.farClip = 10000.0

        playerMesh.texCoords.setAll(
                0f, 0f,
                0f, 1f,
                1f, 1f
        )
        playerMeshView.mesh = playerMesh
        playerMeshView.material = PhongMaterial(Color.BLUE)
        playerMeshView.drawMode = DrawMode.FILL

        npcMesh.texCoords.setAll(
                0f, 0f,
                0f, 1f,
                1f, 1f
        )
        npcMeshView.mesh = npcMesh
        npcMeshView.material = PhongMaterial(Color.GREEN)
        npcMeshView.drawMode = DrawMode.FILL

        val group = Group(cameraController, playerMeshView, npcMeshView)
        group.isAutoSizeChildren = false
        group.depthTest = DepthTest.ENABLE

        val scene = Scene(group, 765.0, 503.0,true, SceneAntialiasing.BALANCED)
        scene.fill = Color.BLACK
        scene.camera = cameraController.camera

        primaryStage?.scene = scene
        primaryStage?.title = "Runescape"
        primaryStage?.sizeToScene()
        primaryStage?.show()

        clientAnimator.start()

    }

    private inner class ClientAnimator: AnimationTimer() {

        override fun handle(now: Long) {
            if (Session.get() == null || !Session.get().isLoggedIn) return

            updatePlayer()
            updateNpc()
            updateCamera()
        }

        private fun updatePlayer() {
            val triangles = Session.get().player.model?.triangles ?: return

            val points = FXCollections.observableFloatArray()
            val faces = FXCollections.observableIntegerArray()

            for ((index, tri) in triangles.withIndex()) {
                points.addAll(tri[0])
                points.addAll(tri[1])
                points.addAll(tri[2])
                faces.addAll((points.size() / 3) - 1, index % 3)
            }

            playerMesh.points.setAll(points)
            playerMesh.faces.setAll(faces)
            playerMeshView.transforms.setAll(
                    Translate(Session.get().player.localX.toDouble(), 0.0, Session.get().player.localY.toDouble())
            )
        }

        private fun updateNpc() {
            val npc = Session.get().npcs.find("Hatius Cosaintus").single() ?: return

            if (npc.model == null)
                return
            val triangles = npc.model.triangles

            val points = FXCollections.observableFloatArray()
            val faces = FXCollections.observableIntegerArray()

            for ((index, tri) in triangles.withIndex()) {
                points.addAll(tri[0])
                points.addAll(tri[1])
                points.addAll(tri[2])
                faces.addAll((points.size() / 3) - 1, index % 3)
            }

            npcMesh.points.setAll(points)
            npcMesh.faces.setAll(faces)
            npcMeshView.transforms.setAll(
                    Translate(npc.localX.toDouble(), 0.0, npc.localY.toDouble())
            )
        }

        private fun updateCamera() {
            val camera = Session.get().camera
            cameraController.reset()
            cameraController.setPivot(Session.get().player.localX.toDouble(), camera.z.toDouble(), Session.get().player.localY.toDouble())
            cameraController.setTranslate(camera.x.toDouble(), camera.z.toDouble(), camera.y.toDouble())
            cameraController.setRotate(-camera.pitch.toDouble(), -camera.angle.toDouble(), 0.0)
        }

    }


}