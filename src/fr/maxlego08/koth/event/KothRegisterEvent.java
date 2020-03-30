/**
 * 
 */
package fr.maxlego08.koth.event;

import fr.maxlego08.koth.FactionListener;

/**
 * @author Maxlego08
 * Permet de register le faction listener
 */
public class KothRegisterEvent extends KothEvent {

	private FactionListener factionListener;

	public KothRegisterEvent(FactionListener factionListener) {
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
