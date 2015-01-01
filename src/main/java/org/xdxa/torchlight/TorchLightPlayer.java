package org.xdxa.torchlight;

import org.bukkit.Location;

/**
 * Player data.
 */
class TorchLightPlayer {

    private Boolean enabled = true;
    private Location previousLocation;
    private Integer previousBlock;
    private Byte previousBlockData;

    /**
     * Is TorchLight enabled for this player?
     * @return true if enabled
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Enable/disable TorchLight for player.
     * @param enabled the new state
     */
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the previous light block location for the player. (This is not the previous player location.)
     * @return the previous block location
     */
    public Location getPreviousLocation() {
        return previousLocation;
    }

    /**
     * Set the previous light block location for the player.
     * @param previousLocation the previous light block location
     */
    public void setPreviousLocation(final Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    /**
     * Get the block material id that existed before replacing it with a light block.
     * @return the block material id
     */
    public Integer getPreviousBlockMaterialId() {
        return previousBlock;
    }

    /**
     * Set the block material id that existed before replacing it with a light block.
     * @param previousBlock the block material id
     */
    public void setPreviousBlockMaterialId(final Integer previousBlock) {
        this.previousBlock = previousBlock;
    }

    /**
     * Get the block metadata that existed before replacing it with a light block.
     * @return the block metadata
     */
    public Byte getPreviousBlockData() {
        return previousBlockData;
    }

    /**
     * Set the block metadata that existed before replacing it with a light block.
     * @param previousBlockData the block metadata
     */
    public void setPreviousBlockData(final Byte previousBlockData) {
        this.previousBlockData = previousBlockData;
    }
}