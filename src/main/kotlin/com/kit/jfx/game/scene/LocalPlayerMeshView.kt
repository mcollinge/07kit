package com.kit.jfx.game.scene

import com.kit.api.wrappers.interaction.SceneNode
import com.kit.core.Session
import com.kit.jfx.game.World
import javafx.scene.paint.Color

/**
 * Created by Matt on 21/02/2017.
 */

class LocalPlayerMeshView(world: World): SceneNodeMeshView(world, Session.get().player, Color.BLUE) {
    override fun node(): SceneNode? {
        return Session.get().player
    }
}
