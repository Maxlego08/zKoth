package fr.maxlego08.zkoth.hooks;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.MPlayer;

import fr.maxlego08.zkoth.api.FactionListener;

public class FactionMassiveHook implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		return MPlayer.get(player).getFaction().getName();
	}


	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return MPlayer.get(player).getFaction().getOnlinePlayers();
	}

}
