package fr.maxlego08.koth.hook.teams;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.event.FactionDisbandEvent;
import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.List;

public class SaberFactionHook implements KothTeam {

    private final KothPlugin plugin;

    public SaberFactionHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTeamName(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
    }

    @Override
    public List<Player> getOnlinePlayer(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFaction().getOnlinePlayers();
    }

    @Override
    public String getLeaderName(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFaction().getFPlayerAdmin().getName();
    }

    @Override
    public String getTeamId(Player player) {
        return FPlayers.getInstance().getByPlayer(player).getFactionId();
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        this.plugin.getStorageManager().onTeamDisband(event.getFaction().getId());
    }
}
