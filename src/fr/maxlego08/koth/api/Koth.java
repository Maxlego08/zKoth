package fr.maxlego08.koth.api;

import fr.maxlego08.koth.zcore.utils.Cuboid;
import org.bukkit.Location;

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
}
