package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothHelp extends VCommand {

	public CommandKothHelp() {
		this.setPermission(Permission.ZKOTH_HELP);
		this.addSubCommand("help");
		this.setDescription(Message.DESCRIPTION_HELP);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		main.getCommandManager().sendHelp("zkoth", sender);
		return CommandType.SUCCESS;
	}

}
