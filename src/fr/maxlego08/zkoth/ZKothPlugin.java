package fr.maxlego08.zkoth;

import org.bukkit.plugin.ServicePriority;

import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.command.CommandManager;
import fr.maxlego08.zkoth.command.commands.CommandKoth;
import fr.maxlego08.zkoth.inventory.InventoryManager;
import fr.maxlego08.zkoth.listener.AdapterListener;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.scoreboard.ScoreBoardManager;
import fr.maxlego08.zkoth.zcore.ZPlugin;

/**
 * System to create your plugins very simply Projet:
 * https://github.com/Maxlego08/TemplatePlugin
 * 
 * @author Maxlego08
 *
 */
public class ZKothPlugin extends ZPlugin {

	private final KothManager kothManager = new ZKothManager();

	@Override
	public void onEnable() {

		preEnable();

		this.getServer().getServicesManager().register(KothManager.class, kothManager, this, ServicePriority.High);

		this.commandManager = new CommandManager(this);
		this.inventoryManager = InventoryManager.getInstance();
		this.scoreboardManager = new ScoreBoardManager(1000);

		/* Commands */

		this.registerCommand("zkoth", new CommandKoth(), "koth");

		/* Add Listener */

		addListener(new AdapterListener(this));
		addListener(inventoryManager);
		addListener((ZKothManager) kothManager);

		/* Add Saver */

		addSave(Config.getInstance());
		addSave((ZKothManager) kothManager);

		getSavers().forEach(saver -> saver.load(getPersist()));

		postEnable();
	}

	@Override
	public void onDisable() {

		preDisable();

		getSavers().forEach(saver -> saver.save(getPersist()));

		postDisable();

	}

	public KothManager getKothManager() {
		return kothManager;
	}

}
