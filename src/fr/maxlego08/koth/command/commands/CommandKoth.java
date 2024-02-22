package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Permission;
import fr.maxlego08.koth.zcore.utils.commands.CommandType;

public class CommandKoth extends VCommand {

	public CommandKoth(KothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.EXAMPLE_PERMISSION);
		this.addSubCommand(new CommandKothReload(plugin));
	}

	@Override
	protected CommandType perform(KothPlugin plugin) {
		syntaxMessage();
		return CommandType.SUCCESS;
	}

}
