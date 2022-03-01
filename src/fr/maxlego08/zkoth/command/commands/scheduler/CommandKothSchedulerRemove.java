package fr.maxlego08.zkoth.command.commands.scheduler;

import java.util.UUID;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSchedulerRemove extends VCommand {

	public CommandKothSchedulerRemove(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_REMOVE);
		this.addSubCommand("remove");
		this.addRequireArg("UUID");
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		UUID uuid = UUID.fromString(argAsString(0));
		main.getSchedulerManager().remove(sender, uuid);
		
		return CommandType.SUCCESS;
	}

}
