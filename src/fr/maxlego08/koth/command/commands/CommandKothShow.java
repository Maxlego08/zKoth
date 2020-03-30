package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothShow extends VCommand {

	public CommandKothShow() {
		this.setPermission(Permission.ZKOTH_SHOW);
		this.addSubCommand("show");
		this.setDescription(Message.DESCRIPTION_SHOW);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		main.getManager().sendKoths(sender);
		return CommandType.SUCCESS;
	}

}
