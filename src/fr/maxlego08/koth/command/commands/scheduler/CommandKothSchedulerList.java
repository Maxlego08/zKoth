package fr.maxlego08.koth.command.commands.scheduler;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothSchedulerList extends VCommand {

	public CommandKothSchedulerList() {
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_LIST);
		this.addSubCommand("list");
	}

	@Override
	protected CommandType perform(ZKoth main) {
		
		main.getSchedulerManager().show(sender);
		
		return CommandType.SUCCESS;
	}

}
