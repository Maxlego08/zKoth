package fr.maxlego08.koth;

import fr.maxlego08.koth.command.CommandManager;
import fr.maxlego08.koth.inventory.InventoryManager;
import fr.maxlego08.koth.listener.AdapterListener;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.save.Lang;
import fr.maxlego08.koth.zcore.ZPlugin;
import fr.maxlego08.koth.zcore.utils.builder.CooldownBuilder;

public class ZKoth extends ZPlugin {

	private CommandManager commandManager;
	private InventoryManager inventoryManager;
	private KothManager manager;

	@Override
	public void onEnable() {

		instance = this;
		
		preEnable();

		commandManager = new CommandManager(this);
		commandManager.registerCommands();
		
		if (!isEnabled())
			return;
		inventoryManager = InventoryManager.getInstance();

		/* Add Listener */

		addListener(new AdapterListener(this));
		addListener(inventoryManager);
		addListener(manager = new KothManager());
		manager.eneableFaction();

		/* Add Saver */

		addSave(Config.getInstance());
		addSave(Lang.getInstance());
		addSave(new CooldownBuilder());

		getSavers().forEach(saver -> saver.load(getPersist()));

		postEnable();

	}

	@Override
	public void onDisable() {

		preDisable();

		getSavers().forEach(saver -> saver.save(getPersist()));

		postDisable();

	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	/**
	 * static Singleton instance.
	 */
	private static volatile ZKoth instance;


	/**
	 * Return a singleton instance of Template.
	 */
	public static ZKoth getInstance() {
		return instance;
	}
	
	public KothManager getManager() {
		return manager;
	}
	
}
