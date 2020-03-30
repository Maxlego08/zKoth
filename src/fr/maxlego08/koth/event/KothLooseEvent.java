package fr.maxlego08.koth.event;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.Koth;

public class KothLooseEvent extends KothEvent {

	private final Koth koth;
	private final Player player;

	public KothLooseEvent(Koth koth, Player player) {
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
