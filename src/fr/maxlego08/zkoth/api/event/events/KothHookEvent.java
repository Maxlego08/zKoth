package fr.maxlego08.zkoth.api.event.events;

import fr.maxlego08.zkoth.api.FactionListener;
import fr.maxlego08.zkoth.api.event.KothEvent;

public class KothHookEvent extends KothEvent {

	private FactionListener factionListener;

	/**
	 * @param factionListener
	 */
	public KothHookEvent(FactionListener factionListener) {
		super();
		this.factionListener = factionListener;
	}

	/**
	 * @return the factionListener
	 */
	public FactionListener getFactionListener() {
		return factionListener;
	}

	/**
	 * @param factionListener
	 *            the factionListener to set
	 */
	public void setFactionListener(FactionListener factionListener) {
		this.factionListener = factionListener;
	}

}
