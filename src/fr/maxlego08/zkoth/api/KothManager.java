package fr.maxlego08.zkoth.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.enums.KothType;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.zcore.utils.storage.Saveable;

public interface KothManager extends Saveable {

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Optional<Koth> getKoth(String name);

	/**
	 * Create a KOTH
	 * 
	 * @param sender
	 * @param name
	 * @param minLocation
	 * @param maxLocation
	 * @param captureSeconds
	 */
	public void createKoth(CommandSender sender, String name, Location minLocation, Location maxLocation,
			int captureSeconds);

	/**
	 * 
	 * @param sender
	 * @param name
	 */
	public void deleteKoth(CommandSender sender, String name);

	/**
	 * Get koth Axe
	 * 
	 * @return {@link ItemStack}
	 */
	public ItemStack getKothAxe();

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	public Optional<Selection> getSelection(UUID uuid);

	/**
	 * 
	 * @param sender
	 * @param maxLocation
	 * @param minLocation
	 * @param name
	 */
	public void moveKoth(CommandSender sender, Location maxLocation, Location minLocation, String name);

	/***
	 * 
	 * @param sender
	 * @param name
	 * @param isNow
	 */
	public void spawnKoth(CommandSender sender, String name, boolean isNow);

	/**
	 * 
	 * @return
	 */
	public List<Koth> getActiveKoths();
	
	public List<Koth> getEnableKoths();

	/**
	 * 
	 * @return true if have active koth
	 */
	public boolean hasActiveKoth();

	/**
	 * 
	 * @param sender
	 */
	public void sendKothList(CommandSender sender);

	/**
	 * Show informations
	 * 
	 * @param sender
	 * @param name
	 */
	public void showInformations(CommandSender sender, String name);

	/**
	 * 
	 * @param sender
	 * @param name
	 * @param command
	 */
	public void addCommand(CommandSender sender, String name, String command);

	/**
	 * Remove commands
	 * 
	 * @param sender
	 * @param name
	 * @param id
	 */
	public void removeCommand(CommandSender sender, String name, int id);

	/**
	 * Stop koth
	 * 
	 * @param sender
	 * @param name
	 */
	public void stopKoth(CommandSender sender, String name);

	/**
	 * 
	 * @param sender
	 * @param name
	 * @param type
	 */
	public void setKothLoot(CommandSender sender, String name, LootType type);

	/**
	 * 
	 * @param player
	 * @param name
	 */
	public void updateLoots(Player player, String name);

	/**
	 * 
	 * @param sender
	 * @param name
	 * @param second
	 */
	public void setCaptureSeconds(CommandSender sender, String name, int second);
	
	/**
	 * 
	 * @return
	 */
	public List<String> getKothNames();

	/**
	 * 
	 * @param sender
	 * @param name
	 * @param kothType
	 */
	public void setKothType(CommandSender sender, String name, KothType kothType);

}
