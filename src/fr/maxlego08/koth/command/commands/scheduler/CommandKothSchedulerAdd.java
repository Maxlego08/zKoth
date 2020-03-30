package fr.maxlego08.koth.command.commands.scheduler;

import fr.maxlego08.koth.Koth;
import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.command.CommandType;
import fr.maxlego08.koth.command.VCommand;
import fr.maxlego08.koth.scheduler.Scheduler;
import fr.maxlego08.koth.scheduler.SchedulerType;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.enums.Permission;

public class CommandKothSchedulerAdd extends VCommand {

	public CommandKothSchedulerAdd() {
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_ADD);
		this.addSubCommand("add");
		this.addRequireArg("delay/repeat");
		this.addRequireArg("koth name");
		this.addOptionalArg("day");
		this.addOptionalArg("hour");
		this.addOptionalArg("minute");
	}

	@Override
	protected CommandType perform(ZKoth main) {

		SchedulerType type = SchedulerType.valueOf(argAsString(0).toUpperCase());
		Koth totem = main.getManager().get(argAsString(1));

		if (totem == null) {
			message(sender, Message.KOTH_DOESNT_EXIST);
			return CommandType.DEFAULT;
		}

		Scheduler scheduler;
		switch (type) {
		case DELAY:

			String day = argAsString(2);
			int hour = argAsInteger(3);
			int minute = argAsInteger(4);

			scheduler = new Scheduler(type, day, hour, minute, totem.getName());

			break;
		case REPEAT:

			minute = argAsInteger(2);
			scheduler = new Scheduler(type, minute, totem.getName());

			break;
		default:
			return CommandType.SYNTAX_ERROR;
		}

		main.getSchedulerManager().addScheduler(sender, scheduler);

		return CommandType.SUCCESS;
	}

}
