package com.kit.api;

import com.kit.api.wrappers.Tile;
import com.kit.api.wrappers.interaction.SceneNode;

/**
 * @author const_
 */
public interface Camera {

    /**
     * Gets the cameraController X
     *
     * @return cameraController x
     */
    int getX();

    /**
     * Gets the cameraController y
     *
     * @return cameraController y
     */
    int getY();

    /**
     * Gets the cameraController z
     *
     * @return cameraController z
     */
    int getZ();


    /**
     * Retrieves the current cameraController yaw in degrees.
     *
     * @return cameraController yaw
     */
    int getYaw();

    /**
     * Retrieves the current cameraController pitch in degrees.
     *
     * @return cameraController pitch
     */
    int getPitch();


    /**
     * Calculates the angle to tile
     *
     * @param tile the tile in which you want to get the angle to
     * @return the angle to tile
     */
    int getAngleTo(Tile tile);



}
