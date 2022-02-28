package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothDelete extends VCommand {

	public CommandKothDelete(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_CREATE);
		this.addSubCommand("delete");
		this.setDescription(Message.DESCRIPTION_DELETE);
		this.addRequireArg("name");
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		this.manager.deleteKoth(this.sender, name);

		return CommandType.SUCCESS;
	}

}
