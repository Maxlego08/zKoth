package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothDelete extends VCommand {

	public CommandKothDelete() {
		this.setPermission(Permission.ZKOTH_DELETE);
		this.addSubCommand("delete");
		this.addRequireArg("totem name");
		this.setDescription(Message.DESCRIPTION_DELETE);
	}

	@Override
	protected CommandType perform(ZKoth main) {

		String name = argAsString(0);
		main.getManager().delete(sender, name);

		return CommandType.SUCCESS;
	}

}
