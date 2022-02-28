package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothRemove extends VCommand {

	public CommandKothRemove(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.setPermission(Permission.ZKOTH_COMMAND_REMOVE);
		this.addSubCommand("removec");
		this.setDescription(Message.DESCRIPTION_REMOVECOMMAND);
		this.addRequireArg("name");
		this.addRequireArg("id");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		int id = argAsInteger(1);
		
		this.manager.removeCommand(sender, name, id);

		return CommandType.SUCCESS;
	}

}
