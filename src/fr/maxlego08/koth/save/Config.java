package fr.maxlego08.koth.save;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import fr.maxlego08.koth.KothLoot;
import fr.maxlego08.koth.zcore.enums.ChatType;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.zcore.logger.Logger.LogType;
import fr.maxlego08.koth.zcore.utils.ItemDecoder;
import fr.maxlego08.koth.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Saveable;

public class Config implements Saveable {

	/**
	 * static Singleton instance.
	 */
	private static volatile Config instance;
	public static int cooldownInSecond = 300;
	public static List<Integer> displayMessageCooldown = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static List<Integer> displayMessageKothCap = Arrays.asList(300, 120, 60, 30, 10, 5, 4, 3, 2, 1);
	public static int defaultCap = 60;
	public static KothLoot loot = KothLoot.COMMAND;
	public static boolean giveCommandToFaction = false;
	public static boolean disableJoinMessage = false;
	public static List<String> commands = new ArrayList<String>();
	public static List<String> itemstacks = new ArrayList<String>();
	public static int removeChestSec = 120;
	public static ChatType messageInformationCapture = ChatType.ACTION;

	static {

		commands.add("bc %player% vient de gagner le koth %name% !");

		itemstacks.add(ItemDecoder.serializeItemStack(new ItemBuilder(Material.DIAMOND, 32, "§ezKoth").build()));

	}

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

		if (ItemDecoder.getNMSVersion() == 1.7) {

			if (messageInformationCapture.equals(ChatType.ACTION)) {

				messageInformationCapture = ChatType.MESSAGE;
				Logger.info(
						"The messages are automatically put in the chat, your version of minecraft does not support the action bar.",
						LogType.WARNING);

			}

		}

	}

}
