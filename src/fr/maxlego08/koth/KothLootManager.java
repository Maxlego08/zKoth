package fr.maxlego08.koth;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.ZPlugin;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.zcore.logger.Logger.LogType;
import fr.maxlego08.koth.zcore.utils.ZUtils;

public class KothLootManager extends ZUtils {

	private final Koth koth;
	private final Player player;
	private final FactionListener factionListener;

	public KothLootManager(Koth koth, Player player, FactionListener factionListener) {
		super();
		this.koth = koth;
		this.player = player;
		this.factionListener = factionListener;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the factionListener
	 */
	public FactionListener getFactionListener() {
		return factionListener;
	}

	public void perform() {

		KothLoot kothLoot = Config.loot;
		if (kothLoot == null) {
			kothLoot = KothLoot.COMMAND;
			Logger.info("loot in config is null, please set COMMAND or CHEST", LogType.ERROR);
		}

		switch (kothLoot) {
		case CHEST:

			// On récup la location au center, et on va récup la location du
			// dessous pour savoir si c'est le sole
			Location center = koth.getCuboid().getCenter();

			while (center.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {

				center = center.getBlock().getRelative(BlockFace.DOWN).getLocation();

			}

			Location location = center;

			Bukkit.getScheduler().runTask(ZPlugin.z(), () -> {

				location.getBlock().setType(Material.CHEST);
				Chest chest = (Chest) location.getBlock().getState();

				List<String> str = (Config.itemstacks == null ? Config.itemstacks = new ArrayList<>()
						: Config.itemstacks);
				str.forEach(item -> {

					ItemStack itemStack = decode(item);
					if (itemStack != null) {
						chest.getInventory().addItem(itemStack);
					}

				});
				Bukkit.getScheduler().runTaskLater(ZPlugin.z(), () -> {
					location.getBlock().setType(Material.AIR);
				}, 20 * Config.removeChestSec);

			});

			break;
		case COMMAND:

			List<String> str = (Config.commands == null ? Config.commands = new ArrayList<>() : Config.commands);
			str.forEach(command -> {

				if (Config.giveCommandToFaction) {

					factionListener.getOnlinePlayer(player).forEach(player -> {
						
						String finalCommand = command.replace("%player%", player.getName());
						finalCommand = finalCommand.replace("%name%", koth.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
						
					});
					
				} else {
					
					String finalCommand = command.replace("%player%", player.getName());
					finalCommand = finalCommand.replace("%name%", koth.getName());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
					
				}
			});

			break;
		default:
			break;
		}

	}

}
