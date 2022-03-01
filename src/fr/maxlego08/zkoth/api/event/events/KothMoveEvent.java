package fr.maxlego08.zkoth.api.event.events;

import org.bukkit.Location;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.event.CancelledKothEvent;

public class KothMoveEvent extends CancelledKothEvent {

	private final Koth koth;
	private Location newMaxLocation;
	private Location newMinLocation;

	/**
	 * @param koth
	 * @param newMaxLocation
	 * @param newMinLocation
	 */
	public KothMoveEvent(Koth koth, Location newMaxLocation, Location newMinLocation) {
		super();
		this.koth = koth;
		this.newMaxLocation = newMaxLocation;
		this.newMinLocation = newMinLocation;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

	/**
	 * @return the newMaxLocation
	 */
	public Location getNewMaxLocation() {
		return newMaxLocation;
	}

	/**
	 * @return the newMinLocation
	 */
	public Location getNewMinLocation() {
		return newMinLocation;
	}

	/**
	 * @param newMaxLocation
	 *            the newMaxLocation to set
	 */
	public void setNewMaxLocation(Location newMaxLocation) {
		this.newMaxLocation = newMaxLocation;
	}

	/**
	 * @param newMinLocation
	 *            the newMinLocation to set
	 */
	public void setNewMinLocation(Location newMinLocation) {
		this.newMinLocation = newMinLocation;
	}

}
