package fr.maxlego08.zkoth.hooks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;

public class NoFaction implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		return player.getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return Arrays.asList(player);
	}

}
