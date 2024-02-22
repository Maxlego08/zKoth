package fr.maxlego08.koth.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public interface KothTeam extends Listener {

    String getFactionTag(Player player);

    List<Player> getOnlinePlayer(Player player);

    String getLeaderName(Player player);

    String getTeamId(Player player);

}
