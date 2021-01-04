package fr.maxlego08.zkoth;

import org.bukkit.plugin.ServicePriority;

import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.command.CommandManager;
import fr.maxlego08.zkoth.inventory.InventoryManager;
import fr.maxlego08.zkoth.listener.AdapterListener;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.scoreboard.ScoreBoardManager;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.utils.builder.CooldownBuilder;

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

		commandManager = new CommandManager(this);

		if (!isEnabled())
			return;
		inventoryManager = InventoryManager.getInstance();

		scoreboardManager = new ScoreBoardManager(1000);

		/* Add Listener */

		addListener(new AdapterListener(this));
		addListener(inventoryManager);

		/* Add Saver */
		addSave(Config.getInstance());
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

}
