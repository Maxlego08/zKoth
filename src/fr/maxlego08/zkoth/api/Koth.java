package fr.maxlego08.zkoth.api;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;
import fr.maxlego08.zkoth.zcore.utils.interfaces.CollectionConsumer;

public interface Koth {

	/**
	 * 
	 * @return koth name
	 */
	public String getName();

	/**
	 * 
	 * @return {@link Location}
	 */
	public Location getMinLocation();

	/**
	 * 
	 * @return {@link Location}
	 */
	public Location getMaxLocation();

	/**
	 * 
	 * @return {@link Cuboid}
	 */
	public Cuboid getCuboid();

	/**
	 * Get center location
	 * 
	 * @return {@link Location}
	 */
	public Location getCenter();

	/**
	 * 
	 * @return
	 */
	public int getCaptureSeconds();

	/**
	 * 
	 * @return commands
	 */
	public List<String> getCommands();

	/**
	 * 
	 * @return itemstacks
	 */
	public List<ItemStack> getItemStacks();

	/**
	 * 
	 * @return {@link LootType}
	 */
	public LootType getLootType();

	/**
	 * 
	 * @param type
	 */
	public void setLootType(LootType type);

	/**
	 * Move koth
	 * 
	 * @param minLocation
	 * @param maxLocation
	 */
	public void move(Location minLocation, Location maxLocation);

	/**
	 * 
	 * @return true if is enable
	 */
	public boolean isEnable();

	/**
	 * 
	 * @return true if is in cooldown
	 */
	public boolean isCooldown();

	/**
	 * Spawn koth
	 * 
	 * @param sender
	 * @param now
	 */
	public void spawn(CommandSender sender, boolean now);

	/**
	 * 
	 * @param player
	 * @param factionListener
	 */
	public void playerMove(Player player, FactionListener factionListener);
	
	/**
	 * 
	 * @return
	 */
	public CollectionConsumer<Player> onScoreboard();

	/**
	 * 
	 * @param command
	 */
	public void addCommand(String command);

	/**
	 * 
	 * @param id
	 */
	public void removeCommand(int id);
}
