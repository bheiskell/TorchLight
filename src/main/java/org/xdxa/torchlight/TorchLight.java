package org.xdxa.torchlight;

import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;

/**
 * Torch Light plugin.
 */
public class TorchLight extends JavaPlugin implements Listener {

    private static final Logger LOG = Logger.getLogger("Minecraft");
    private static final String TORCH_LIGHT_PREFIX = ChatColor.YELLOW + "[TorchLight] ";

    private TorchLightConfig config;
    private final Map<Player, TorchLightPlayer> torchLightPlayers = Maps.newHashMap();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        config = TorchLightConfig.create(getConfig());
        saveConfig();

        LOG.info("[TorchLight] Enabled");
    }

    @Override
    public void onDisable() {
        LOG.info("[TorchLight] Disabled");
    }

    @Override
    public boolean onCommand(final CommandSender sender,
                             final Command cmd,
                             final String commandLabel,
                             final String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        final Player player = (Player)sender;

        if (!player.hasPermission("torchlight.command")) {
            player.sendMessage(TORCH_LIGHT_PREFIX + ChatColor.RED + "TorchLight command is disabled");
            return false;
        }

        if (commandLabel.equalsIgnoreCase("torch")) {

            final TorchLightPlayer torchLightPlayer = getOrCreate(player);

            if (torchLightPlayer.isEnabled()) {
                torchLightPlayer.setEnabled(false);
                player.sendMessage(TORCH_LIGHT_PREFIX + ChatColor.RED + "TorchLight disabled");

                resetPreviousBlock(player);

            } else {
                torchLightPlayer.setEnabled(true);
                player.sendMessage(TORCH_LIGHT_PREFIX + ChatColor.GREEN + "TorchLight enabled");
            }

            return true;
        }
        return false;
    }

    @EventHandler
    @SuppressWarnings("javadoc")
    public void onPlayerJoin(final PlayerJoinEvent playerJoinEvent) {
        getOrCreate(playerJoinEvent.getPlayer());
    }

    @EventHandler
    @SuppressWarnings("javadoc")
    public void onPlayerQuit(final PlayerQuitEvent playerQuitEvent) {
        torchLightPlayers.remove(playerQuitEvent.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    @SuppressWarnings({ "javadoc", "deprecation" })
    public void onPlayerMove(final PlayerMoveEvent playerMoveEvent) {

        final Player player = playerMoveEvent.getPlayer();
        final TorchLightPlayer torchLightPlayer = getOrCreate(player);

        if (!player.hasPermission("torchlight.allow") || !torchLightPlayer.isEnabled()) {
            return;
        }

        final Location location = getPlayersLightBlockLocation(player);
        final World world = location.getWorld();
        final Block block = world.getBlockAt(location);

        resetPreviousBlock(player);

        for (final LightConfiguration light : config.getLights()) {

            final PlayerInventory inventory = player.getInventory();

            final boolean hasItem   = inventory.getItemInHand() != null;
            final boolean hasHelmet = inventory.getHelmet()     != null;
            final boolean hasBoots  = inventory.getBoots()      != null;

            final boolean isHoldingItem   = hasItem   && inventory.getItemInHand().getType() == light.getItem();
            final boolean isWearingHelmet = hasHelmet && inventory.getHelmet().getType()     == light.getHelmet();
            final boolean isWearingBoots  = hasBoots  && inventory.getBoots().getType()      == light.getBoot();

            if (isHoldingItem || isWearingHelmet || isWearingBoots) {
                torchLightPlayer.setPreviousBlockMaterialId(block.getTypeId());
                torchLightPlayer.setPreviousBlockData(block.getData());
                torchLightPlayer.setPreviousLocation(location);

                player.sendBlockChange(location, light.getLightBlock(), light.getLightMeta());
                return;
            }
        }
    }

    /**
     * Obtain or create the {@link TorchLightPlayer} for a given {@Player}.
     * @param player the player
     * @return the new or reused instance
     */
    private TorchLightPlayer getOrCreate(final Player player) {
        // TODO: Handle this better
        TorchLightPlayer torchLightPlayer = torchLightPlayers.get(player);

        if (torchLightPlayer == null) {
            LOG.finest("TorchLight data was null for player: " + player.getName());
            torchLightPlayers.put(player, new TorchLightPlayer());
            torchLightPlayer = torchLightPlayers.get(player);
            torchLightPlayer.setEnabled(player.hasPermission("torchlight.enabled"));
        }

        return torchLightPlayer;
    }

    /**
     * Determine the block beneath the player to light.
     * @param player the player
     * @return the location of the light block
     */
    private Location getPlayersLightBlockLocation(final Player player) {
        final Location location = player.getLocation();
        final World world = location.getWorld();

        // the block to be replaced is beneath the player's feet
        location.setY(location.getY() - 1);

        // skip undesirable blocks, e.g. air, fences, etc
        while (config.getIgnoredBlocks().contains(world.getBlockAt(location).getType())) {
            location.setY(location.getY() - 1);
        }
        return location;
    }

    @SuppressWarnings("deprecation")
    private void resetPreviousBlock(final Player player) {
        final TorchLightPlayer torchLightPlayer = torchLightPlayers.get(player);
        if (torchLightPlayer != null && torchLightPlayer.getPreviousLocation() != null) {
            player.sendBlockChange(torchLightPlayer.getPreviousLocation(),
                                   torchLightPlayer.getPreviousBlockMaterialId(),
                                   torchLightPlayer.getPreviousBlockData());
        }
    }
}