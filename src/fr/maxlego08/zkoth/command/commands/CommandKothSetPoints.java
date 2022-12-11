package fr.maxlego08.zkoth.command.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetPoints extends VCommand {

	public CommandKothSetPoints(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1, (a, b) -> Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190).stream().map(e -> e.toString()).collect(Collectors.toList()));
		this.setPermission(Permission.ZKOTH_POINTS);
		this.addSubCommand("setpoints");
		this.setDescription(Message.DESCRIPTION_POINTS);
		this.addRequireArg("name");
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