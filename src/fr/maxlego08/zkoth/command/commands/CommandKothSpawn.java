package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSpawn extends VCommand {

	public CommandKothSpawn(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.setPermission(Permission.ZKOTH_SPAWN);
		this.addSubCommand("spawn");
		this.setDescription(Message.DESCRIPTION_SPAWN);
		this.addRequireArg("name");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		this.manager.spawnKoth(sender, name, false);

		return CommandType.SUCCESS;
	}
	
}
