package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothList extends VCommand {

	public CommandKothList() {
		this.setPermission(Permission.ZKOTH_LIST);
		this.setDescription("Get koth list");
		this.addSubCommand("list");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {
		manager.sendKothList(sender);
		return CommandType.SUCCESS;
	}

}
