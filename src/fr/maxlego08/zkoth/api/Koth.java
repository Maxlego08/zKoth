package fr.maxlego08.zkoth.api;

import org.bukkit.Location;

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
	 * @return {@link Location}
	 */
	public Location getCenter();
	
	/**
	 * 
	 * @return
	 */
	public int getCaptureSeconds();
	
}
