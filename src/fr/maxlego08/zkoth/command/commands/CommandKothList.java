package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothList extends VCommand {

	public CommandKothList(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_LIST);
		this.setDescription(Message.DESCRIPTION_LIST);
		this.addSubCommand("list");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {
		this.manager.sendKothList(sender);
		return CommandType.SUCCESS;
	}

}
