package fr.maxlego08.zkoth.command.commands.scheduler;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.command.VCommand;
import fr.maxlego08.zkoth.scheduler.Scheduler;
import fr.maxlego08.zkoth.scheduler.SchedulerType;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.commands.CommandType;

public class CommandKothSchedulerAddDelay extends VCommand {

	public CommandKothSchedulerAddDelay(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_ADD);
		this.addSubCommand("delay");
		this.addRequireArg("koth name");
		this.addRequireArg("day");
		this.addRequireArg("hour");
		this.addRequireArg("minute");
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1,
				(a, b) -> Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));
		this.addCompletion(2, (a, b) -> IntStream.range(0, 24).boxed().map(String::valueOf).collect(Collectors.toList()));
		this.addCompletion(3, (a, b) -> IntStream.range(0, 60).boxed().map(String::valueOf).collect(Collectors.toList()));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		Optional<Koth> optional = main.getKothManager().getKoth(this.argAsString(0));

		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST, "%name%", this.argAsString(0));
			return CommandType.DEFAULT;
		}

		Koth totem = optional.get();

		String day = this.argAsString(1);

		if (!isDay(day)) {
			message(sender, Message.ZKOTH_SCHEDULER_ERROR, day);
			return CommandType.SUCCESS;
		}

		int hour = this.argAsInteger(2);
		int minute = this.argAsInteger(3);

		Scheduler scheduler = new Scheduler(SchedulerType.REPEAT, day, hour, minute, totem.getName());

		main.getSchedulerManager().addScheduler(this.sender, scheduler);

		return CommandType.SUCCESS;
	}

}
