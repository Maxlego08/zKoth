package fr.maxlego08.zkoth.api;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;

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

}
