package fr.maxlego08.zkoth.command.commands.scheduler;

import java.util.Optional;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.scheduler.Scheduler;
import fr.maxlego08.zkoth.scheduler.SchedulerType;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

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
	protected CommandType perform(ZKothPlugin main) {

		SchedulerType type = SchedulerType.valueOf(argAsString(0).toUpperCase());
		Optional<Koth> optional = main.getKothManager().getKoth(argAsString(1));

		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST);
			return CommandType.DEFAULT;
		}

		Koth totem = optional.get();
		Scheduler scheduler;
		switch (type) {
		case DELAY:

			String day = argAsString(2);

			if (!isDay(day)) {
				message(sender, Message.KOTH_SCHEDULER_ERROR, day);
				return CommandType.SUCCESS;
			}

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
