package fr.maxlego08.koth;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.koth.event.KothEvent;
import fr.maxlego08.koth.event.KothLooseEvent;
import fr.maxlego08.koth.event.KothLootEvent;
import fr.maxlego08.koth.event.KothSpawnEvent;
import fr.maxlego08.koth.event.KothStartEvent;
import fr.maxlego08.koth.event.KothStopEvent;
import fr.maxlego08.koth.event.KothWinEvent;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.Cuboid;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.builder.TimerBuilder;

public class Koth extends ZUtils {

	private final String name;
	private int capSec;
	private Location pos1;
	private Location pos2;
	private transient boolean isEnable;
	private transient boolean isCooldown;
	private transient int cooldown;
	private transient Player currentPlayer;
	private transient Cuboid cuboid;

	public Koth(String name, int capSec) {
		super();
		this.name = name;
		this.capSec = capSec;
	}

	/**
	 * @return the capSec
	 */
	public int getCapSec() {
		return capSec;
	}

	/**
	 * @param pos1
	 *            the pos1 to set
	 */
	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}

	/**
	 * @param pos2
	 *            the pos2 to set
	 */
	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pos1
	 */
	public Location getPos1() {
		return pos1;
	}

	/**
	 * @return the pos2
	 */
	public Location getPos2() {
		return pos2;
	}

	/**
	 * @return the isEnable
	 */
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @return the isCooldown
	 */
	public boolean isCooldown() {
		return isCooldown;
	}

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @return the cuboid
	 */
	public Cuboid getCuboid() {
		return cuboid;
	}

	/**
	 * 
	 * @return
	 */
	public String toFirstLocation() {
		return pos1.getBlockX() + ", " + pos1.getBlockY() + ", " + pos1.getBlockZ();
	}

	/**
	 * 
	 * @return
	 */
	public String toSecondLocation() {
		return pos2.getBlockX() + ", " + pos2.getBlockY() + ", " + pos2.getBlockZ();
	}

	/**
	 * Permet de stop un koth
	 */
	public void stop() {

		KothStopEvent event = new KothStopEvent(cuboid, this);
		event.callEvent();

		if (event.isCancelled())
			return;

		isEnable = false;
		isCooldown = false;
		currentPlayer = null;
		broadcast(Message.KOTH_STOP, null, null, 0);

	}

	/**
	 * Permet de start un koth
	 * 
	 * @param sender
	 * @param now
	 */
	public void spawn(CommandSender sender, boolean now) {

		if (pos1 == null) {
			message(sender, Message.KOTH_SET_FIRST_POSITION_NULL, name);
			return;
		}

		if (pos2 == null) {
			message(sender, Message.KOTH_SET_SECOND_POSITION_NULL, name);
			return;
		}

		if (isCooldown) {
			message(sender, Message.KOTH_SPAWN_COOLDOWN);
			return;
		}

		if (isEnable) {
			message(sender, Message.KOTH_SPAWN_ALREADY);
			return;
		}

		if (cuboid == null)
			buildCuboid();

		if (now)
			spawnNow(sender);
		else
			spawn(sender);

	}

	/**
	 * Permet de créer le Cuboid
	 * 
	 * @return
	 */
	public Cuboid buildCuboid() {
		if (pos1 == null || pos2 == null)
			return null;
		return cuboid = new Cuboid(pos1, pos2);
	}

	/**
	 * 
	 * @param sender
	 */
	private void spawn(CommandSender sender) {

		isCooldown = true;

		cooldown = Config.cooldownInSecond;

		scheduleFix(0, 1000, (task, canRun) -> {
			if (!canRun)
				return;

			if (!isCooldown) {
				task.cancel();
				return;
			}

			// Si on doit avetir
			if (Config.displayMessageCooldown.contains(cooldown))
				broadcast(Message.KOTH_SPAWN_MESSAGE_COOLDOWN, null, null, 0);

			// On fait spawn le totem
			if (cooldown <= 0) {
				task.cancel();
				isCooldown = false;
				spawnNow(sender);
			}

			cooldown--;
		});

	}

	/**
	 * Permet de faire spawn le totem sans cooldown
	 * 
	 * @param sender
	 */
	private void spawnNow(CommandSender sender) {

		KothSpawnEvent event = new KothSpawnEvent(cuboid, this);
		event.callEvent();

		if (event.isCancelled())
			return;

		isEnable = true;
		currentPlayer = null;
		buildCuboid();
		broadcast(Message.KOTH_SPAWN_MESSAGE, null, null, 0);

	}

	public void startCap(Player player, FactionListener listener) {

		KothEvent event = new KothStartEvent(player, this);
		event.callEvent();

		if (event.isCancelled())
			return;

		broadcast(Message.KOHT_CATCH, player, listener.getFactionTag(player), 0);

		if (capSec <= 0)
			capSec = Config.defaultCap;

		AtomicInteger timer = new AtomicInteger(capSec);

		scheduleFix(0, 1000, (task, isCancelled) -> {

			if (!isCancelled) {
				task.cancel();
				return;
			}

			if (!isEnable) {
				task.cancel();
				return;
			}

			int tmpTimer = timer.getAndDecrement();

			if (currentPlayer == null) {

				KothEvent kothEvent = new KothLooseEvent(this, player);
				kothEvent.callEvent();

				if (kothEvent.isCancelled())
					return;

				task.cancel();
				broadcast(Message.KOHT_LOOSE, player, listener.getFactionTag(player), tmpTimer);
				return;
			}

			if (Config.displayMessageKothCap.contains(tmpTimer))
				broadcast(Message.KOHT_TIMER, player, listener.getFactionTag(player), tmpTimer);

			if (tmpTimer == 0) {

				KothEvent kothEvent = new KothWinEvent(this, player);
				kothEvent.callEvent();

				if (kothEvent.isCancelled())
					return;

				task.cancel();
				broadcast(Message.KOHT_END, player, listener.getFactionTag(player), 0);

				isEnable = false;
				isCooldown = false;
				currentPlayer = null;

				KothLootManager lootManager = new KothLootManager(this, player, listener);
				
				KothLootEvent lootEvent = new KothLootEvent(lootManager);
				lootEvent.callEvent();

				if (lootEvent.isCancelled())
					return;

				lootManager.perform();

				// Méthode pour win

			}
		});

	}

	/**
	 * 
	 * @param message
	 * @param player
	 * @param currentFaction
	 * @param cooldown
	 */
	private void broadcast(Message message, Player player, String currentFaction, int cooldown) {
		String msg = message.getMessage();

		Location location = cuboid.getCenter();
		msg = msg.replace("%centerX%", String.valueOf(location.getBlockX()));
		msg = msg.replace("%centerY%", String.valueOf(location.getBlockY()));
		msg = msg.replace("%centerZ%", String.valueOf(location.getBlockZ()));
		msg = msg.replace("%pos1X%", String.valueOf(pos1.getBlockX()));
		msg = msg.replace("%pos1Y%", String.valueOf(pos1.getBlockY()));
		msg = msg.replace("%pos1Z%", String.valueOf(pos1.getBlockZ()));
		msg = msg.replace("%pos2X%", String.valueOf(pos2.getBlockX()));
		msg = msg.replace("%pos2Y%", String.valueOf(pos2.getBlockY()));
		msg = msg.replace("%pos2Z%", String.valueOf(pos2.getBlockZ()));
		msg = msg.replace("%player%", player == null ? "NULL" : player.getName());
		msg = msg.replace("%sec%", String.valueOf(cooldown));
		msg = msg.replace("%cooldown%", TimerBuilder.getStringTime(cooldown));
		msg = msg.replace("%currentFaction%", currentFaction == null ? "NULL" : currentFaction);
		msg = msg.replace("%currentPlayer%",
				this.currentPlayer == null ? Message.KOTH_NOONE.getMessage() : this.currentPlayer.getName());
		msg = msg.replace("%name%", String.valueOf(name));

		Bukkit.broadcastMessage(Message.PREFIX.getMessage() + " " + msg);
	}

	/**
	 * Quand un joueur bouge
	 * 
	 * @param player
	 * @param factionListener
	 */
	public void playerMove(Player player, FactionListener factionListener) {

		if (!isEnable)
			return;

		if (cuboid == null)
			return;

		// Si le mec est dans le koth
		if (currentPlayer == null && cuboid.contains(player.getLocation())) {

			currentPlayer = player;
			startCap(player, factionListener);

			// Le mec sort du koth
		} else if (currentPlayer == player && !cuboid.contains(player.getLocation())) {

			currentPlayer = null;

		}

	}

}
