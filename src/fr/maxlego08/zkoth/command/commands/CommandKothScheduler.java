package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothScheduler extends VCommand {

	public CommandKothScheduler(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER);
		this.addSubCommand("scheduler", "s");
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		message(sender, Message.ZKOTH_SCHEDULER_INFO);

		return CommandType.SUCCESS;
	}

}
