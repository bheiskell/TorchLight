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

    public Boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }
    public Location getPreviousLocation() {
        return previousLocation;
    }
    public void setPreviousLocation(final Location previousLocation) {
        this.previousLocation = previousLocation;
    }
    public Integer getPreviousBlock() {
        return previousBlock;
    }
    public void setPreviousBlockMaterialId(final Integer previousBlock) {
        this.previousBlock = previousBlock;
    }
    public Byte getPreviousBlockData() {
        return previousBlockData;
    }
    public void setPreviousBlockData(final Byte previousBlockData) {
        this.previousBlockData = previousBlockData;
    }
}