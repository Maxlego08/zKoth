package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothStop extends VCommand {

	public CommandKothStop() {
		this.setPermission(Permission.ZKOTH_STOP);
		this.addSubCommand("stop");
		this.addRequireArg("totem name");
		this.setDescription(Message.DESCRIPTION_STOP);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		
		String name = argAsString(0);
		main.getManager().stop(sender, name);
		
		return CommandType.SUCCESS;
	}

}
