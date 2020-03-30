package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothSet extends VCommand {

	public CommandKothSet() {
		this.addSubCommand("set");
		this.setPermission(Permission.ZKOTH_SET);
		this.setDescription(Message.DESCRIPTION_SET);
		this.addRequireArg("pos1/pos2");
		this.addRequireArg("koth name");
		this.setConsoleCanUse(false);
	}

	@Override
	protected CommandType perform(ZKoth main) {

		String type = argAsString(0);
		String name = argAsString(1);

		if (!type.equalsIgnoreCase("pos1") && !type.equalsIgnoreCase("pos2"))
			return CommandType.SYNTAX_ERROR;
		main.getManager().setPosition(sender, name, player.getLocation(), type.equalsIgnoreCase("pos1"));

		return CommandType.SUCCESS;
	}

}
