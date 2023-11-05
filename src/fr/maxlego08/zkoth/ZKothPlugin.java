package fr.maxlego08.zkoth;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.command.CommandManager;
import fr.maxlego08.zkoth.command.commands.CommandKoth;
import fr.maxlego08.zkoth.hologram.DecentHologram;
import fr.maxlego08.zkoth.hologram.EmptyHologram;
import fr.maxlego08.zkoth.hologram.ZHologram;
import fr.maxlego08.zkoth.inventory.InventoryManager;
import fr.maxlego08.zkoth.listener.AdapterListener;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.save.MessageLoader;
import fr.maxlego08.zkoth.scheduler.ZkothImplementation;
import fr.maxlego08.zkoth.scoreboard.ScoreBoardManager;
import fr.maxlego08.zkoth.scoreboard.implementations.FeatherBoardHook;
import fr.maxlego08.zkoth.scoreboard.implementations.SimpleBoardHook;
import fr.maxlego08.zkoth.scoreboard.implementations.SternalBoardHook;
import fr.maxlego08.zkoth.scoreboard.implementations.TabPremiumHook;
import fr.maxlego08.zkoth.scoreboard.implementations.TitleManagerHook;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.logger.Logger;
import fr.maxlego08.zkoth.zcore.logger.Logger.LogType;
import fr.maxlego08.zkoth.zcore.utils.plugins.Metrics;
import fr.maxlego08.zkoth.zcore.utils.plugins.Plugins;
import fr.maxlego08.zkoth.zcore.utils.plugins.VersionChecker;

/**
 * System to create your plugins very simply Projet:
 * https://github.com/Maxlego08/TemplatePlugin
 * 
 * @author Maxlego08
 *
 */
public class ZKothPlugin extends ZPlugin {

	private KothManager kothManager;
	private final MessageLoader messageLoader = new MessageLoader(this);
	private ZHologram hologram = new EmptyHologram();

	@Override
	public void onEnable() {

		preEnable();

		this.scoreboardManager = new ScoreBoardManager();
		this.kothManager = new ZKothManager(this.scoreboardManager);

		this.getServer().getServicesManager().register(KothManager.class, kothManager, this, ServicePriority.High);

		this.commandManager = new CommandManager(this);
		this.inventoryManager = InventoryManager.getInstance();

		/* Commands */

		this.registerCommand("zkoth", new CommandKoth(this), "koth");

		/* Add Listener */

		addListener(new AdapterListener(this));
		addListener(inventoryManager);
		addListener((ZKothManager) kothManager);

		/* Add Saver */

		saveDefaultConfig();
		addSave(this.messageLoader);

		if (this.isEnable(Plugins.FEATHERBOARD)) {
			this.scoreboardManager.setScoreboard(new FeatherBoardHook());
		}

		if (this.isEnable(Plugins.TAB)) {
			this.scoreboardManager.setScoreboard(new TabPremiumHook());
		}

		if (this.isEnable(Plugins.TITLEMANAGER)) {
			this.scoreboardManager.setScoreboard(new TitleManagerHook());
		}

		if (this.isEnable(Plugins.STERNALBOARD)) {
			Plugin plugin = this.getServer().getPluginManager().getPlugin("SternalBoard");
			this.scoreboardManager.setScoreboard(new SternalBoardHook(plugin));
		}

		if (this.isEnable(Plugins.SIMPLECORE)) {
			this.scoreboardManager.setScoreboard(new SimpleBoardHook(this));
		}

		this.scoreboardManager.setDefaultScoreboard();
		Logger.info("Load " + this.scoreboardManager.getScoreboard().getClass().getName() + " scoreboard manager");

		if (this.isEnable(Plugins.PLACEHOLDERAPI)) {
			Logger.info("Load PlaceHolderAPI", LogType.INFO);
			KothExpension expension = new KothExpension(this, kothManager);
			expension.register();
		}

		if (this.isEnable(Plugins.ZSCHEDULERS)) {
			Logger.info("Register zScheduler implementation", LogType.INFO);
			ZkothImplementation implementation = new ZkothImplementation(this);
			implementation.register();
		}

		if (this.isEnable(Plugins.DH)) {
			Logger.info("Register DecentHologram implementation", LogType.INFO);
			this.hologram = new DecentHologram();	
		}
		
		getSavers().forEach(saver -> saver.load(getPersist()));
		Config.getInstance().load(this);

		new Metrics(this, 6924);

		VersionChecker checker = new VersionChecker(this, 9);
		checker.useLastVersion();

		postEnable();
	}

	@Override
	public void onDisable() {

		preDisable();

		this.hologram.onDisable();
		this.scoreboardManager.setRunning(false);
		getSavers().forEach(saver -> saver.save(getPersist()));

		postDisable();

	}

	public MessageLoader getMessageLoader() {
		return messageLoader;
	}

	public KothManager getKothManager() {
		return kothManager;
	}
	
	public ZHologram getHologram() {
		return hologram;
	}

}
