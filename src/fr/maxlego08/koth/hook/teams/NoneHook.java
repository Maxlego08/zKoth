package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.api.KothTeam;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class NoneHook implements KothTeam {


    @Override
    public String getTeamName(Player player) {
        return player.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(Player player) {
        return Collections.singletonList(player);
    }

    @Override
    public String getLeaderName(Player player) {
        return player.getName();
    }

    @Override
    public String getTeamId(Player player) {
        return player.getUniqueId().toString();
    }
}
