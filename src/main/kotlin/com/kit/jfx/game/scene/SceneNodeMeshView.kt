package com.kit.jfx.game.scene

import com.kit.api.wrappers.interaction.SceneNode
import com.kit.core.Session
import com.kit.jfx.game.World
import javafx.collections.FXCollections
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.CullFace
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import javafx.scene.transform.Translate

/**
 * Created by Matt on 20/02/2017.
 */
abstract class SceneNodeMeshView(world: World, wrapped: SceneNode, color: Color): MeshView() {

    val world = world
    val index = wrapped.index
        get

    init {
        val triMesh = TriangleMesh()
        triMesh.texCoords.setAll(
                0f, 0f,
                0f, 1f,
                1f, 1f
        )
        mesh = triMesh
        material = PhongMaterial(color)
        drawMode = DrawMode.FILL
        cullFace = CullFace.BACK
    }

    abstract fun node(): SceneNode?

    fun update() {
        val sceneNode = node()

        if (sceneNode == null) {
            world.remove(this)
            return
        }

        val triangles = sceneNode.model?.triangles ?: return

        val points = FXCollections.observableFloatArray()
        val faces = FXCollections.observableIntegerArray()

        for ((index, tri) in triangles.withIndex()) {
            points.addAll(tri[0])
            points.addAll(tri[1])
            points.addAll(tri[2])
            faces.addAll((points.size() / 3) - 1, index % 3)
        }
        val triMesh = mesh as TriangleMesh
        triMesh.points.setAll(points)
        triMesh.faces.setAll(faces)

        val x = sceneNode.localX.toDouble()
        val y = Session.get().viewport.getTileHeight(sceneNode.localX, sceneNode.localY, sceneNode.z).toDouble()
        val z = sceneNode.localY.toDouble()

        transforms.setAll(
                Translate(x, y, z)
        )

    }

}