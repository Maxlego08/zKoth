package fr.maxlego08.zkoth.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.scoreboard.ScoreBoardManager;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.logger.Logger;
import fr.maxlego08.zkoth.zcore.logger.Logger.LogType;
import fr.maxlego08.zkoth.zcore.utils.ZUtils;
import fr.maxlego08.zkoth.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.zkoth.zcore.utils.storage.Persist;
import fr.maxlego08.zkoth.zcore.utils.storage.Saveable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class SchedulerManager extends ZUtils implements Saveable {

	private static List<Scheduler> schedulers = new ArrayList<Scheduler>();
	private transient Map<UUID, Scheduler> map = new HashMap<>();
	private transient boolean isRunning = false;
	private final transient KothManager manager;

	public SchedulerManager(KothManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public void save(Persist persist) {
		persist.save(this, "schedulers");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(this, SchedulerManager.class, "schedulers");
		run();
	}

	/**
	 * Ajout d'un scheduler
	 * 
	 * @param sender
	 * @param scheduler
	 */
	public void addScheduler(CommandSender sender, Scheduler scheduler) {

		schedulers.add(scheduler);
		message(sender, Message.ZKOTH_SCHEDULER_CREATE, "%koth%", scheduler.getTotemName());
		run();

	}

	public void run() {

		if (isRunning)
			return;

		isRunning = true;

		scheduleFix(1000, (task, canRun) -> {

			if (!canRun || !isRunning || schedulers.size() == 0) {
				task.cancel();
				isRunning = false;
				return;
			}

			Calendar calendar = Calendar.getInstance();
			Iterator<Scheduler> iterator = schedulers.iterator();
			while (iterator.hasNext()) {

				Scheduler scheduler = iterator.next();
				if (scheduler.toggle(calendar)) {

					Optional<Koth> optional = manager.getKoth(scheduler.getTotemName());

					if (!optional.isPresent()) {
						Logger.info("Deleting a scheduler, unable to find the koth " + scheduler.getTotemName(),
								LogType.ERROR);
						iterator.remove();
						continue;
					}

					Koth koth = optional.get();
					koth.spawn(Config.spawnKothWithSchedulerNow);

					if (Config.enableScoreboard) {

						ScoreBoardManager boardManager = this.plugin.getScoreboardManager();

						boardManager.setLinesAndSchedule(koth.onScoreboard());
						for (Player player : Bukkit.getOnlinePlayers()) {
							boardManager.createBoard(player, Config.scoreboardTitle);
						}

					}

				}

			}

		});

	}

	/**
	 * 
	 * @param sc
	 * @return
	 */
	private UUID getUUID(Scheduler sc) {
		for (Entry<UUID, Scheduler> e : map.entrySet())
			if (e.getValue().equals(sc))
				return e.getKey();
		return UUID.randomUUID();
	}

	/**
	 * 
	 * @param sender
	 * @param uuid
	 */
	public void remove(CommandSender sender, UUID uuid) {

		if (!map.containsKey(uuid)) {
			message(sender, Message.ZKOTH_SCHEDULER_REMOVE_ERROR);
			return;
		}

		Scheduler scheduler = map.get(uuid);

		schedulers.remove(scheduler);
		map.remove(uuid);

		message(sender, Message.ZKOTH_SCHEDULER_REMOVE_SUCCESS);

	}

	/**
	 * 
	 * @param sender
	 */
	public void show(CommandSender sender) {

		if (schedulers.size() == 0) {

			message(sender, Message.ZKOTH_SCHEDULER_EMPTY);
			return;

		}

		if (sender instanceof Player) {

			schedulers.forEach(sc -> {

				String message = "";
				if (sc.getType().equals(SchedulerType.DELAY)) {
					message = Message.ZKOTH_SCHEDULER_LIST.getMessage().replace("%totemName%", sc.getTotemName())
							.replace("%type%", sc.getType().name()).replace("%hour%", String.valueOf(sc.getHour()))
							.replace("%minute%", String.valueOf(sc.getMinutes())).replace("%day%", sc.getDay());
				} else {
					message = Message.ZKOTH_SCHEDULER_LIST_REPEAT.getMessage().replace("%type%", sc.getType().name())
							.replace("%totemName%", sc.getTotemName())
							.replace("%timer%", TimerBuilder.getStringTime(sc.getMinutes() * 60));
				}

				UUID uuid = getUUID(sc);
				map.putIfAbsent(uuid, sc);

				TextComponent component = new TextComponent(message);
				component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,
						new TextComponent[] { new TextComponent(Message.ZKOTH_SCHEDULER_REMOVE_HOVER.getMessage()) }));
				component.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND,
						"/koth scheduler remove " + uuid));

				((Player) sender).spigot().sendMessage(component);

			});

		} else {

			schedulers.forEach(sc -> {

				if (sc.getType().equals(SchedulerType.DELAY)) {
					message(sender, Message.ZKOTH_SCHEDULER_LIST.getMessage().replace("%totemName%", sc.getTotemName())
							.replace("%type%", sc.getType().name()).replace("%hour%", String.valueOf(sc.getHour()))
							.replace("%minute%", String.valueOf(sc.getMinutes())).replace("%day%", sc.getDay()));
				} else {
					message(sender,
							Message.ZKOTH_SCHEDULER_LIST_REPEAT.getMessage().replace("%type%", sc.getType().name())
									.replace("%totemName%", sc.getTotemName())
									.replace("%timer%", TimerBuilder.getStringTime(sc.getMinutes() * 60)));
				}

			});

		}

	}

}
