package fr.maxlego08.zkoth.save;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.maxlego08.zkoth.zcore.utils.storage.Persist;
import fr.maxlego08.zkoth.zcore.utils.storage.Saveable;

public class Config implements Saveable {

	public static long playerMoveEventCooldown = 50;
	public static List<Integer> displayMessageCooldown = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static List<Integer> displayMessageKothCap = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static boolean enableScoreboard = false;
	public static boolean enableVersionChecker = true;
	public static boolean useNoFactionHook = false;
	public static boolean enableStartCapMessage = true;
	public static boolean enableLooseCapMessage = true;
	public static boolean enableEverySecondsCapMessage = false;
	public static String scoreboardTitle = "§f§l⌈ §7§ozKoth §f§l⌋";
	public static List<String> scoreboard = new ArrayList<String>();
	public static List<String> scoreboardCooldown = new ArrayList<String>();
	public static long schedulerMillisecond = 1000;
	public static int cooldownInSecond = 300;
	public static int removeChestSec = 120;
	public static String percentPrecision = "#.#";
	public static ProgressBar progressBarPoints = new ProgressBar(10, '|', "§b", "§7");
	public static ProgressBar progressBarTimer = new ProgressBar(10, '|', "§b", "§7");
	public static ProgressBar progressBarClassic = new ProgressBar(10, '|', "§b", "§7");
	
	public static ReplaceConfig replaceNoFaction = new ReplaceConfig("§2Wilderness", "§3NoFaction");
	public static boolean enableReplaceNoFaction = false;

	static {

		scoreboard.add("§r");
		scoreboard.add("§6§l⟣ §fKoth: §b%name%");
		scoreboard.add("§6§l⟣ §fCoord: §b%x% %y% %z%");
		scoreboard.add("§6§l⟣ §fFaction: §b%faction%");
		scoreboard.add("§0");
		scoreboard.add("§6§l⟣ §fTime: §d%capture%");
		scoreboard.add("§b");
		scoreboard.add("§6§l⟣ §fPoints: §b%points%§f/§a%maxPoints%");
		scoreboard.add("§6§l⟣ §fPercent: §b%pointsPercent%§7%");
		scoreboard.add("§6§l⟣ §fTimer: §b%timer%");
		scoreboard.add("§6§l⟣ §fTimer: §b%timerSeconds%§f/§a%maxTimerSeconds%");
		scoreboard.add("§6§l⟣ §fPercent: §b%timerPercent%§7%");
		scoreboard.add("§1");
		scoreboard.add("§6§l⟣ §fhttps://groupez.dev");

		scoreboardCooldown.add("§r");
		scoreboardCooldown.add("§6§l⟣ §fKoth: §b%name%");
		scoreboardCooldown.add("§6§l⟣ §fCoordinate: §b%x% %y% %z%");
		scoreboardCooldown.add("§6§l⟣ §fStarts in: §d%capture%");
		scoreboardCooldown.add("§1");
		scoreboardCooldown.add("§6§l⟣ §fhttps://groupez.dev");

	}

	/**
	 * static Singleton instance.
	 */
	private static volatile Config instance;
	public static boolean enableDebug;
	public static boolean spawnKothWithSchedulerNow = true;
	public static String defaultNoKoth = "No KOTH";

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

	public void save(Persist persist) {
		persist.save(getInstance());
	}

	public void load(Persist persist) {
		persist.loadOrSaveDefault(getInstance(), Config.class);
	}

}
