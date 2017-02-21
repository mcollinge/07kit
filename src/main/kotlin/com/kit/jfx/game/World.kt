package com.kit.jfx.game

import com.kit.core.Session
import com.kit.jfx.game.scene.LocalPlayerMeshView
import com.kit.jfx.game.scene.NpcMeshView
import com.kit.jfx.game.scene.SceneNodeMeshView
import javafx.scene.DepthTest
import javafx.scene.Group

/**
 * Created by Matt on 21/02/2017.
 */
class World: Group() {

    val cameraController = CameraController()
    private var localPlayer: LocalPlayerMeshView? = null
    private val npcMap = mutableMapOf<Int, NpcMeshView>()

    init {
        isAutoSizeChildren = false
        depthTest = DepthTest.ENABLE
        cameraController.camera.nearClip = 0.1
        cameraController.camera.farClip = 10000.0
        children.add(cameraController)
    }

    fun add(node: SceneNodeMeshView) {
        if (node is NpcMeshView)
            npcMap.put(node.index, node)
        children.add(node)
    }

    fun remove(node: SceneNodeMeshView) {
        if (node is NpcMeshView)
            npcMap.remove(node.index)
        children.remove(node)
    }

    fun update() {
        updateCamera()
        updatePlayer()
        updateNpcs()
    }

    private fun updateCamera() {
        val clientCamera = Session.get().camera

        cameraController.reset()
        cameraController.setPivot(clientCamera.x.toDouble(), clientCamera.z.toDouble(), clientCamera.y.toDouble())
        cameraController.setRotate(-clientCamera.pitch.toDouble(), -clientCamera.yaw.toDouble(), 0.0)
        cameraController.setTranslate(clientCamera.x.toDouble(), clientCamera.z.toDouble(), clientCamera.y.toDouble())
    }

    private fun updatePlayer() {
        if (localPlayer == null) {
            localPlayer = LocalPlayerMeshView(this)
            children.add(localPlayer)
        }

        localPlayer?.update()
    }

    private fun updateNpcs() {
        for (npc in Session.get().npcs.all) {
            if (npcMap.containsKey(npc.index))
                npcMap[npc.index]?.update()
            else {
                add(NpcMeshView(this, npc))
            }
        }
    }

}