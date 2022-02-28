package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothAdd extends VCommand {

	public CommandKothAdd(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_COMMAND_ADD);
		this.addSubCommand("addcommand", "addc");
		this.setDescription(Message.DESCRIPTION_ADDCOMMAND);
		this.addRequireArg("name");
		this.addRequireArg("command");
		this.setIgnoreParent(true);
		this.setIgnoreArgs(true);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		if (this.args.length < 2) {
			return CommandType.SYNTAX_ERROR;
		}

		String command = "";
		for (int a = 2; a != this.args.length; a++) {
			command += this.args[a] + " ";
		}

		this.manager.addCommand(this.sender, name, command);

		return CommandType.SUCCESS;
	}

}
