package fr.maxlego08.zkoth.zcore.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;

import fr.maxlego08.zkoth.api.Selection;

public class ZSelection implements Selection {

	private Location rightLocation;
	private Location leftLocation;
	private LivingEntity rightEntity;
	private LivingEntity leftEntity;

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
	public void action(Action action, Location location, LivingEntity livingEntity) {
		switch (action) {
		case LEFT_CLICK_BLOCK:
			this.setLeftLocation(location);
			if (this.leftEntity != null && this.leftEntity.isValid()) {
				this.leftEntity.remove();
			}
			this.leftEntity = livingEntity;
			break;
		case RIGHT_CLICK_BLOCK:
			this.setRightLocation(location);
			if (this.rightEntity != null && this.rightEntity.isValid()) {
				this.rightEntity.remove();
			}
			this.rightEntity = livingEntity;
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

	@Override
	public boolean isCorrect() {
		return isValid() && Math.abs(this.leftLocation.getY() - this.rightLocation.getY()) > 1;
	}

	@Override
	public LivingEntity getRightEntity() {
		return this.rightEntity;
	}

	@Override
	public LivingEntity getLeftEntity() {
		return this.leftEntity;
	}

	@Override
	public void clear() {
		if (this.rightEntity != null && this.rightEntity.isValid()) {
			this.rightEntity.remove();
		}
		if (this.leftEntity != null && this.leftEntity.isValid()) {
			this.leftEntity.remove();
		}

	}

}
