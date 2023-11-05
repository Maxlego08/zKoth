package fr.maxlego08.zkoth.save;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.zcore.utils.ZUtils;

public class Config extends ZUtils {

	public static List<Integer> displayMessageCooldown = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static List<Integer> displayMessageKothCap = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static long schedulerMillisecond = 1000;
	public static int cooldownInSecond = 300;
	public static int removeChestSec = 120;
	public static int forceStoKothAfterSeconds = 600;
	public static String percentPrecision = "#.#";

	public static ProgressBar progressBarPoints = new ProgressBar(10, '|', "§b", "§7");
	public static ProgressBar progressBarTimer = new ProgressBar(10, '|', "§b", "§7");
	public static ProgressBar progressBarClassic = new ProgressBar(10, '|', "§b", "§7");

	public static ReplaceConfig replaceNoFaction = new ReplaceConfig("§2Wilderness", "§3NoFaction");
	public static boolean enableReplaceNoFaction = false;

	public static boolean enableVersionChecker = true;
	public static boolean useNoFactionHook = false;
	public static boolean enableStartCapMessage = true;
	public static boolean enableLooseCapMessage = true;
	public static boolean enableEverySecondsCapMessage = false;

	public static boolean enableScoreboard = false;
	public static long playerMoveEventCooldown = 50;

	public static String scoreboardTitle = "§f§l⌈ §7§ozKoth §f§l⌋";
	public static List<String> scoreboard = new ArrayList<String>();
	public static List<String> scoreboardCooldown = new ArrayList<String>();

	public static Material noOneCapturingMaterial = Material.DIAMOND_BLOCK;
	public static Material onePersonneCapturingMaterial = Material.GOLD_BLOCK;
	public static Material multiPersonneCapturingMaterial = Material.EMERALD_BLOCK;
	public static boolean enableDebug = false;

	public static String defaultNoKoth = "No KOTH";

	public static List<HologramConfig> hologramConfigs = new ArrayList<>();

	/**
	 * static Singleton instance.
	 */
	private static volatile Config instance;

	/**
	 * Private constructor for singleton.
	 */
	private Config() {
	}

	/**
	 * Return a singleton instance of Config.
	 */
	public static Config getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (Config.class) {
				if (instance == null) {
					instance = new Config();
				}
			}
		}
		return instance;
	}

	public void load(ZKothPlugin plugin) {

		YamlConfiguration configuration = (YamlConfiguration) plugin.getConfig();

		progressBarPoints = new ProgressBar(configuration, "progressBarPoints.");
		progressBarTimer = new ProgressBar(configuration, "progressBarTimer.");
		progressBarClassic = new ProgressBar(configuration, "progressBarClassic.");

		enableScoreboard = configuration.getBoolean("enableScoreboard", false);
		enableVersionChecker = configuration.getBoolean("enableVersionChecker", false);
		useNoFactionHook = configuration.getBoolean("useNoFactionHook", false);
		enableStartCapMessage = configuration.getBoolean("enableStartCapMessage", false);
		enableLooseCapMessage = configuration.getBoolean("enableLooseCapMessage", false);
		enableEverySecondsCapMessage = configuration.getBoolean("enableEverySecondsCapMessage", false);
		enableReplaceNoFaction = configuration.getBoolean("enableReplaceNoFaction.enable", false);
		enableDebug = configuration.getBoolean("enableDebug", false);
		replaceNoFaction = new ReplaceConfig(configuration);

		playerMoveEventCooldown = configuration.getInt("playerMoveEventCooldown", 50);

		scoreboardTitle = color(configuration.getString("scoreboardTitle"));
		scoreboard = color(configuration.getStringList("scoreboard"));
		scoreboardCooldown = color(configuration.getStringList("scoreboardCooldown"));

		displayMessageCooldown = configuration.getIntegerList("displayMessageCooldown");
		displayMessageKothCap = configuration.getIntegerList("displayMessageKothCap");

		schedulerMillisecond = configuration.getLong("schedulerMillisecond", 1000L);
		cooldownInSecond = configuration.getInt("cooldownInSecond", 300);
		removeChestSec = configuration.getInt("removeChestSec", 120);
		forceStoKothAfterSeconds = configuration.getInt("forceStoKothAfterSeconds", 300);

		percentPrecision = configuration.getString("percentPrecision", "#.#");
		defaultNoKoth = configuration.getString("defaultNoKoth", "No Koth");
		noOneCapturingMaterial = Material.valueOf(configuration.getString("noOneCapturingMaterial", "STONE"));
		onePersonneCapturingMaterial = Material
				.valueOf(configuration.getString("onePersonneCapturingMaterial", "STONE"));
		multiPersonneCapturingMaterial = Material
				.valueOf(configuration.getString("multiPersonneCapturingMaterial", "STONE"));

		hologramConfigs.clear();
		ConfigurationSection configurationSection = configuration.getConfigurationSection("holograms.");
		if (configurationSection != null) {
			for (String key : configurationSection.getKeys(false)) {
				hologramConfigs.add(new HologramConfig(configuration, "holograms." + key + "."));
			}
		}
	}

}
