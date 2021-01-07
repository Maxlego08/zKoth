package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothAdd extends VCommand {

	public CommandKothAdd() {
		this.setPermission(Permission.ZKOTH_COMMAND_ADD);
		this.addSubCommand("addc");
		this.setDescription("Add commands");
		this.addRequireArg("name");
		this.addRequireArg("command");
		this.setIgnoreParent(true);
		this.setIgnoreArgs(true);
		this.setBypassCheck(true);
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		if (args.length < 2)
			return CommandType.SYNTAX_ERROR;

		String command = "";
		for (int a = 2; a != args.length; a++)
			command += args[a] + " ";

		manager.addCommand(sender, name, command);

		return CommandType.SUCCESS;
	}

}
