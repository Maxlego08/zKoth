package fr.maxlego08.zkoth.command.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetRandomItem extends VCommand {

	public CommandKothSetRandomItem(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1, (a, b) -> Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).stream().map(e -> e.toString()).collect(Collectors.toList()));
		this.setPermission(Permission.ZKOTH_RANDOMITEM);
		this.addSubCommand("setrandomitem");
		this.setDescription(Message.DESCRIPTION_RANDOMITEM);
		this.addRequireArg("name");
		this.addRequireArg("items");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = this.argAsString(0);
		int items = this.argAsInteger(1);

		try {
			this.manager.setKothRandomItems(this.sender, name, items);
		} catch (Exception e) {
			return CommandType.SYNTAX_ERROR;
		}

		return CommandType.SUCCESS;
	}

}