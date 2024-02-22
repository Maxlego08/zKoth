package fr.maxlego08.koth.hook.teams;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import net.william278.husktowns.api.HuskTownsAPI;
import net.william278.husktowns.town.Member;
import net.william278.husktowns.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HuskTownHook implements KothTeam {

    private final KothPlugin plugin;

    public HuskTownHook(KothPlugin plugin) {
        this.plugin = plugin;
    }

    private Optional<Town> getTown(Player player) {
        Optional<Member> optional = HuskTownsAPI.getInstance().getUserTown(player);
        if (optional.isPresent()) {
            Member member = optional.get();
            return Optional.of(member.town());
        }
        return Optional.empty();
    }

    @Override
    public String getFactionTag(Player player) {
        Optional<Town> optional = getTown(player);
        if (optional.isPresent()) return optional.get().getName();
        return player.getName();
    }

    @Override
    public List<Player> getOnlinePlayer(Player player) {
        Optional<Town> optional = getTown(player);
        return optional.map(town -> town.getMembers().keySet().stream().map(Bukkit::getOfflinePlayer)
                .filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList())).orElseGet(() -> Collections.singletonList(player));
    }

    @Override
    public String getLeaderName(Player player) {
        Optional<Town> optional = getTown(player);
        if (optional.isPresent()) return Bukkit.getOfflinePlayer(optional.get().getMayor()).getName();
        return player.getName();
    }

    @Override
    public String getTeamId(Player player) {
        Optional<Town> optional = getTown(player);
        return optional.map(town -> String.valueOf(town.getId())).orElseGet(player::getName);
    }

    @EventHandler
    public void onDelete(LandDeleteEvent event) {
        this.plugin.getStorageManager().onTeamDisband(String.valueOf(event.getLand().getId()));
    }

}
