package fr.maxlego08.koth.command.commands;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.save.Lang;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothReload extends VCommand {

	public CommandKothReload() {
		this.setPermission(Permission.ZKOTH_RELOAD);
		this.addSubCommand("reload");
		this.addSubCommand("rl");
		this.setDescription(Message.DESCRIPTION_RELOAD);
	}

	@Override
	protected CommandType perform(ZKoth main) {
		
		Config.getInstance().load(main.getPersist());
		Lang.getInstance().load(main.getPersist());
		
		message(sender, "§aPlugin successfuly reload");
		
		return CommandType.SUCCESS;
	}

}
