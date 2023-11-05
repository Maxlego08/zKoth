package fr.maxlego08.zkoth.hooks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.booksaw.betterTeams.Team;

import fr.maxlego08.zkoth.api.FactionListener;

public class BetterTeamHook implements FactionListener{

	@Override
	public String getFactionTag(Player player) {
		Team team = Team.getTeam(player);
		return team == null ? player.getName() : team.getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		Team team = Team.getTeam(player);
		return team == null ? Arrays.asList(player) : team.getOnlineMembers();
	}

}
