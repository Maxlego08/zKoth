package fr.maxlego08.zkoth.api.event.events;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.event.CancelledKothEvent;

public class KothStartEvent extends CancelledKothEvent {

	private final Koth koth;

	/**
	 * @param koth
	 */
	public KothStartEvent(Koth koth) {
		super();
		this.koth = koth;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

}
