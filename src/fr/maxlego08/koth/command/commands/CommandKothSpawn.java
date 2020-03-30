package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothSpawn extends VCommand {

	public CommandKothSpawn() {
		this.setPermission(Permission.ZKOTH_SPAWN);
		this.addSubCommand("spawn");
		this.addRequireArg("koth name");
		this.setDescription(Message.DESCRIPTION_SPAWN);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		
		String name = argAsString(0);
		main.getManager().spawn(sender, name, false);
		
		return CommandType.SUCCESS;
	}

}
