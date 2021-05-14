package fr.maxlego08.zkoth.command.commands.scheduler;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothScheduler extends VCommand {

	public CommandKothScheduler() {
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER);
		this.addSubCommand("scheduler", "s");
		this.addSubCommand(new CommandKothSchedulerList());
		this.addSubCommand(new CommandKothSchedulerAdd());
		this.addSubCommand(new CommandKothSchedulerRemove());
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		this.subVCommands.forEach(command -> {
			String message = Message.COMMAND_SYNTAXE_HELP.getMessage();
			message = message.replace("%command%", command.getSyntaxe());
			message = message.replace("%description%", command.getDescription());
			messageWO(sender, message);
		});

		return CommandType.SUCCESS;
	}

}
