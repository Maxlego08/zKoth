package fr.maxlego08.koth.event;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.FactionListener;
import fr.maxlego08.koth.Koth;

public class KothStartEvent extends KothEvent {

	private final Player player;
	private final Koth koth;
	private final FactionListener factionListener;

	public KothStartEvent(Player player, Koth koth, FactionListener factionListener) {
		super();
		this.player = player;
		this.koth = koth;
		this.factionListener = factionListener;
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

	/**
	 * @return the factionListener
	 */
	public FactionListener getFactionListener() {
		return factionListener;
	}

}
