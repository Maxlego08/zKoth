package fr.maxlego08.zkoth.command.commands;

import java.util.Arrays;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothLoot extends VCommand {

	public CommandKothLoot(ZKothPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_LOOT);
		this.setPermission(Permission.ZKOTH_LOOT);
		this.addSubCommand("loot");
		this.addRequireArg("name");
		this.addOptionalArg("page");
		this.setConsoleCanUse(false);
		this.setTabCompletor();
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1, (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		String name = argAsString(0);
		int page = argAsInteger(1, 1);
		if (page < 1) return CommandType.SYNTAX_ERROR;
		this.manager.updateLoots(this.player, name, page);

		return CommandType.SUCCESS;
	}

}
