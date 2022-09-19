package fr.maxlego08.zkoth.command.commands.scheduler;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSchedulerAdd extends VCommand {

	public CommandKothSchedulerAdd(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_ADD);
		this.addSubCommand("add");
		this.addSubCommand(new CommandKothSchedulerAddDelay(plugin));
		this.addSubCommand(new CommandKothSchedulerAddRepeat(plugin));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {
		message(this.sender, Message.ZKOTH_SCHEDULER_ADD);
		return CommandType.SUCCESS;
	}

}
