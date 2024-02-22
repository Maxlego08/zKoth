package fr.maxlego08.koth.api;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.zcore.utils.Cuboid;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface Koth {

    String getFileName();

    KothType getKothType();

    String getName();

    void setName(String name);

    Location getMinLocation();

    Location getMaxLocation();

    Cuboid getCuboid();

    Location getCenter();

    List<String> getStartCommands();

    List<String> getEndCommands();

    void move(Location minLocation, Location maxLocation);

    int getCaptureSeconds();

    void setCaptureSeconds(int captureSeconds);

    ScoreboardConfiguration getCooldownScoreboard();

    ScoreboardConfiguration getStartScoreboard();

    KothStatus getStatus();

    void spawn(CommandSender sender, boolean now);

    void spawn(boolean now);

    void stop(CommandSender sender);

    void playerMove(Player player, KothTeam kothTeam);

    int getCooldownStart();

    int getStopAfterSeconds();

    boolean isEnableStartCapMessage();

    boolean isEnableLooseCapMessage();
    boolean isEnableEverySecondsCapMessage();
}
