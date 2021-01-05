package fr.maxlego08.zkoth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.FactionListener;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.api.event.events.KothCatchEvent;
import fr.maxlego08.zkoth.api.event.events.KothLooseEvent;
import fr.maxlego08.zkoth.api.event.events.KothSpawnEvent;
import fr.maxlego08.zkoth.api.event.events.KothWinEvent;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;
import fr.maxlego08.zkoth.zcore.utils.ZUtils;
import fr.maxlego08.zkoth.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.zkoth.zcore.utils.interfaces.CollectionConsumer;

public class ZKoth extends ZUtils implements Koth {

	private final String name;
	private int captureSeconds;
	private Location minLocation;
	private Location maxLocation;
	private LootType type;
	private List<String> commands = new ArrayList<String>();
	private List<ItemStack> itemStacks = new ArrayList<ItemStack>();

	private transient boolean isEnable = false;
	private transient boolean isCooldown = false;
	private transient TimerTask timerTask;
	private transient Player currentPlayer;
	private transient FactionListener factionListener;
	private transient AtomicInteger currentCaptureSeconds;

	/**
	 * @param name
	 * @param captureSeconds
	 * @param minLocation
	 * @param maxLocation
	 */
	public ZKoth(String name, int captureSeconds, Location minLocation, Location maxLocation) {
		super();
		this.name = name;
		this.captureSeconds = captureSeconds;
		this.minLocation = minLocation;
		this.maxLocation = maxLocation;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getMinLocation() {
		return this.minLocation;
	}

	@Override
	public Location getMaxLocation() {
		return this.maxLocation;
	}

	@Override
	public Cuboid getCuboid() {
		return new Cuboid(this.maxLocation, this.minLocation);
	}

	@Override
	public Location getCenter() {
		Cuboid cuboid = getCuboid();
		return cuboid.getCenter();
	}

	@Override
	public int getCaptureSeconds() {
		return this.captureSeconds;
	}

	@Override
	public List<String> getCommands() {
		return this.commands;
	}

	@Override
	public List<ItemStack> getItemStacks() {
		return this.itemStacks;
	}

	@Override
	public LootType getLootType() {
		return this.type;
	}

	@Override
	public void setLootType(LootType type) {
		this.type = type;
	}

	@Override
	public void move(Location minLocation, Location maxLocation) {
		this.maxLocation = maxLocation;
		this.minLocation = minLocation;
	}

	@Override
	public boolean isEnable() {
		return this.isEnable;
	}

	@Override
	public boolean isCooldown() {
		return this.isCooldown;
	}

	@Override
	public void spawn(CommandSender sender, boolean now) {

		if (this.minLocation == null || this.maxLocation == null) {
			message(sender, Message.ZKOTH_SPAWN_ERROR);
		} else if (this.isCooldown) {
			message(sender, Message.ZKOTH_SPAWN_COOLDOWN);
		} else if (this.isEnable) {
			message(sender, Message.ZKOTH_SPAWN_ALREADY);
		} else {
			if (now)
				spawnNow();
			else
				spawn();
		}

	}

	private void spawn() {

		isCooldown = true;
		this.currentCaptureSeconds = new AtomicInteger(Config.cooldownInSecond);
		scheduleFix(0, 1000, (task, isCancelled) -> {

		});

	}

	private void spawnNow() {

		KothSpawnEvent event = new KothSpawnEvent(this);
		event.callEvent();

		if (event.isCancelled())
			return;

		this.isCooldown = false;
		this.isEnable = true;

		this.broadcast(Message.ZKOTH_EVENT_START);
	}

	/**
	 * Broadcast message
	 * 
	 * @param message
	 */
	private void broadcast(Message message) {

		switch (message.getType()) {
		case ACTION: {
			String realMessage = replaceMessage(message.getMessage());
			this.broadcastAction(realMessage);
			break;
		}
		case CENTER: {
			if (message.getMessages().size() == 0) {
				String realMessage = replaceMessage(message.getMessage());
				broadcastCenterMessage(Arrays.asList(realMessage));
			} else
				broadcastCenterMessage(
						message.getMessages().stream().map(e -> replaceMessage(e)).collect(Collectors.toList()));
			break;
		}
		case TCHAT: {
			String realMessage = replaceMessage(message.getMessage());
			this.broadcast(realMessage);
			break;
		}
		case TITLE: {
			String title = replaceMessage(message.getTitle());
			String subTitle = replaceMessage(message.getSubTitle());
			int fadeInTime = message.getStart();
			int showTime = message.getTime();
			int fadeOutTime = message.getEnd();
			for (Player player : Bukkit.getOnlinePlayers())
				this.title(player, title, subTitle, fadeInTime, showTime, fadeOutTime);
			break;
		}
		default:
			break;
		}
	}

	/**
	 * 
	 * @param message
	 * @return string
	 */
	private String replaceMessage(String message) {

		Cuboid cuboid = getCuboid();
		Location center = cuboid.getCenter();
		message = message.replace("%x%", String.valueOf(center.getBlockX()));
		message = message.replace("%y%", String.valueOf(center.getBlockY()));
		message = message.replace("%z%", String.valueOf(center.getBlockZ()));
		message = message.replace("%capture%", TimerBuilder
				.getStringTime(this.currentCaptureSeconds == null ? this.captureSeconds : currentCaptureSeconds.get()));
		message = message.replace("%world%", center.getWorld().getName());
		message = message.replace("%name%", this.name);
		message = message.replace("%player%",
				this.currentPlayer == null ? Message.ZKOHT_EVENT_PLAYER.getMessage() : this.currentPlayer.getName());
		String faction = this.currentPlayer == null ? Message.ZKOHT_EVENT_FACION.getMessage()
				: this.factionListener.getFactionTag(this.currentPlayer);
		message = message.replace("%faction%", faction);

		return message;
	}

	@Override
	public void playerMove(Player player, FactionListener factionListener) {

		if (!this.isEnable)
			return;

		this.factionListener = factionListener;
		Cuboid cuboid = this.getCuboid();

		if (this.currentPlayer == null && cuboid.contains(player.getLocation())) {

			this.currentPlayer = player;
			this.startCap(player);

		} else if (this.currentPlayer != null && !cuboid.contains(this.currentPlayer.getLocation())) {

			KothLooseEvent event = new KothLooseEvent(this.currentPlayer, this);
			event.callEvent();

			if (event.isCancelled())
				return;

			broadcast(Message.ZKOHT_EVENT_LOOSE);

			if (this.timerTask != null)
				this.timerTask.cancel();

			this.timerTask = null;
			this.currentPlayer = null;
		}
	}

	/**
	 * Start cap
	 * 
	 * @param player
	 */
	private synchronized void startCap(Player player) {

		if (this.currentPlayer == null)
			return;

		KothCatchEvent event = new KothCatchEvent(this, player, this.captureSeconds);
		event.callEvent();

		if (event.isCancelled()) {
			this.currentPlayer = null;
			return;
		}

		broadcast(Message.ZKOHT_EVENT_CATCH);

		int captureSeconds = event.getCaptureSeconds();
		captureSeconds = captureSeconds < 0 ? 30 : captureSeconds;
		this.currentCaptureSeconds = new AtomicInteger(captureSeconds);
		Cuboid cuboid = getCuboid();

		scheduleFix(0, 1000, (task, isCancelled) -> {

			this.timerTask = task;

			if (!isCancelled) {
				task.cancel();
				return;
			}

			if (!this.isEnable) {
				task.cancel();
				return;
			}

			int tmpCapture = this.currentCaptureSeconds.getAndDecrement();

			if (this.currentPlayer != null) {
				if (!this.currentPlayer.isOnline() || !cuboid.contains(this.currentPlayer.getLocation()))
					this.currentPlayer = null;
			}

			if (this.currentPlayer == null) {

				KothLooseEvent kothLooseEvent = new KothLooseEvent(this.currentPlayer, this);
				kothLooseEvent.callEvent();

				if (kothLooseEvent.isCancelled())
					return;

				if (this.timerTask != null)
					this.timerTask.cancel();

				this.timerTask = null;
				this.currentPlayer = null;

				broadcast(Message.ZKOHT_EVENT_LOOSE);
				return;

			}
			if (Config.displayMessageKothCap.contains(tmpCapture))
				broadcast(Message.ZKOHT_EVENT_TIMER);

			if (tmpCapture <= 0) {

				KothWinEvent kothWinEvent = new KothWinEvent(this, this.currentPlayer);
				kothWinEvent.callEvent();

				if (kothWinEvent.isCancelled())
					return;

				task.cancel();
				broadcast(Message.ZKOTH_EVENT_WIN);

				// donner les loots

				this.isEnable = false;
				this.isCooldown = false;
				this.currentPlayer = null;
				this.timerTask = null;
				this.currentCaptureSeconds = null;

			}
		});
	}

	@Override
	public CollectionConsumer<Player> onScoreboard() {
		if (isCooldown)
			return p -> Config.scoreboardCooldown.stream().map(e -> replaceMessage(e)).collect(Collectors.toList());
		else
			return p -> Config.scoreboard.stream().map(e -> replaceMessage(e)).collect(Collectors.toList());
	}

	@Override
	public void addCommand(String command) {
		this.commands.add(command);
	}

	@Override
	public void removeCommand(int id) {
		try {
			this.commands.remove(id - 1);
		} catch (Exception e) {
		}
	}

}
