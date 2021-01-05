package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothNow extends VCommand {

	public CommandKothNow() {
		this.setPermission(Permission.ZKOTH_SPAWN);
		this.addSubCommand("now");
		this.setDescription("Spawn a koth without cooldown");
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		manager.spawnKoth(sender, name, true);

		return CommandType.SUCCESS;
	}

}
