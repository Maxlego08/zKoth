package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothNow extends VCommand {

	public CommandKothNow() {
		this.setPermission(Permission.ZKOTH_NOW);
		this.addSubCommand("now");
		this.addRequireArg("koth name");
		this.setDescription(Message.DESCRIPTION_NOW);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		
		String name = argAsString(0);
		main.getManager().spawn(sender, name, true);
		
		return CommandType.SUCCESS;
	}

}
