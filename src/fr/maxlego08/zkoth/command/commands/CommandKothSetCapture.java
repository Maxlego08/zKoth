package fr.maxlego08.zkoth.command.commands;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSetCapture extends VCommand {

	public CommandKothSetCapture(ZKothPlugin plugin) {
		super(plugin);
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.setPermission(Permission.ZKOTH_SETCAPTURE);
		this.addSubCommand("setcapture", "setcap");
		this.setDescription(Message.DESCRIPTION_CAPTURE);
		this.addRequireArg("name");
		this.addRequireArg("capture");
	}

	@Override
	protected CommandType perform(ZKothPlugin plugin) {

		String name = argAsString(0);
		int second = argAsInteger(1);

		this.manager.setCaptureSeconds(this.sender, name, second);

		return CommandType.SUCCESS;
	}

}
