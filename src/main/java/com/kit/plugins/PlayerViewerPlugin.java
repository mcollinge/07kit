package com.kit.plugins;

import com.kit.api.event.EventHandler;
import com.kit.api.event.PaintEvent;
import com.kit.api.plugin.Plugin;
import com.kit.api.wrappers.Npc;
import com.kit.core.control.PluginManager;
import com.kit.gui2.modelviewer.ModelViewer;

import javax.swing.*;

/**
 * Date: 13/02/2017
 * Time: 15:53
 *
 * @author Matt Collinge
 */
public class PlayerViewerPlugin extends Plugin {

    private ModelViewer viewer = null;

    public PlayerViewerPlugin(PluginManager manager) {
        super(manager);
    }

    @Override
    public String getName() {
        return "Player Viewer";
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @EventHandler
    public void onPaintEvent(PaintEvent event) {
        if (!isLoggedIn())
            return;
        if (player == null || player.getModel() == null)
            return;

        SwingUtilities.invokeLater(() -> {

            if (viewer == null)
                viewer = new ModelViewer();

            viewer.setCamera(camera.getX(), camera.getY(), client().getCameraZ(), camera.getPitch(), camera.getYaw());

           /* List<INpc> npcs = new ArrayList<>();
            for (INpc npc: client().getNpcs()) {
                if (npc != null)
                    npcs.add(npc);
            }*/

            //viewer.setNpcs(npcs.toArray(new INpc[npcs.size()]), player.getLocalX(), player.getLocalY());
            viewer.setPlayer(player.unwrap().unwrap());

            Npc npc = npcs.find("Hatius Cosaintus").single();
            if (npc != null)
                viewer.setNpcs(npc.unwrap(), player.getLocalX(), player.getLocalY());
        });


    }

}
