package fr.maxlego08.zkoth.scheduler;

import java.util.Optional;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.zcore.logger.Logger;
import fr.maxlego08.zkoth.zcore.logger.Logger.LogType;
import fr.maxlego08.zscheduler.api.Implementation;
import fr.maxlego08.zscheduler.api.Scheduler;
import fr.maxlego08.zscheduler.api.SchedulerManager;

public class ZkothImplementation implements Implementation {

	private final ZKothPlugin plugin;

	/**
	 * @param plugin
	 */
	public ZkothImplementation(ZKothPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	public void register() {
		SchedulerManager schedulerManager = this.plugin.getProvider(SchedulerManager.class);
		schedulerManager.registerImplementation(this);
	}

	@Override
	public String getName() {
		return "ZKOTH";
	}

	@Override
	public void schedule(Scheduler scheduler) {
		String kothName = (String) scheduler.getImplementationValues().getOrDefault("koth_name", "");
		boolean startNow = (boolean) scheduler.getImplementationValues().getOrDefault("start_now", false);

		Optional<Koth> optional = this.plugin.getKothManager().getKoth(kothName);
		if (optional.isPresent()) {

			Koth koth = optional.get();
			koth.spawn(startNow);

		} else {
			Logger.info("Koth with name " + kothName + " was not found with scheduler " + scheduler.getName(),
					LogType.ERROR);
		}

	}

}
