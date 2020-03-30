package fr.maxlego08.koth.save;

import java.util.HashMap;
import java.util.Map;

import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Saveable;

public class Lang implements Saveable {

	public static Map<Message, String> messages = new HashMap<Message, String>();

	static {

		for (Message message : Message.values())
			messages.put(message, message.getMessage());

	}

	/**
	 * static Singleton instance.
	 */
	private static volatile Lang instance;

	/**
	 * Private constructor for singleton.
	 */
	private Lang() {
	}

	/**
	 * Return a singleton instance of Config.
	 */
	public static Lang getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (Lang.class) {
				if (instance == null) {
					instance = new Lang();
				}
			}
		}
		return instance;
	}

	public void save(Persist persist) {
		for (Message message : Message.values())
			if (message.isUse())
				messages.putIfAbsent(message, message.getMessage());
		persist.save(getInstance());
	}

	public void load(Persist persist) {
		persist.loadOrSaveDefault(getInstance(), Lang.class);
		messages.forEach((key, value) -> key.setMessage(value));
	}

}
