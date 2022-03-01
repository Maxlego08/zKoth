package fr.maxlego08.zkoth.command.commands.scheduler;

import java.util.Arrays;
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

	public CommandKothSchedulerAdd(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_ADD);
		this.addSubCommand("add");
		this.addRequireArg("delay/repeat");
		this.addRequireArg("koth name");
		this.addOptionalArg("day");
		this.addOptionalArg("hour");
		this.addOptionalArg("minute");
		this.addCompletion(0, (a, b) -> Arrays.asList("delay", "repeat"));
		this.addCompletion(1, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(2, (a, b) -> Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		SchedulerType type = SchedulerType.valueOf(this.argAsString(0).toUpperCase());
		Optional<Koth> optional = main.getKothManager().getKoth(this.argAsString(1));

		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST, "%name%", this.argAsString(1));
			return CommandType.DEFAULT;
		}

		Koth totem = optional.get();
		Scheduler scheduler;
		switch (type) {
		case DELAY:

			String day = this.argAsString(2);

			if (!isDay(day)) {
				message(sender, Message.ZKOTH_SCHEDULER_ERROR, day);
				return CommandType.SUCCESS;
			}

			int hour = this.argAsInteger(3);
			int minute = this.argAsInteger(4);

			scheduler = new Scheduler(type, day, hour, minute, totem.getName());

			break;
		case REPEAT:

			minute = this.argAsInteger(2);
			scheduler = new Scheduler(type, minute, totem.getName());

			break;
		default:
			return CommandType.SYNTAX_ERROR;
		}

		main.getSchedulerManager().addScheduler(sender, scheduler);

		return CommandType.SUCCESS;
	}

}
