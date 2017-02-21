package com.kit.jfx.game.scene

import com.kit.api.wrappers.Npc
import com.kit.api.wrappers.interaction.SceneNode
import com.kit.core.Session
import com.kit.jfx.game.World
import javafx.scene.paint.Color

/**
 * Created by Matt on 21/02/2017.
 */

class NpcMeshView(world: World, wrapped: Npc): SceneNodeMeshView(world, wrapped, Color.GREEN) {
    override fun node(): SceneNode? {
        return Session.get().npcs.index(index)
    }

}