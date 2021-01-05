package fr.maxlego08.zkoth.hooks;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayers;

import fr.maxlego08.zkoth.api.FactionListener;

public class FactionUUIDHook implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		return FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return FPlayers.getInstance().getByPlayer(player).getFaction().getOnlinePlayers();
	}

}
