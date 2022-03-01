package fr.maxlego08.zkoth.api.event.events;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.event.KothEvent;

public class KothCapEvent extends KothEvent {

	private final Koth koth;
	private final Player player;
	private final int timer;
	private final String factionName;

	/**
	 * @param koth
	 * @param player
	 * @param timer
	 * @param factionName
	 */
	public KothCapEvent(Koth koth, Player player, int timer, String factionName) {
		super();
		this.koth = koth;
		this.player = player;
		this.timer = timer;
		this.factionName = factionName;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the timer
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * @return the factionName
	 */
	public String getFactionName() {
		return factionName;
	}

}
