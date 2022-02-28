package fr.maxlego08.zkoth.command.commands.scheduler;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSchedulerList extends VCommand {

	public CommandKothSchedulerList(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_LIST);
		this.addSubCommand("list");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {
		plugin.getSchedulerManager().show(sender);
		return CommandType.SUCCESS;
	}

}
