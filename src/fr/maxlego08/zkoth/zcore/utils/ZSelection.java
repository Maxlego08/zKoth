package fr.maxlego08.zkoth.zcore.utils;

import org.bukkit.Location;
import org.bukkit.event.block.Action;

import fr.maxlego08.zkoth.api.Selection;

public class ZSelection implements Selection {

	private Location rightLocation;
	private Location leftLocation;

	@Override
	public Location getRightLocation() {
		return this.rightLocation;
	}

	@Override
	public Location getLeftLocation() {
		return this.leftLocation;
	}

	/**
	 * @param rightLocation
	 *            the rightLocation to set
	 */
	private void setRightLocation(Location rightLocation) {
		this.rightLocation = rightLocation;
	}

	/**
	 * @param leftLocation
	 *            the leftLocation to set
	 */
	private void setLeftLocation(Location leftLocation) {
		this.leftLocation = leftLocation;
	}

	@Override
	public void action(Action action, Location location) {
		switch (action) {
		case LEFT_CLICK_BLOCK:
			this.setLeftLocation(location);
			break;
		case RIGHT_CLICK_BLOCK:
			this.setRightLocation(location);
			break;
		default:
		case LEFT_CLICK_AIR:
		case PHYSICAL:
		case RIGHT_CLICK_AIR:
			break;
		}
	}

	@Override
	public boolean isValid() {
		return this.leftLocation != null && this.rightLocation != null;
	}

}
