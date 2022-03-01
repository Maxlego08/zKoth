package fr.maxlego08.zkoth.command.commands;

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
		this.setConsoleCanUse(false);
		this.setTabCompletor();
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		String name = argAsString(0);
		this.manager.updateLoots(player, name);

		return CommandType.SUCCESS;
	}

}
