package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.api.discord.DiscordWebhookConfig;
import fr.maxlego08.koth.api.utils.HologramConfig;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class KothLoader extends ZUtils implements Loader<Koth> {

    private final KothPlugin plugin;
    private final Loader<Location> locationLoader = new LocationLoader();
    private final Loader<ScoreboardConfiguration> scoreboardLoaderLoader = new ScoreboardLoader();
    private final Loader<HologramConfig> hologramConfigLoader = new HologramLoader();

    public KothLoader(KothPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Koth load(YamlConfiguration configuration, String path, File file) {

        String fileName = getFileNameWithoutExtension(file);
        KothType kothType = KothType.valueOf(configuration.getString("type", KothType.CAPTURE.name()).toUpperCase());
        String name = configuration.getString("name");
        int captureSeconds = configuration.getInt("capture");
        int cooldownStart = configuration.getInt("cooldownStart");
        int stopAfterSeconds = configuration.getInt("stopAfterSeconds");
        boolean enableStartCapMessage = configuration.getBoolean("enableStartCapMessage", true);
        boolean enableLooseCapMessage = configuration.getBoolean("enableLooseCapMessage", true);
        boolean enableEverySecondsCapMessage = configuration.getBoolean("enableEverySecondsCapMessage", false);
        List<String> startCommands = configuration.getStringList("startCommands");
        List<String> endCommands = configuration.getStringList("endCommands");
        Location minLocation = locationLoader.load(configuration, "minLocation.", file);
        Location maxLocation = locationLoader.load(configuration, "maxLocation.", file);
        ScoreboardConfiguration cooldownScoreboard = scoreboardLoaderLoader.load(configuration, "scoreboard.cooldown.", file);
        ScoreboardConfiguration startScoreboard = scoreboardLoaderLoader.load(configuration, "scoreboard.start.", file);
        HologramConfig hologramConfig = hologramConfigLoader.load(configuration, "hologram.", file);

        DiscordWebhookConfig discordWebhookConfig = new DiscordWebhookConfig(configuration);

        return new ZKoth(this.plugin, fileName, kothType, name, captureSeconds, minLocation, maxLocation, startCommands, endCommands, cooldownScoreboard,
                startScoreboard, cooldownStart, stopAfterSeconds, enableStartCapMessage, enableLooseCapMessage, enableEverySecondsCapMessage, hologramConfig, discordWebhookConfig);
    }

    @Override
    public void save(Koth koth, YamlConfiguration configuration, String path) {

        configuration.set("type", koth.getKothType().name());
        configuration.set("name", koth.getName());
        configuration.set("capture", koth.getCaptureSeconds());
        configuration.set("cooldownStart", koth.getCooldownStart());
        configuration.set("stopAfterSeconds", koth.getStopAfterSeconds());
        configuration.set("startCommands", koth.getStartCommands());
        configuration.set("endCommands", koth.getEndCommands());
        configuration.set("enableStartCapMessage", koth.isEnableStartCapMessage());
        configuration.set("enableLooseCapMessage", koth.isEnableLooseCapMessage());
        configuration.set("enableEverySecondsCapMessage", koth.isEnableEverySecondsCapMessage());
        locationLoader.save(koth.getMinLocation(), configuration, "minLocation.");
        locationLoader.save(koth.getMaxLocation(), configuration, "maxLocation.");
        scoreboardLoaderLoader.save(koth.getCooldownScoreboard(), configuration, "scoreboard.cooldown.");
        scoreboardLoaderLoader.save(koth.getStartScoreboard(), configuration, "scoreboard.start.");
        hologramConfigLoader.save(koth.getHologramConfig(), configuration, "hologram.");

    }
}
