package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.command.commands.scheduler.CommandKothScheduler;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKoth extends VCommand {

	public CommandKoth() {
		this.setPermission(Permission.ZKOTH_USE);

		this.addSubCommand(new CommandKothCreate());
		this.addSubCommand(new CommandKothDelete());
		this.addSubCommand(new CommandKothMove());
		this.addSubCommand(new CommandKothNow());
		this.addSubCommand(new CommandKothSpawn());
		this.addSubCommand(new CommandKothStop());
		this.addSubCommand(new CommandKothReload());
		this.addSubCommand(new CommandKothList());
		this.addSubCommand(new CommandKothInfo());
		this.addSubCommand(new CommandKothAdd());
		this.addSubCommand(new CommandKothRemove());
		this.addSubCommand(new CommandKothSetType());
		this.addSubCommand(new CommandKothLoot());
		this.addSubCommand(new CommandKothSetCapture());
		this.addSubCommand(new CommandKothAxe());
		this.addSubCommand(new CommandKothVersion());
		this.addSubCommand(new CommandKothScheduler());
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
