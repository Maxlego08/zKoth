package fr.maxlego08.koth.factions;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.maxlego08.koth.FactionListener;

public class NoFaction implements FactionListener {

	public NoFaction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFactionTag(Player player) {
		return player.getName();
	}

	@Override
	public String getFactionTag(String player) {
		return player;
	}

	@Override
	public List<Player> getOnlinePlayer(String player) {
		return Arrays.asList(Bukkit.getPlayer(player));
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return Arrays.asList(player);
	}

}
