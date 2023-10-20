package fr.maxlego08.zkoth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.maxlego08.zkoth.api.FactionListener;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.enums.KothType;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.api.event.events.KothCapEvent;
import fr.maxlego08.zkoth.api.event.events.KothCatchEvent;
import fr.maxlego08.zkoth.api.event.events.KothLooseEvent;
import fr.maxlego08.zkoth.api.event.events.KothSpawnEvent;
import fr.maxlego08.zkoth.api.event.events.KothStartEvent;
import fr.maxlego08.zkoth.api.event.events.KothStopEvent;
import fr.maxlego08.zkoth.api.event.events.KothWinEvent;
import fr.maxlego08.zkoth.hooks.DefaultHook;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.save.ReplaceConfig;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.logger.Logger;
import fr.maxlego08.zkoth.zcore.logger.Logger.LogType;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;
import fr.maxlego08.zkoth.zcore.utils.ZUtils;
import fr.maxlego08.zkoth.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.zkoth.zcore.utils.interfaces.CollectionConsumer;

public class ZKoth extends ZUtils implements Koth {

	private final String name;
	private KothType kothType;
	private int captureSeconds;
	private Location minLocation;
	private Location maxLocation;
	private LootType type;
	private List<String> commands = new ArrayList<String>();
	private List<String> itemStacks = new ArrayList<String>();
	private List<String> blockLocations = new ArrayList<String>();

	private int maxSecondsCap;
	private int maxPoints;
	private int randomItemStacks = 0;

	private transient boolean isEnable = false;
	private transient boolean isCooldown = false;
	private transient TimerTask timerTask;
	private transient TimerTask timerTaskStop;
	private transient Player currentPlayer;
	private transient FactionListener factionListener = new DefaultHook();
	private transient AtomicInteger currentCaptureSeconds;

	private transient Material lastMaterial;
	private transient Map<UUID, Integer> playersValues = new HashMap<UUID, Integer>();

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
		this.type = LootType.NONE;
		this.kothType = KothType.CLASSIC;
		this.maxPoints = 60;
		this.maxSecondsCap = 60;
		this.randomItemStacks = 0;
		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}
		if (this.blockLocations == null) {
			this.blockLocations = new ArrayList<>();
		}

		Cuboid cuboid = getCuboid();
		cuboid.forEach(block -> {
			if (block.getType() == Material.SPONGE) {
				this.blockLocations.add(changeLocationToString(block.getLocation()));
			}
		});
	}

	@Override
	public void setType(KothType type) {
		this.kothType = type;
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
		List<ItemStack> itemStacks = this.itemStacks.stream().map(e -> decode(e)).collect(Collectors.toList());
		if (this.randomItemStacks <= 0) {
			return itemStacks;
		}
		Collections.shuffle(itemStacks, new Random());
		return itemStacks.stream().limit(this.randomItemStacks).collect(Collectors.toList());
	}

	@Override
	public List<ItemStack> getAllItemStacks() {
		return this.itemStacks.stream().map(e -> decode(e)).collect(Collectors.toList());
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

		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}

		if (this.minLocation == null || this.maxLocation == null) {
			message(sender, Message.ZKOTH_SPAWN_ERROR);
		} else if (this.isCooldown) {
			message(sender, Message.ZKOTH_SPAWN_COOLDOWN);
		} else if (this.isEnable) {
			message(sender, Message.ZKOTH_SPAWN_ALREADY);
		} else {
			if (now) {
				spawnNow();
			} else {
				spawn();
			}
		}

	}

	@Override
	public void spawn(boolean now) {

		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}

		if (this.minLocation == null || this.maxLocation == null) {
			return;
		} else if (this.isCooldown) {
			return;
		} else if (this.isEnable) {
			return;
		} else {
			if (now) {
				spawnNow();
			} else {
				spawn();
			}
		}

	}

	/**
	 * Permet de faire spawn le Koth avec un cooldown
	 */
	private void spawn() {

		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}

		this.isCooldown = true;
		this.isEnable = true;
		this.currentCaptureSeconds = new AtomicInteger(Config.cooldownInSecond);

		KothStartEvent event = new KothStartEvent(this);
		event.callEvent();

		if (event.isCancelled()) {
			return;
		}

		scheduleFix(0, Config.enableDebug ? 10 : 1000, (task, isCancelled) -> {

			this.timerTask = task;

			if (!isCancelled) {
				task.cancel();
				return;
			}

			if (!this.isEnable) {
				task.cancel();
				return;
			}

			int tmpCapture = this.currentCaptureSeconds.get();

			// Si on doit avetir
			if (Config.displayMessageCooldown.contains(tmpCapture)) {
				broadcast(Message.ZKOTH_EVENT_COOLDOWN);
			}

			if (tmpCapture <= 0) {
				this.isCooldown = false;
				this.timerTask.cancel();
				this.spawnNow();
				return;
			}

			this.currentCaptureSeconds.decrementAndGet();
		});

	}

	/**
	 * Permet de faire spawn le koth maintenant
	 */
	private void spawnNow() {

		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}

		this.playersValues.clear();

		KothSpawnEvent event = new KothSpawnEvent(this);
		event.callEvent();

		if (event.isCancelled()) {
			return;
		}

		this.isCooldown = false;
		this.isEnable = true;
		this.currentCaptureSeconds = new AtomicInteger(this.captureSeconds);

		this.broadcast(Message.ZKOTH_EVENT_START);
		this.changeBlocks(Config.noOneCapturingMaterial);

		if (this.factionListener == null) {
			this.factionListener = new DefaultHook();
		}

		if (this.kothType == KothType.POINT_COUNT) {
			this.startschedule();
		}

		Timer timer = new Timer();
		this.timerTaskStop = new TimerTask() {
			@Override
			public void run() {
				stop(Bukkit.getConsoleSender());
			}
		};
		timer.schedule(this.timerTaskStop, Config.forceStoKothAfterSeconds * 1000);

	}

	/**
	 * Broadcast message
	 * 
	 * @param message
	 */
	private void broadcast(Message message) {

		switch (message.getType()) {
		case ACTION: {
			if (message.getMessage() == null) {
				Logger.info(message.name() + " is null, check your config plz !", LogType.ERROR);
				return;
			}
			String realMessage = replaceMessage(message.getMessage());
			this.broadcastAction(realMessage);
			break;
		}
		case CENTER: {
			if (message.getMessages().size() == 0) {
				String realMessage = replaceMessage(message.getMessage());
				broadcastCenterMessage(Arrays.asList(realMessage));
			} else {
				broadcastCenterMessage(message.getMessages().stream().map(e -> {
					return replaceMessage(e);
				}).collect(Collectors.toList()));
			}
			break;
		}
		case TCHAT: {
			if (message.getMessages().size() == 0) {
				String realMessage = replaceMessage(message.getMessage());
				this.broadcast(realMessage);
			} else {
				message.getMessages().forEach(m -> {
					String realMessage = replaceMessage(m);
					this.broadcast(realMessage);
				});
			}
			break;
		}
		case TITLE: {
			String title = replaceMessage(message.getTitle());
			String subTitle = replaceMessage(message.getSubTitle());
			int fadeInTime = message.getStart();
			int showTime = message.getTime();
			int fadeOutTime = message.getEnd();
			for (Player player : Bukkit.getOnlinePlayers()) {
				this.title(player, title, subTitle, fadeInTime, showTime, fadeOutTime);
			}
			break;
		}
		case NONE:
		default:
			break;
		}
	}

	/**
	 * Permet de remplacer les messages
	 * 
	 * @param message
	 * @return string
	 */
	private String replaceMessage(String message) {

		Cuboid cuboid = getCuboid();
		Location center = cuboid.getCenter();

		if (center != null) {
			message = message.replace("%x%", String.valueOf(center.getBlockX()));
			message = message.replace("%y%", String.valueOf(center.getBlockY()));
			message = message.replace("%z%", String.valueOf(center.getBlockZ()));
		}

		int seconds = this.currentCaptureSeconds == null ? this.captureSeconds : this.currentCaptureSeconds.get();
		message = message.replace("%capture%", TimerBuilder.getStringTime(seconds));
		message = message.replace("%world%", center.getWorld().getName());
		message = message.replace("%name%", this.name);

		String player = this.currentPlayer == null ? Message.ZKOHT_EVENT_PLAYER.getMessage()
				: this.currentPlayer.getName();
		message = message.replace("%player%", player);

		int value = (int) this.getValue(this.currentPlayer);
		message = message.replace("%points%", String.valueOf(value));
		message = message.replace("%maxPoints%", String.valueOf(this.maxPoints));
		message = message.replace("%pointsPercent%",
				String.valueOf(this.format(this.percent(value, this.maxPoints), Config.percentPrecision)));

		message = message.replace("%timer%", TimerBuilder.getStringTime(value));
		message = message.replace("%maxTimer%", TimerBuilder.getStringTime(this.maxSecondsCap));

		message = message.replace("%timerSeconds%", String.valueOf(value));
		message = message.replace("%maxTimerSeconds%", String.valueOf(this.maxSecondsCap));

		message = message.replace("%timerPercent%",
				String.valueOf(this.format(this.percent(value, this.maxSecondsCap), Config.percentPrecision)));

		message = message.replace("%timerProgress%",
				this.getProgressBar(value, this.maxSecondsCap, Config.progressBarTimer));
		message = message.replace("%pointsProgress%",
				this.getProgressBar(value, this.maxPoints, Config.progressBarPoints));

		message = message.replace("%classicProgress%",
				this.getProgressBar(this.captureSeconds - seconds, this.captureSeconds, Config.progressBarClassic));
		message = message.replace("%classicPercent%", String.valueOf(this
				.format(this.percent(this.captureSeconds - seconds, this.captureSeconds), Config.percentPrecision)));

		String faction = this.currentPlayer == null ? Message.ZKOHT_EVENT_FACION.getMessage()
				: this.factionListener.getFactionTag(this.currentPlayer);
		message = message.replace("%faction%", faction);

		if (Config.replaceNoFaction != null && Config.enableReplaceNoFaction) {

			ReplaceConfig config = Config.replaceNoFaction;
			message = message.replace(config.getFrom(), config.getTo());

		}

		return message;
	}

	@Override
	public void playerMove(Player player, FactionListener factionListener) {

		if (!this.isEnable) {
			return;
		}

		if (this.kothType == KothType.POINT_COUNT) {
			return;
		}

		this.factionListener = factionListener;
		Cuboid cuboid = this.getCuboid();

		if (this.currentPlayer == null && cuboid.contains(player.getLocation())) {

			this.currentPlayer = player;
			this.startCap(player);

		} else if (this.currentPlayer != null && !cuboid.contains(this.currentPlayer.getLocation())) {

			KothLooseEvent event = new KothLooseEvent(this.currentPlayer, this);
			event.callEvent();

			if (event.isCancelled()) {
				return;
			}

			broadcast(Message.ZKOHT_EVENT_LOOSE);

			if (this.timerTask != null) {
				this.timerTask.cancel();
			}

			this.changeBlocks(Config.noOneCapturingMaterial);
			this.currentCaptureSeconds = new AtomicInteger(this.captureSeconds);
			this.timerTask = null;
			this.currentPlayer = null;
		}
	}

	private void startschedule() {
		this.scheduleFix(1000, 1000, (task, isCancelled) -> {

			this.timerTask = task;

			if (!isCancelled) {
				task.cancel();
				return;
			}

			if (!this.isEnable) {
				task.cancel();
				return;
			}

			Cuboid cuboid = this.getCuboid();
			int amount = 0;
			for (Player player : Bukkit.getOnlinePlayers()) {

				if (!cuboid.contains(player.getLocation())) {
					continue;
				}

				amount++;

				int value = this.playersValues.getOrDefault(player.getUniqueId(), 0) + 1;
				this.playersValues.put(player.getUniqueId(), value);

				if (value > this.maxPoints) {

					// WINNER
					this.currentPlayer = player;
					this.endKoth(task, cuboid, player);

					amount = -1;
				}
			}

			if (amount == 1) {
				this.changeBlocks(Config.onePersonneCapturingMaterial);
			} else if (amount > 1) {
				this.changeBlocks(Config.multiPersonneCapturingMaterial);
			} else {
				this.changeBlocks(Config.noOneCapturingMaterial);
			}

		});
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

		if (Config.enableStartCapMessage) {
			broadcast(Message.ZKOHT_EVENT_CATCH);
		}

		int captureSeconds = event.getCaptureSeconds();
		captureSeconds = captureSeconds < 0 ? 30 : captureSeconds;
		this.currentCaptureSeconds = new AtomicInteger(captureSeconds);
		Cuboid cuboid = getCuboid();

		this.changeBlocks(Config.onePersonneCapturingMaterial);

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

			int tmpCapture = this.currentCaptureSeconds.get();

			if (this.currentPlayer != null) {
				if (!this.currentPlayer.isValid() || !this.currentPlayer.isOnline()
						|| !cuboid.contains(this.currentPlayer.getLocation())) {
					this.currentPlayer = null;
				}
			}

			if (this.currentPlayer == null) {

				KothLooseEvent kothLooseEvent = new KothLooseEvent(this.currentPlayer, this);
				kothLooseEvent.callEvent();

				if (kothLooseEvent.isCancelled()) {
					return;
				}

				if (this.timerTask != null) {
					this.timerTask.cancel();
				}

				this.changeBlocks(Config.noOneCapturingMaterial);
				this.timerTask = null;
				this.currentPlayer = null;
				this.currentCaptureSeconds = new AtomicInteger(this.captureSeconds);

				if (Config.enableLooseCapMessage) {
					broadcast(Message.ZKOHT_EVENT_LOOSE);
				}
				return;

			}

			if (Config.displayMessageKothCap.contains(tmpCapture)) {
				broadcast(Message.ZKOHT_EVENT_TIMER);
			} else if (Config.enableEverySecondsCapMessage) {
				broadcast(Message.ZKOHT_EVENT_EVERYSECONDS);
			}

			if (this.hasWin()) {

				this.endKoth(task, cuboid, player);

			} else {

				KothCapEvent capEvent = new KothCapEvent(this, player, this.currentCaptureSeconds.get(),
						this.factionListener.getFactionTag(player));
				capEvent.callEvent();

				switch (this.getType()) {
				case CLASSIC:
				default:
					this.currentCaptureSeconds.decrementAndGet();
					break;
				case POINT:
				case TIMER:
					this.playersValues.put(this.currentPlayer.getUniqueId(), this.getValue(this.currentPlayer) + 1);
					break;
				}
			}
		});
	}

	@Override
	public boolean hasWin() {

		switch (this.getType()) {
		case CLASSIC:
			return this.currentCaptureSeconds != null && this.currentCaptureSeconds.get() <= 0;
		case POINT:
			return this.currentPlayer == null ? false : this.getValue(this.currentPlayer) >= this.maxPoints;
		case TIMER:
			return this.currentPlayer == null ? false : this.getValue(this.currentPlayer) >= this.maxSecondsCap;
		default:
			return false;
		}

	}

	@Override
	public void endKoth(TimerTask task, Cuboid cuboid, Player player) {
		KothWinEvent kothWinEvent = new KothWinEvent(this, this.currentPlayer);
		kothWinEvent.callEvent();

		if (kothWinEvent.isCancelled()) {
			return;
		}

		task.cancel();
		broadcast(Message.ZKOTH_EVENT_WIN);

		/* Gestion des loots */

		this.commands.forEach(command -> {
			if (command.contains("%online-player%")) {
				for (Player cPlayer : this.factionListener.getOnlinePlayer(player)) {
					String finaleCommand = replaceMessage(command);
					finaleCommand = finaleCommand.replace("%online-player%", cPlayer.getName());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), papi(finaleCommand, cPlayer));
				}
			} else {
				command = replaceMessage(command);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), papi(command, player));
			}
		});

		Location center = cuboid.getCenter();
		World world = center.getWorld();
		while (center.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
			center = center.getBlock().getRelative(BlockFace.DOWN).getLocation();
		}

		Location location = center;

		if (this.itemStacks.size() != 0) {
			switch (this.type) {
			case CHEST:
				location.getBlock().setType(Material.CHEST);
				Chest chest = (Chest) location.getBlock().getState();

				this.getItemStacks().forEach(itemStack -> chest.getInventory().addItem(itemStack));

				Bukkit.getScheduler().runTaskLater(ZPlugin.z(), () -> {
					location.getBlock().setType(Material.AIR);
				}, 20 * Config.removeChestSec);
				break;
			case DROP:
				location.add(0.5, 0.3, 0.5);
				this.getItemStacks().forEach(itemStack -> {

					Item item = world.dropItem(location, itemStack);
					Vector vector = item.getVelocity();
					vector.setZ(0);
					vector.setY(0.5);
					vector.setX(0);
					item.setVelocity(vector);

				});
				break;
			case INVENTORY:
				this.getItemStacks().forEach(itemStack -> give(this.currentPlayer, itemStack));
				break;
			case NONE:
				break;
			default:
				break;
			}
		}

		/* FIN Gestion des loots */

		this.isEnable = false;
		this.isCooldown = false;
		this.currentPlayer = null;
		this.timerTask = null;
		this.currentCaptureSeconds = null;
		this.playersValues.clear();
		this.resetBlocks();
		if (this.timerTaskStop != null) this.timerTaskStop.cancel();
	}

	@Override
	public CollectionConsumer<Player> onScoreboard() {
		return p -> {
			if (this.isCooldown) {
				return Config.scoreboardCooldown.stream().map(e -> papi(replaceMessage(e), p))
						.collect(Collectors.toList());
			} else {
				return Config.scoreboard.stream().map(e -> papi(replaceMessage(e), p)).collect(Collectors.toList());
			}
		};
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

	@Override
	public void setItemStacks(List<ItemStack> itemStacks) {
		this.itemStacks = itemStacks.stream().filter(e -> e != null).map(e -> encode(e)).collect(Collectors.toList());
	}

	@Override
	public void stop(CommandSender sender) {

		if (!this.isEnable) {
			message(sender, Message.ZKOTH_EVENT_DISABLE);
			return;
		}

		KothStopEvent event = new KothStopEvent(this);
		event.callEvent();

		if (event.isCancelled()) {
			return;
		}

		broadcast(Message.ZKOHT_EVENT_STOP);

		if (this.timerTask != null) {
			this.timerTask.cancel();
		}

		this.timerTask = null;
		this.isEnable = false;
		this.isCooldown = false;
		this.currentPlayer = null;
		this.timerTask = null;
		this.currentCaptureSeconds = null;
		this.playersValues.clear();
		this.resetBlocks();
		if (this.timerTaskStop != null) this.timerTaskStop.cancel();
	}

	@Override
	public void setCapture(int second) {
		this.captureSeconds = second;
	}

	@Override
	public int getCurrentSecond() {
		return this.currentCaptureSeconds == null ? 0 : this.currentCaptureSeconds.get();
	}

	@Override
	public String getCurrentPlayer() {
		return this.currentPlayer == null ? Message.ZKOHT_EVENT_PLAYER.getMessage() : this.currentPlayer.getName();
	}

	@Override
	public String getCurrentFaction() {
		return this.currentPlayer == null ? Message.ZKOHT_EVENT_FACION.getMessage()
				: this.factionListener.getFactionTag(this.currentPlayer);
	}

	@Override
	public KothType getType() {
		return this.kothType == null ? this.kothType = KothType.CLASSIC : this.kothType;
	}

	@Override
	public int getMaxSecondsCap() {
		return this.maxSecondsCap;
	}

	@Override
	public void setMaxSecondsCap(int seconds) {
		this.maxSecondsCap = seconds;
	}

	@Override
	public int getMaxPoints() {
		return this.maxPoints;
	}

	@Override
	public void setMaxPoints(int points) {
		this.maxPoints = points;
	}

	@Override
	public Map<UUID, Integer> getValues() {
		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}
		return this.playersValues;
	}

	@Override
	public int getValue(Player player) {
		if (this.playersValues == null) {
			this.playersValues = new HashMap<>();
		}
		return player == null ? 0 : this.playersValues.getOrDefault(player.getUniqueId(), 0);
	}

	@Override
	public void onPlayerLeave(Player player) {
		this.playersValues.remove(player.getUniqueId());
	}

	private Entry<UUID, Integer> getEntryAt(int position) {
		try {
			return this.playersValues.entrySet().stream()
					.sorted((f1, f2) -> Integer.compare(f2.getValue(), f1.getValue())).collect(Collectors.toList())
					.get(position - 1);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int getPointsAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return 0;
		}
		return entry.getValue();
	}

	@Override
	public int getTimerAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return 0;
		}
		return entry.getValue();
	}

	@Override
	public String getPointsPercentAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return "0";
		}
		return this.format(this.percent(entry.getValue(), this.maxPoints), Config.percentPrecision);
	}

	@Override
	public String getTimerPercentAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return "0";
		}
		return this.format(this.percent(entry.getValue(), this.maxSecondsCap), Config.percentPrecision);
	}

	@Override
	public String getTimerFormatAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return "0s";
		}
		return TimerBuilder.getStringTime(entry.getValue());
	}

	@Override
	public String getMaxTimerFormat() {
		return TimerBuilder.getStringTime(this.maxSecondsCap);
	}

	@Override
	public String getPointsProgressBarAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return "";
		}
		return this.getProgressBar(entry.getValue(), this.maxPoints, Config.progressBarPoints);
	}

	@Override
	public String getTimerProgressBarAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return "";
		}
		return this.getProgressBar(entry.getValue(), this.maxSecondsCap, Config.progressBarTimer);
	}

	@Override
	public String getClassicProgressBar() {
		return this.getProgressBar(this.captureSeconds - this.currentCaptureSeconds.get(), this.captureSeconds,
				Config.progressBarClassic);
	}

	@Override
	public String getTimerNameAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return Message.ZKOHT_EVENT_PLAYER.getMessage();
		}
		return Bukkit.getOfflinePlayer(entry.getKey()).getName();
	}

	@Override
	public String getPointsNameAt(int position) {
		Entry<UUID, Integer> entry = this.getEntryAt(position);
		if (entry == null) {
			return Message.ZKOHT_EVENT_PLAYER.getMessage();
		}
		return Bukkit.getOfflinePlayer(entry.getKey()).getName();
	}

	@Override
	public int getRandomItemStacks() {
		return this.randomItemStacks;
	}

	@Override
	public void setRandomItemStacks(int value) {
		this.randomItemStacks = value;
	}

	@Override
	public List<Location> getBlockLocations() {
		if (this.blockLocations == null) {
			this.blockLocations = new ArrayList<>();
		}
		return this.blockLocations.stream().map(this::changeStringLocationToLocation).collect(Collectors.toList());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeBlocks(Material material) {

		if (this.blockLocations == null || this.blockLocations.isEmpty()) {
			return;
		}

		if (this.lastMaterial != null && material == this.lastMaterial) {
			return;
		}
		this.lastMaterial = material;

		Bukkit.getOnlinePlayers().forEach(player -> {
			this.getBlockLocations().forEach(location -> {
				player.sendBlockChange(location, material, (byte) 0);
			});
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void resetBlocks() {

		if (this.blockLocations == null || this.blockLocations.isEmpty()) {
			return;
		}

		this.getBlockLocations().forEach(location -> {
			Bukkit.getOnlinePlayers().forEach(player -> {
				player.sendBlockChange(location, location.getBlock().getType(), (byte) 0);
			});
		});

	}

}
