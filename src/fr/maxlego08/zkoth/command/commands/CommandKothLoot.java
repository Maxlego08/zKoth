package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothLoot extends VCommand {

	public CommandKothLoot() {
		this.setDescription("Modify items");
		this.setPermission(Permission.ZKOTH_LOOT);
		this.addSubCommand("loot");
		this.addRequireArg("name");
		this.setConsoleCanUse(false);
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		String name = argAsString(0);
		manager.updateLoots(player, name);

		return CommandType.SUCCESS;
	}

}
