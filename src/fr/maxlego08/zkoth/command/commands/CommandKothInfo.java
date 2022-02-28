package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothInfo extends VCommand {

	public CommandKothInfo(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_INFO);
		this.addSubCommand("info");
		this.setDescription(Message.DESCRIPTION_INFO);
		this.addRequireArg("name");
		this.setTabCompletor();
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		this.manager.showInformations(this.sender, name);

		return CommandType.SUCCESS;
	}

}
