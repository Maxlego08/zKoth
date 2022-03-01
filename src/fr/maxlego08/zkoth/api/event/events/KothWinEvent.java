package fr.maxlego08.zkoth.api.event.events;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.event.CancelledKothEvent;

public class KothWinEvent extends CancelledKothEvent {

	private final Koth koth;
	private final Player player;

	/**
	 * @param koth
	 * @param player
	 */
	public KothWinEvent(Koth koth, Player player) {
		super();
		this.koth = koth;
		this.player = player;
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

}
