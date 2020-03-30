package fr.maxlego08.koth.event;

import fr.maxlego08.koth.Koth;
import fr.maxlego08.koth.zcore.utils.Cuboid;

public class KothStopEvent extends KothEvent {

	private final Cuboid location;
	private final Koth koth;

	public KothStopEvent(Cuboid location, Koth koth) {
		super();
		this.location = location;
		this.koth = koth;
	}

	/**
	 * @return the location
	 */
	public Cuboid getLocation() {
		return location;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

}
