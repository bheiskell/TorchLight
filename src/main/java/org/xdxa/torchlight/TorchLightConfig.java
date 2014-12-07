package org.xdxa.torchlight;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * {@link TorchLight} configuration.
 */
public class TorchLightConfig {

    private static final String COMMENT =
"Each entry within 'lights:' configures the activation of the 'light-block'\n" +
"when holding/wearing specific items. Each entry within 'ignored-blocks:' is\n" +
"bypased when placing the light block beneath the player.";

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger("Minecraft");

    private static final String KEY_LIGHTS            = "lights";
    private static final String KEY_IGNORED_MATERIALS = "ignored-blocks";

    private static final List<String> IGNORED_BLOCKS = ImmutableList.of(
            Material.AIR.name(),
            Material.BED.name(),
            Material.BREWING_STAND.name(),
            Material.BROWN_MUSHROOM.name(),
            Material.CHEST.name(),
            Material.DEAD_BUSH.name(),
            Material.DETECTOR_RAIL.name(),
            Material.DIODE_BLOCK_OFF.name(),
            Material.DIODE_BLOCK_ON.name(),
            Material.ENCHANTMENT_TABLE.name(),
            Material.FENCE.name(),
            Material.FENCE_GATE.name(),
            Material.LADDER.name(),
            Material.LAVA.name(),
            Material.LEVER.name(),
            Material.LEVER.name(),
            Material.LONG_GRASS.name(),
            Material.NETHER_FENCE.name(),
            Material.NETHER_WARTS.name(),
            Material.POWERED_RAIL.name(),
            Material.RAILS.name(),
            Material.REDSTONE_TORCH_OFF.name(),
            Material.REDSTONE_TORCH_ON.name(),
            Material.REDSTONE_WIRE.name(),
            Material.RED_MUSHROOM.name(),
            Material.RED_ROSE.name(),
            Material.SNOW.name(),
            Material.STATIONARY_LAVA.name(),
            Material.STATIONARY_WATER.name(),
            Material.STEP.name(),
            Material.STONE_PLATE.name(),
            Material.TORCH.name(),
            Material.TRAP_DOOR.name(),
            Material.VINE.name(),
            Material.WATER.name(),
            Material.WATER_LILY.name(),
            Material.WEB.name(),
            Material.WOOD_PLATE.name(),
            Material.YELLOW_FLOWER.name(),
            Material.STAINED_CLAY.name(),
            Material.COBBLE_WALL.name(),
            Material.IRON_FENCE.name(),
            Material.THIN_GLASS.name());

    private List<LightConfiguration> lights;
    private Set<Material> ignoredBlocks;

    /**
     * Get the light configurations.
     * @return the light configurations
     */
    public Collection<LightConfiguration> getLights() {
        return lights;
    }

    /**
     * Get the blocks which are skipped when determine which block to replace with a light.
     * @return the ignored blocks
     */
    public Set<Material> getIgnoredBlocks() {
        return ignoredBlocks;
    }

    /**
     * Initialize the {@link TorchLightConfig} while populating the defaults for Bukkit's {@link FileConfiguration}.
     * @param config the file configuration which will be read and have defaults set
     * @return the {@link TorchLightConfig}
     */
    public static TorchLightConfig create(final FileConfiguration config) {
        config.options().header(COMMENT);
        config.options().copyHeader(true);
        config.options().copyDefaults(true);

        config.addDefault(KEY_LIGHTS, ImmutableList.of(
                LightConfiguration.GLOWSTONE_DEFAULT.serialize(),
                LightConfiguration.REDSTONE_DEFAULT.serialize()));

        config.addDefault(KEY_IGNORED_MATERIALS, IGNORED_BLOCKS);

        final TorchLightConfig result = new TorchLightConfig();

        final List<LightConfiguration> lights = Lists.newArrayList();
        for (final Map<?, ?> options : config.getMapList(KEY_LIGHTS)) {
            lights.add(LightConfiguration.deserialize(options));
        }

        result.lights = ImmutableList.copyOf(lights);
        result.ignoredBlocks = materialsFromStringNames(config.getStringList(KEY_IGNORED_MATERIALS));

        return result;
    }

    /**
     * Given a {@link Collection} of {@link Material} names, return the list of {@Set Material}s.
     * @param materialStrings the {@link Collection} of {@link Material} Strings
     * @return the {@link Set} of {@link Material}s
     */
    private static Set<Material> materialsFromStringNames(final List<String> materialStrings) {
        final Set<Material> materials = Sets.newHashSet();

        for (final String material : materialStrings) {
            materials.add(Material.getMaterial(material));
        }

        return ImmutableSet.copyOf(materials);
    }
}
