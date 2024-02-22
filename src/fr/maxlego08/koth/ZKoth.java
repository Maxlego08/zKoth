package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.zcore.utils.Cuboid;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ZKoth implements Koth {

    private final String fileName;
    private String name;
    private int captureSeconds;
    private Location minLocation;
    private Location maxLocation;
    private List<String> startCommands = new ArrayList<>();
    private List<String> endCommands = new ArrayList<>();

    public ZKoth(String fileName, String name, int captureSeconds, Location minLocation, Location maxLocation, List<String> startCommands, List<String> endCommands) {
        this.fileName = fileName;
        this.name = name;
        this.captureSeconds = captureSeconds;
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
        this.startCommands = startCommands;
        this.endCommands = endCommands;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Location getMinLocation() {
        return this.minLocation;
    }

    @Override
    public Location getMaxLocation() {
        return this.maxLocation;
    }

    @Override
    public Cuboid getCuboid() {
        return new Cuboid(this.maxLocation, this.minLocation);
    }

    @Override
    public Location getCenter() {
        Cuboid cuboid = getCuboid();
        return cuboid.getCenter();
    }

    @Override
    public List<String> getStartCommands() {
        return this.startCommands;
    }

    @Override
    public List<String> getEndCommands() {
        return this.endCommands;
    }

    @Override
    public void move(Location minLocation, Location maxLocation) {
        this.minLocation = minLocation;
        this.maxLocation = maxLocation;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCaptureSeconds(int captureSeconds) {
        this.captureSeconds = captureSeconds;
    }

    @Override
    public int getCaptureSeconds() {
        return captureSeconds;
    }
}
