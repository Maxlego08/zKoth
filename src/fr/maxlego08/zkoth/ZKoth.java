package fr.maxlego08.zkoth;

import org.bukkit.Location;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;

public class ZKoth implements Koth {

	private final String name;
	private final int captureSeconds;
	private final Location minLocation;
	private final Location maxLocation;

	/**
	 * @param name
	 * @param captureSeconds
	 * @param minLocation
	 * @param maxLocation
	 */
	public ZKoth(String name, int captureSeconds, Location minLocation, Location maxLocation) {
		super();
		this.name = name;
		this.captureSeconds = captureSeconds;
		this.minLocation = minLocation;
		this.maxLocation = maxLocation;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getMinLocation() {
		return this.minLocation;
	}

	@Override
	public Location getMaxLocation() {
		return this.maxLocation;
	}

	@Override
	public Cuboid getCuboid() {
		return new Cuboid(this.maxLocation, this.minLocation);
	}

	@Override
	public Location getCenter() {
		Cuboid cuboid = getCuboid();
		return cuboid.getCenter();
	}

	@Override
	public int getCaptureSeconds() {
		return this.captureSeconds;
	}

}
