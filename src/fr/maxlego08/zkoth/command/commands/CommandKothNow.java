package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothNow extends VCommand {

	public CommandKothNow(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SPAWN);
		this.addSubCommand("now");
		this.setDescription(Message.DESCRIPTION_NOW);
		this.addRequireArg("name");
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		this.manager.spawnKoth(sender, name, true);

		return CommandType.SUCCESS;
	}
	
}
