package fr.maxlego08.zkoth.command.commands.scheduler;

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

public class CommandKothSchedulerAddRepeat extends VCommand {

	public CommandKothSchedulerAddRepeat(ZKothPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZKOTH_SCHEDULER);
		this.setDescription(Message.DESCRIPTION_SCHEDULER_ADD);
		this.addSubCommand("repeat");
		this.addRequireArg("koth name");
		this.addRequireArg("hour");
		this.addCompletion(0, (a, b) -> plugin.getKothManager().getKothNames());
		this.addCompletion(1, (a, b) -> IntStream.range(0, 24).boxed().map(String::valueOf).collect(Collectors.toList()));
	}

	@Override
	protected CommandType perform(ZKothPlugin main) {

		Optional<Koth> optional = main.getKothManager().getKoth(this.argAsString(0));

		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST, "%name%", this.argAsString(0));
			return CommandType.DEFAULT;
		}

		Koth totem = optional.get();

		int minute = this.argAsInteger(1);
		Scheduler scheduler = new Scheduler(SchedulerType.REPEAT, minute, totem.getName());

		main.getSchedulerManager().addScheduler(sender, scheduler);

		return CommandType.SUCCESS;
	}

}
