package fr.maxlego08.koth.factions;

import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.FactionListener;
import fr.pridenetwork.faction.FPlayers;

public class PrideFaction implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		return FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
	}

	@Override
	public String getFactionTag(String player) {
		return null;
	}

	@Override
	public List<Player> getOnlinePlayer(String player) {
		return null;
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return FPlayers.getInstance().getByPlayer(player).getFaction().getOnlinePlayers();
	}

}
