package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothCreate extends VCommand {

	public CommandKothCreate() {
		this.setPermission(Permission.ZKOTH_CREATE);
		this.addSubCommand("create");
		this.addRequireArg("koth name");
		this.addOptionalArg("time cap");
		this.setConsoleCanUse(false);
		this.setDescription(Message.DESCRIPTION_CREATE);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		
		String name = argAsString(0);
		int cap = argAsInteger(1, Config.defaultCap);
		main.getManager().create(sender, name, cap);
		
		return CommandType.SUCCESS;
	}

}
