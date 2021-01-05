package fr.maxlego08.zkoth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.FactionListener;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.api.Selection;
import fr.maxlego08.zkoth.api.event.events.KothCreateEvent;
import fr.maxlego08.zkoth.api.event.events.KothHookEvent;
import fr.maxlego08.zkoth.api.event.events.KothMoveEvent;
import fr.maxlego08.zkoth.api.event.events.KothWinEvent;
import fr.maxlego08.zkoth.hooks.NoFaction;
import fr.maxlego08.zkoth.listener.ListenerAdapter;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.scoreboard.ScoreBoardManager;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.utils.ZSelection;
import fr.maxlego08.zkoth.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.zkoth.zcore.utils.storage.Persist;

public class ZKothManager extends ListenerAdapter implements KothManager {

	private static List<ZKoth> koths = new ArrayList<ZKoth>();

	private final transient ScoreBoardManager manager;
	private final transient String itemName = "§6✤ §ezKoth axe §6✤";
	private final transient Map<UUID, Selection> selections = new HashMap<UUID, Selection>();
	private transient FactionListener factionListener;
	private transient long playerMoveEventCooldown = 0;

	/**
	 * @param manager
	 */
	public ZKothManager(ScoreBoardManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public void save(Persist persist) {
		persist.save(this, "koths");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(this, ZKothManager.class, "koths");

		/* Enable faction */

		if (factionListener == null) {
			factionListener = new NoFaction();

		}

		KothHookEvent event = new KothHookEvent(factionListener);
		event.callEvent();

		factionListener = event.getFactionListener();
	}

	@Override
	public Optional<Koth> getKoth(String name) {
		Optional<ZKoth> zKoth = koths.stream().filter(koth -> koth.getName().equalsIgnoreCase(name)).findFirst();
		return zKoth.isPresent() ? Optional.of(zKoth.get()) : Optional.empty();
	}

	@Override
	public void createKoth(CommandSender sender, String name, Location minLocation, Location maxLocation,
			int captureSeconds) {

		Optional<Koth> optional = getKoth(name);
		if (optional.isPresent()) {
			message(sender, Message.ZKOTH_ALREADY_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = new ZKoth(name, captureSeconds, minLocation, maxLocation);

		KothCreateEvent event = new KothCreateEvent(koth);
		event.callEvent();

		if (event.isCancelled())
			return;

		koths.add((ZKoth) koth);
		message(sender, Message.ZKOTH_CREATE_SUCCESS.replace("%name%", name));
	}

	@Override
	public ItemStack getKothAxe() {
		ItemBuilder builder = new ItemBuilder(Material.STONE_AXE, itemName);
		builder.addLine("§8§m-+------------------------------+-");
		builder.addLine("");
		builder.addLine("§f§l» §7Allows you to select a zone to create a koth");
		builder.addLine(" §7§oYou must select an area with the right click");
		builder.addLine(" §7§oand left then do the command /koth create <name>");
		builder.addLine("");
		builder.addLine("§8§m-+------------------------------+-");
		return builder.build();
	}

	@Override
	protected void onInteract(PlayerInteractEvent event, Player player) {
		@SuppressWarnings("deprecation")
		ItemStack itemStack = player.getItemInHand();
		if (itemStack != null && event.getClickedBlock() != null && same(itemStack, itemName)) {

			event.setCancelled(true);
			Optional<Selection> optional = getSelection(player.getUniqueId());
			Selection selection = null;
			if (!optional.isPresent()) {
				selection = new ZSelection();
				this.selections.put(player.getUniqueId(), selection);
			} else
				selection = optional.get();

			Location location = event.getClickedBlock().getLocation();
			Action action = event.getAction();
			selection.action(action, location);
			String message = (action.equals(Action.LEFT_CLICK_BLOCK) ? Message.ZKOTH_AXE_POS1 : Message.ZKOTH_AXE_POS2)
					.getMessage();
			message = message.replace("%x%", String.valueOf(location.getBlockX()));
			message = message.replace("%y%", String.valueOf(location.getBlockY()));
			message = message.replace("%z%", String.valueOf(location.getBlockZ()));
			message = message.replace("%world%", location.getWorld().getName());
			message(player, message);
		}
	}

	@Override
	public Optional<Selection> getSelection(UUID uuid) {
		return Optional.ofNullable(selections.getOrDefault(uuid, null));
	}

	@Override
	protected void onConnect(PlayerJoinEvent event, Player player) {

		if (this.hasActiveKoth())
			if (Config.enableScoreboard)
				manager.createBoard(player, Config.scoreboardTitle);

		schedule(500, () -> {
			if (event.getPlayer().getName().startsWith("Maxlego") || event.getPlayer().getName().startsWith("Sak")) {
				String version = ZPlugin.z().getDescription().getFullName();
				message(player, "§aLe serveur utilise §2%s§a!", version);
				String name = "%%__USER__%%";
				message(player, "§aUtilisateur spigot §2%s §a!", name);
			}

		});
	}

	@Override
	protected void onQuit(PlayerQuitEvent event, Player player) {
		if (Config.enableScoreboard)
			manager.delete(player);
	}

	@Override
	public void deleteKoth(CommandSender sender, String name) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		ZKoth koth = (ZKoth) optional.get();
		koths.remove(koth);
		message(sender, Message.ZKOTH_DELETE_SUCCESS.replace("%name%", name));
	}

	@Override
	public void moveKoth(CommandSender sender, Location maxLocation, Location minLocation, String name) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		KothMoveEvent event = new KothMoveEvent(koth, maxLocation, minLocation);
		event.callEvent();

		if (event.isCancelled())
			return;

		koth.move(minLocation, maxLocation);
		message(sender, Message.ZKOTH_MOVE_SUCCESS.replace("%name%", name));

	}

	@Override
	public void spawnKoth(CommandSender sender, String name, boolean isNow) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		koth.spawn(sender, isNow);

		if (Config.enableScoreboard) {

			manager.setLinesAndSchedule(koth.onScoreboard());
			for (Player player : Bukkit.getOnlinePlayers())
				manager.createBoard(player, Config.scoreboardTitle);

		}
	}

	@Override
	public List<Koth> getActiveKoths() {
		return koths.stream().filter(koth -> koth.isEnable() && !koth.isCooldown()).collect(Collectors.toList());
	}

	@Override
	protected void onMove(PlayerMoveEvent event, Player player) {

		if (this.playerMoveEventCooldown != 0 && this.playerMoveEventCooldown > System.currentTimeMillis()) {

			// Do nothin

		} else {

			this.playerMoveEventCooldown = System.currentTimeMillis() + Config.playerMoveEventCooldown;
			List<Koth> koths = getActiveKoths();
			koths.forEach(koth -> koth.playerMove(player, factionListener));

		}

	}

	@Override
	public boolean hasActiveKoth() {
		return koths.stream().anyMatch(koth -> koth.isEnable());
	}

	@Override
	public void onKothWin(KothWinEvent event, Koth koth) {
		manager.clearBoard();
	}

}
