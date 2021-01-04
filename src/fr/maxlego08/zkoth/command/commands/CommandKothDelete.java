package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothDelete extends VCommand {

	public CommandKothDelete() {
		this.setPermission(Permission.ZKOTH_CREATE);
		this.addSubCommand("delete");
		this.setDescription("Delete a koth");
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		manager.deleteKoth(sender, name);

		return CommandType.SUCCESS;
	}

}
