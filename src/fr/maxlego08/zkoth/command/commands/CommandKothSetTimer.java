package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetTimer extends VCommand {

	public CommandKothSetTimer(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.setPermission(Permission.ZKOTH_TIMER);
		this.addSubCommand("settimer");
		this.setDescription(Message.DESCRIPTION_TIMER);
		this.addRequireArg("seconds");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = this.argAsString(0);
		int seconds = this.argAsInteger(1);

		try {
			this.manager.setKothTimerSeconds(this.sender, name, seconds);
		} catch (Exception e) {
			return CommandType.SYNTAX_ERROR;
		}

		return CommandType.SUCCESS;
	}

}