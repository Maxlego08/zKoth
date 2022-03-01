package fr.maxlego08.zkoth.command.commands.scheduler;

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
		this.addSubCommand(new CommandKothSchedulerList(plugin));
		this.addSubCommand(new CommandKothSchedulerAdd(plugin));
		this.addSubCommand(new CommandKothSchedulerRemove(plugin));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		this.subVCommands.forEach(command -> {
			messageWO(this.sender, Message.COMMAND_SYNTAXE_HELP, "%command%", command.getSyntax(), "%description%",
					command.getDescription());
		});

		return CommandType.SUCCESS;
	}

}
