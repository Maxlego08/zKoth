package fr.maxlego08.koth.event;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.FactionListener;
import fr.maxlego08.koth.Koth;

public class KothWinEvent extends KothEvent {

	private final Koth koth;
	private final Player player;
	private final FactionListener factionListener;

	public KothWinEvent(Koth koth, Player player, FactionListener factionListener) {
		super();
		this.koth = koth;
		this.player = player;
		this.factionListener = factionListener;
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
	 * @return the factionListener
	 */
	public FactionListener getFactionListener() {
		return factionListener;
	}

}
