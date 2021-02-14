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
	public static boolean enableStartCapMessage = true;
	public static boolean enableLooseCapMessage = true;
	public static String scoreboardTitle = "§fzKoth";
	public static List<String> scoreboard = new ArrayList<String>();
	public static List<String> scoreboardCooldown = new ArrayList<String>();
	public static long schedulerMillisecond = 1000;
	public static int cooldownInSecond = 300;
	public static int removeChestSec = 120;

	static {

		scoreboard.add("§r");
		scoreboard.add("§fKoth: §b%name%");
		scoreboard.add("§fCoordinate: §b%x% %y% %z%");
		scoreboard.add("§fFaction: §b%faction%");
		scoreboard.add("§0");
		scoreboard.add("§fTime: §d%capture%");
		scoreboard.add("§1");
		scoreboard.add("§fhttps://groupez.xyz");
		
		scoreboardCooldown.add("§r");
		scoreboardCooldown.add("§fKoth: §b%name%");
		scoreboardCooldown.add("§fCoordinate: §b%x% %y% %z%");
		scoreboardCooldown.add("§fStarts in: §d%capture%");
		scoreboardCooldown.add("§1");
		scoreboardCooldown.add("§fhttps://groupez.xyz");
		
	}

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

	public void save(Persist persist) {
		persist.save(getInstance());
	}

	public void load(Persist persist) {
		persist.loadOrSaveDefault(getInstance(), Config.class);
	}

}
