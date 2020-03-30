package fr.maxlego08.koth.command.commands.scheduler;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

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
	protected CommandType perform(ZKoth main) {

		this.subVCommands.forEach(command -> {
			message(sender, Message.COMMAND_SYNTAXE_HELP, command.getSyntaxe(), command.getDescription());
		});

		return CommandType.SUCCESS;
	}

}
