package fr.maxlego08.koth;

import org.bukkit.Location;

import fr.maxlego08.koth.zcore.utils.Cuboid;

public class Koth {

	private final String name;
	private Location pos1;
	private Location pos2;
	private transient boolean isEnable;
	private transient boolean isCooldown;
	private transient String currentPlayer;
	private transient Cuboid cuboid;

	public Koth(String name) {
		super();
		this.name = name;
	}

	/**
	 * @param pos1
	 *            the pos1 to set
	 */
	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}

	/**
	 * @param pos2
	 *            the pos2 to set
	 */
	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pos1
	 */
	public Location getPos1() {
		return pos1;
	}

	/**
	 * @return the pos2
	 */
	public Location getPos2() {
		return pos2;
	}

	/**
	 * @return the isEnable
	 */
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @return the isCooldown
	 */
	public boolean isCooldown() {
		return isCooldown;
	}

	/**
	 * @return the currentPlayer
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @return the cuboid
	 */
	public Cuboid getCuboid() {
		return cuboid;
	}

	/**
	 * 
	 * @return
	 */
	public String toFirstLocation() {
		return pos1.getBlockX() + ", " + pos1.getBlockY() + ", " + pos1.getBlockZ();
	}

	/**
	 * 
	 * @return
	 */
	public String toSecondLocation() {
		return pos2.getBlockX() + ", " + pos2.getBlockY() + ", " + pos2.getBlockZ();
	}

}
