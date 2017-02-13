package com.kit.plugins;

import com.kit.api.event.EventHandler;
import com.kit.api.event.PaintEvent;
import com.kit.api.plugin.Plugin;
import com.kit.api.wrappers.Entity;
import com.kit.api.wrappers.Model;
import com.kit.core.control.PluginManager;
import com.kit.gui2.modelviewer.ModelViewer;

import javax.swing.*;
import java.util.Optional;

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
            Model playerModel = player.getModel();
            if (viewer == null)
                viewer = new ModelViewer();
            viewer.setModel(playerModel);
            viewer.setCamera(client().getCameraX(), client().getCameraZ(), client().getCameraY(), client().getCameraPitch(), client().getCameraYaw());
        });
    }

}
