package fr.maxlego08.koth;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;

import fr.maxlego08.koth.command.CommandManager;
import fr.maxlego08.koth.inventory.InventoryManager;
import fr.maxlego08.koth.listener.AdapterListener;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.save.Lang;
import fr.maxlego08.koth.scheduler.SchedulerManager;
import fr.maxlego08.koth.zcore.ZPlugin;
import fr.maxlego08.koth.zcore.utils.Metrics;
import fr.maxlego08.koth.zcore.utils.builder.CooldownBuilder;

public class ZKoth extends ZPlugin {

	private CommandManager commandManager;
	private InventoryManager inventoryManager;
	private SchedulerManager schedulerManager;
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
		addSave(schedulerManager = new SchedulerManager(manager));

		getSavers().forEach(saver -> saver.load(getPersist()));

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			KothPlaceholderExpansion expansion = new KothPlaceholderExpansion(manager, getDescription().getFullName());
			expansion.register();
		}

		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SingleLineChart("koths", new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				return manager.size();
			}
		}));

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

	public SchedulerManager getSchedulerManager() {
		return schedulerManager;
	}
}
