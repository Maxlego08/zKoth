package fr.maxlego08.koth.factions;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.MPlayer;

import fr.maxlego08.koth.FactionListener;

public class MassiveFaction implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		return MPlayer.get(player).getFaction().getName();
	}

	@Override
	public String getFactionTag(String player) {
		return MPlayer.get(player).getFaction().getName();
	}

	@Override
	public List<Player> getOnlinePlayer(String player) {
		return MPlayer.get(player).getFaction().getOnlinePlayers();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return MPlayer.get(player).getFaction().getOnlinePlayers();
	}

}
