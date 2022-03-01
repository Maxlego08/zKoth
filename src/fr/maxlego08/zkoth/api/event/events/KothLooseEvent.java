package fr.maxlego08.zkoth.api.event.events;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.event.CancelledKothEvent;

public class KothLooseEvent extends CancelledKothEvent {

	private final Player player;
	private final Koth koth;

	/**
	 * @param player
	 * @param koth
	 */
	public KothLooseEvent(Player player, Koth koth) {
		super();
		this.player = player;
		this.koth = koth;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the koth
	 */
	public Koth getKoth() {
		return koth;
	}

}
