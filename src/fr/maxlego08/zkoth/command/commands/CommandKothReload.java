package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothReload extends VCommand {

	public CommandKothReload(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_RELOAD);
		this.addSubCommand("reload", "rl");
		this.setDescription("Reload configuration file");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		plugin.reloadConfig();
		Config.getInstance().load(plugin);
		plugin.getMessageLoader().load(plugin.getPersist());
		message(this.sender, "§2Reload !");

		return CommandType.SUCCESS;
	}

}
