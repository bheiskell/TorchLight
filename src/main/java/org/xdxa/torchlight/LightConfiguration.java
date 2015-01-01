package org.xdxa.torchlight;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * This class represents the item in hand or inventory worn that will activate a given light block.
 */
public class LightConfiguration implements ConfigurationSerializable {

    /**
     * Default configuration for glowstone.
     */
    public static final LightConfiguration GLOWSTONE_DEFAULT = new LightConfiguration(
            Material.GLOWSTONE,
            null,
            Material.TORCH,
            Material.GOLD_HELMET,
            Material.GOLD_BOOTS
            );
    /**
     * Default configuration for redstone.
     */
    public static final LightConfiguration REDSTONE_DEFAULT = new LightConfiguration(
            Material.REDSTONE_LAMP_ON,
            null,
            Material.REDSTONE_TORCH_ON,
            null,
            null
            );

    private static final String KEY_LIGHT_BLOCK = "light-block";
    private static final String KEY_LIGHT_META  = "light-meta";
    private static final String KEY_ITEM        = "item";
    private static final String KEY_HELMET      = "helmet";
    private static final String KEY_BOOT        = "boot";

    private final Material lightBlock;
    private final Byte lightMeta;
    private final Material item;
    private final Material helmet;
    private final Material boot;

    LightConfiguration(final Material lightBlock,
                       final Byte lightMeta,
                       final Material item,
                       final Material helmet,
                       final Material boot) {
        this.lightBlock = Preconditions.checkNotNull(lightBlock, "light-block cannot be null");
        this.lightMeta  = lightMeta;
        this.item       = item;
        this.helmet     = helmet;
        this.boot       = boot;
    }

    /**
     * Get the light block to place underneath the player.
     * @return the light block
     */
    public Material getLightBlock() {
        return lightBlock;
    }

    /**
     * Get the meta-data associated with the light block, typically 0;
     * @return the meta-data byte
     */
    public Byte getLightMeta() {
        return lightMeta == null ? (byte)0 : lightMeta;
    }

    /**
     * Get the item in hand which will activate the light block.
     * @return the item or null
     */
    public Material getItem() {
        return item;
    }

    /**
     * Get the helmet which can be worn to activate the light block.
     * @return the helmet or null
     */
    public Material getHelmet() {
        return helmet;
    }

    /**
     * Get the boot which can be worn to activate the light block.
     * @return the boot or null
     */
    public Material getBoot() {
        return boot;
    }

    /**
     * Deserialize from the {@link Map} of configuration options.
     * @param config the configuration
     * @return the instance
     */
    public static LightConfiguration deserialize(final Map<?, ?> config) {
        final Material lightBlock = Material.getMaterial((String)config.get(KEY_LIGHT_BLOCK));
        final Byte lightMeta  = (Byte)config.get(KEY_LIGHT_META);

        final Material item   = Material.getMaterial((String)config.get(KEY_ITEM));
        final Material helmet = Material.getMaterial((String)config.get(KEY_HELMET));
        final Material boot   = Material.getMaterial((String)config.get(KEY_BOOT));

        return new LightConfiguration(lightBlock, lightMeta, item, helmet, boot);
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> config = Maps.newLinkedHashMap();

        config.put(KEY_LIGHT_BLOCK, lightBlock.name());

        if (lightMeta != null) {
            config.put(KEY_LIGHT_META, lightMeta);
        }

        putNameIfNotNull(config, KEY_ITEM, item);
        putNameIfNotNull(config, KEY_HELMET, helmet);
        putNameIfNotNull(config, KEY_BOOT, boot);

        return config;
    }

    private static void putNameIfNotNull(final Map<String, Object> config, final String key, final Material material) {
        if (material != null) {
            config.put(key, material.name());
        }
    }
}