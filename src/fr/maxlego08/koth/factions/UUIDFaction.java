package fr.maxlego08.koth.factions;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayers;

import fr.maxlego08.koth.FactionListener;

public class UUIDFaction implements FactionListener {

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
