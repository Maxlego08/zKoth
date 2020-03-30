package fr.maxlego08.koth.event;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.Koth;

public class KothStartEvent extends KothEvent {

	private final Player player;
	private final Koth koth;

	public KothStartEvent(Player player, Koth koth) {
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
