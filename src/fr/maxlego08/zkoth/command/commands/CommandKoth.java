package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKoth extends VCommand {

	public CommandKoth() {
		this.setPermission(Permission.ZKOTH_USE);

		this.addSubCommand(new CommandKothCreate());
		this.addSubCommand(new CommandKothDelete());
		this.addSubCommand(new CommandKothAxe());
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
