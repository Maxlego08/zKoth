package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothStop extends VCommand {

	public CommandKothStop(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.setPermission(Permission.ZKOTH_STOP);
		this.addSubCommand("stop");
		this.setDescription(Message.DESCRIPTION_STOP);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		this.manager.stopKoth(sender, name);

		return CommandType.SUCCESS;
	}
	
}
