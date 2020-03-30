package fr.maxlego08.koth.command.commands.scheduler;

import java.util.UUID;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothSchedulerRemove extends VCommand {

	public CommandKothSchedulerRemove() {
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_REMOVE);
		this.addSubCommand("remove");
		this.addRequireArg("UUID");
	}

	@Override
	protected CommandType perform(ZKoth main) {

		UUID uuid = UUID.fromString(argAsString(0));
		main.getSchedulerManager().remove(sender, uuid);
		
		return CommandType.SUCCESS;
	}

}
