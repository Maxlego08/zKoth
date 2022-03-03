package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetPoints extends VCommand {

	public CommandKothSetPoints(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.setPermission(Permission.ZKOTH_POINTS);
		this.addSubCommand("setpoints");
		this.setDescription(Message.DESCRIPTION_POINTS);
		this.addRequireArg("points");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = this.argAsString(0);
		int points = this.argAsInteger(1);

		try {
			this.manager.setKothPoints(this.sender, name, points);
		} catch (Exception e) {
			return CommandType.SYNTAX_ERROR;
		}

		return CommandType.SUCCESS;
	}

}