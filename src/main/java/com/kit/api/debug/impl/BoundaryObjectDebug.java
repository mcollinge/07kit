package com.kit.api.debug.impl;

import com.kit.api.debug.AbstractDebug;
import com.kit.api.event.EventHandler;

import com.kit.api.debug.AbstractDebug;
import com.kit.api.event.PaintEvent;
import com.kit.api.wrappers.GameObject;
import com.kit.api.wrappers.ObjectComposite;
import com.kit.core.Session;
import com.kit.api.debug.AbstractDebug;
import com.kit.api.event.EventHandler;
import com.kit.api.event.PaintEvent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import static com.kit.api.wrappers.GameObject.GameObjectType.BOUNDARY;

/**
 */
public class BoundaryObjectDebug extends AbstractDebug {

    public BoundaryObjectDebug() {
        super();
    }

    @EventHandler
    public void onPaint(PaintEvent event) {
        Graphics g = event.getGraphics();
        g.setColor(Color.YELLOW);
        if (ctx().isLoggedIn()) {
            for (GameObject object : ctx().objects.find().distance(10).type(BOUNDARY).asList()) {
                Point pos = object.getBasePoint();
                ObjectComposite composite = object.getComposite();
                if (composite == null || composite.getName() == null) {
                    g.drawString(String.valueOf(object.getId()), pos.x, pos.y);
                } else {
                    g.drawString(composite.getName() + " | " + String.valueOf(object.getId()), pos.x, pos.y);
                    int y = pos.y + 10;
                    for (short colour: composite.getOriginalModelColors()) {
                        g.drawString(String.valueOf(colour), pos.x, y);
                        y += 10;
                    }
                }
            }

            List<GameObject> boundariesOnTile = ctx().objects.find().location(ctx().player.getTile()).type(BOUNDARY).asList();
            for (int i = 0; i < boundariesOnTile.size(); i++) {
                GameObject boundary = boundariesOnTile.get(i);
                g.drawString("Boundary on tile: " + boundary.getName(), 10, (150 + (i * 30)));
            }
        }
    }

    @Override
    public String getName() {
        return "Objects/Boundaries";
    }

    @Override
    public String getShortcode() {
        return "boundaries";
    }
}
