package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSpawn extends VCommand {

	public CommandKothSpawn() {
		this.setPermission(Permission.ZKOTH_SPAWN);
		this.addSubCommand("spawn");
		this.setDescription("Spawn a koth");
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		manager.spawnKoth(sender, name, false);

		return CommandType.SUCCESS;
	}

}
