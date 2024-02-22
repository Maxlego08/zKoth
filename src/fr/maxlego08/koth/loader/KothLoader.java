package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class KothLoader extends ZUtils implements Loader<Koth> {

    private final Loader<Location> locationLoader = new LocationLoader();
    private final Loader<ScoreboardConfiguration> scoreboardLoaderLoader = new ScoreboardLoader();

    @Override
    public Koth load(YamlConfiguration configuration, String path, File file) {

        String fileName = getFileNameWithoutExtension(file);
        KothType kothType = KothType.valueOf(configuration.getString("type", KothType.CAPTURE.name()).toUpperCase());
        String name = configuration.getString("name");
        int captureSeconds = configuration.getInt("capture");
        List<String> startCommands = configuration.getStringList("startCommands");
        List<String> endCommands = configuration.getStringList("endCommands");
        Location minLocation = locationLoader.load(configuration, "minLocation.", file);
        Location manLocation = locationLoader.load(configuration, "maxLocation.", file);
        ScoreboardConfiguration cooldownScoreboard = scoreboardLoaderLoader.load(configuration, "scoreboard.cooldown.", file);
        ScoreboardConfiguration startScoreboard = scoreboardLoaderLoader.load(configuration, "scoreboard.start.", file);

        return new ZKoth(fileName, kothType, name, captureSeconds, minLocation, manLocation, startCommands, endCommands, cooldownScoreboard, startScoreboard);
    }

    @Override
    public void save(Koth koth, YamlConfiguration configuration, String path) {

        configuration.set("type", koth.getKothType().name());
        configuration.set("name", koth.getName());
        configuration.set("capture", koth.getCaptureSeconds());
        configuration.set("startCommands", koth.getStartCommands());
        configuration.set("endCommands", koth.getEndCommands());
        locationLoader.save(koth.getMinLocation(), configuration, "minLocation.");
        locationLoader.save(koth.getMaxLocation(), configuration, "manLocation.");
        scoreboardLoaderLoader.save(koth.getCooldownScoreboard(), configuration, "scoreboard.cooldown.");
        scoreboardLoaderLoader.save(koth.getStartScoreboard(), configuration, "scoreboard.start.");

    }
}
