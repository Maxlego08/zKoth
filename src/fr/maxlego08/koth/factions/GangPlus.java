package fr.maxlego08.koth.factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.FactionListener;
import net.brcdev.gangs.GangsPlugin;

public class GangPlus implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		return GangsPlugin.getInstance().getGangManager().getPlayersGang(player).getName();
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
		return new ArrayList<Player>(
				GangsPlugin.getInstance().getGangManager().getPlayersGang(player).getOnlineMembers());
	}

}
