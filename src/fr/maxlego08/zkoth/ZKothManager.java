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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import fr.maxlego08.zkoth.api.FactionListener;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.api.Selection;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.api.event.events.KothCreateEvent;
import fr.maxlego08.zkoth.api.event.events.KothHookEvent;
import fr.maxlego08.zkoth.api.event.events.KothMoveEvent;
import fr.maxlego08.zkoth.api.event.events.KothStopEvent;
import fr.maxlego08.zkoth.api.event.events.KothWinEvent;
import fr.maxlego08.zkoth.hooks.FactionMassiveHook;
import fr.maxlego08.zkoth.hooks.FactionsHook;
import fr.maxlego08.zkoth.hooks.FactionsXHook;
import fr.maxlego08.zkoth.hooks.GuildsHook;
import fr.maxlego08.zkoth.hooks.DefaultHook;
import fr.maxlego08.zkoth.hooks.FactionLegacyHook;
import fr.maxlego08.zkoth.hooks.SuperiorSkyblock2Hook;
import fr.maxlego08.zkoth.listener.ListenerAdapter;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.scoreboard.ScoreBoardManager;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.logger.Logger;
import fr.maxlego08.zkoth.zcore.logger.Logger.LogType;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;
import fr.maxlego08.zkoth.zcore.utils.ZSelection;
import fr.maxlego08.zkoth.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.zkoth.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.zkoth.zcore.utils.storage.Persist;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class ZKothManager extends ListenerAdapter implements KothManager {

	private static List<ZKoth> koths = new ArrayList<ZKoth>();

	private final transient ScoreBoardManager manager;
	private final transient String itemName = "§6✤ §ezKoth axe §6✤";
	private final transient Map<UUID, Selection> selections = new HashMap<UUID, Selection>();
	private transient FactionListener factionListener;
	private transient long playerMoveEventCooldown = 0;

	/**
	 * @param plugin
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

		PluginManager pluginManager = Bukkit.getPluginManager();

		if (pluginManager.isPluginEnabled("FactionsX")) {

			factionListener = new FactionsXHook();
			Logger.info("FactionsX plugin detected successfully.", LogType.SUCCESS);

		} else if (pluginManager.isPluginEnabled("SuperiorSkyblock2")) {

			factionListener = new SuperiorSkyblock2Hook();
			Logger.info("SuperiorSkyblock2 plugin detected successfully.", LogType.SUCCESS);

		} else if (pluginManager.isPluginEnabled("Guilds")) {

			factionListener = new GuildsHook();
			Logger.info("Guilds plugin detected successfully.", LogType.SUCCESS);

		} else if (pluginManager.isPluginEnabled("LegacyFactions")) {

			factionListener = new FactionLegacyHook();
			Logger.info("LegacyFactions plugin detected successfully.", LogType.SUCCESS);

		} else if (pluginManager.isPluginEnabled("Factions")) {

			Plugin plugin = pluginManager.getPlugin("Factions");
			List<String> authors = plugin.getDescription().getAuthors();

			if (authors.contains("Cayorion") && pluginManager.isPluginEnabled("MassiveCore")) {

				factionListener = new FactionMassiveHook();
				Logger.info("MassiveCraft plugin detected successfully.", LogType.SUCCESS);

			} else if (authors.contains("Savag3life")) {

				factionListener = new FactionsHook();
				Logger.info("SavageFaction plugin detected successfully.", LogType.SUCCESS);

			} else if (authors.contains("Driftay")) {

				factionListener = new FactionsHook();
				Logger.info("SaberFaction plugin detected successfully.", LogType.SUCCESS);

			} else if (authors.contains("drtshock")) {

				factionListener = new FactionsHook();
				Logger.info("FactionUUID plugin detected successfully.", LogType.SUCCESS);

			}

		} else

		{
			factionListener = new DefaultHook();
			Logger.info("No plugin was detected.", LogType.SUCCESS);
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
			org.bukkit.event.block.Action action = event.getAction();
			selection.action(action, location);
			String message = (action.equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) ? Message.ZKOTH_AXE_POS1
					: Message.ZKOTH_AXE_POS2).getMessage();
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

	@Override
	public void onKothStop(KothStopEvent event, Koth koth) {
		manager.clearBoard();
	}

	@Override
	public void sendKothList(CommandSender sender) {

		if (sender instanceof ConsoleCommandSender) {

			message(sender, "§fKoths§8: §f"
					+ toList(koths.stream().map(e -> e.getName()).collect(Collectors.toList()), "§8", "§7"));

		} else {

			Player player = (Player) sender;
			message(player, "§fKoths§8:");
			koths.forEach(koth -> buildKothMessage(player, koth));

		}

	}

	/**
	 * 
	 * @param player
	 * @param koth
	 */
	private void buildKothMessage(Player player, Koth koth) {

		TextComponent component = buildTextComponent("§f§l» §7" + koth.getName() + " ");

		Cuboid cuboid = koth.getCuboid();
		Location center = cuboid.getCenter();
		String location = center.getWorld().getName() + ", " + center.getBlockX() + ", " + center.getBlockY() + ", "
				+ center.getBlockZ();

		setHoverMessage(component, "§7Coordinate: " + location);
		setClickAction(component, Action.SUGGEST_COMMAND, "/koth info " + koth.getName());

		TextComponent spawn = buildTextComponent("§8(§2Spawn§8)");
		setClickAction(spawn, Action.SUGGEST_COMMAND, "/koth spawn " + koth.getName());
		setHoverMessage(spawn, "§7Click for spawn koth");

		TextComponent now = buildTextComponent(" §8(§aSpawn now§8)");
		setClickAction(now, Action.SUGGEST_COMMAND, "/koth now " + koth.getName());
		setHoverMessage(now, "§7Click for spawn koth now");

		TextComponent delete = buildTextComponent(" §8(§cDelete§8)");
		setClickAction(delete, Action.SUGGEST_COMMAND, "/koth delete " + koth.getName());
		setHoverMessage(delete, "§7Click for delete koth");

		component.addExtra(spawn);
		component.addExtra(now);
		component.addExtra(delete);

		player.spigot().sendMessage(component);

	}

	@Override
	public void showInformations(CommandSender sender, String name) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		Cuboid cuboid = koth.getCuboid();
		Location center = cuboid.getCenter();
		String location = center.getWorld().getName() + ", " + center.getBlockX() + ", " + center.getBlockY() + ", "
				+ center.getBlockZ();

		message(sender, "§fName: §b%s", koth.getName());
		message(sender, "§fCoordinate: §b%s", location);
		message(sender, "§fLoot type: §b%s", koth.getLootType().name());
		message(sender, "§fCommands §8(§7" + koth.getCommands().size() + "§8):");
		if (sender instanceof ConsoleCommandSender) {
			koth.getCommands().forEach(command -> messageWO(sender, " §7" + command));
			message(sender, "§dHow to add command ? §d/zkoth addc <koth> <command>");
		} else {

			Player player = (Player) sender;

			for (int a = 0; a != koth.getCommands().size(); a++) {

				TextComponent textComponent = buildTextComponent(" §b#" + (a + 1) + " §f" + koth.getCommands().get(0));
				setClickAction(textComponent, Action.SUGGEST_COMMAND,
						"/koth removec " + koth.getName() + " " + (a + 1));
				setHoverMessage(textComponent, "§7Click for remove command");
				player.spigot().sendMessage(textComponent);

			}

			TextComponent textComponent = buildTextComponent(
					Message.PREFIX.getMessage() + " §fHow to add command ? §d/zkoth §daddc §d<koth> §d<command>");
			setClickAction(textComponent, Action.SUGGEST_COMMAND, "/koth addc " + koth.getName() + " ");
			setHoverMessage(textComponent, "§7Click for add command");
			player.spigot().sendMessage(textComponent);
		}

	}

	@Override
	public void addCommand(CommandSender sender, String name, String command) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		koth.addCommand(command);
		message(sender, "§fYou have just added the command §8\"§7" + command + "§8\"");
	}

	@Override
	public void removeCommand(CommandSender sender, String name, int id) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		koth.removeCommand(id);
		message(sender, "§7You have just deleted a command.");

	}

	@Override
	public void setKothLoot(CommandSender sender, String name, LootType type) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		koth.setLootType(type);
		message(sender, "§7You have just set the type to §f%s§7.", type.name().toLowerCase());
	}

	@Override
	public void updateLoots(Player player, String name) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(player, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		Inventory inventory = Bukkit.createInventory(null, 54, "§8Loots: " + name);
		int slot = 0;
		for (ItemStack itemStack : koth.getItemStacks()) {
			inventory.setItem(slot, itemStack);
		}

		player.openInventory(inventory);

	}

	@Override
	protected void onInventoryClose(InventoryCloseEvent event, Player player) {

		InventoryView view = event.getView();
		String title = view.getTitle();

		if (title.startsWith("§8Loots: ")) {

			String name = title.replace("§8Loots: ", "");
			Optional<Koth> optional = getKoth(name);
			if (!optional.isPresent()) {
				message(player, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
				return;
			}

			Koth koth = optional.get();
			List<ItemStack> itemStacks = new ArrayList<>();
			for (ItemStack itemStack : event.getInventory().getContents())
				if (itemStack != null)
					itemStacks.add(itemStack);

			koth.setItemStacks(itemStacks);
			message(player, "§aYou have just modified the loots of the koth §2%s.", koth.getName());
		}

	}

	@Override
	public void stopKoth(CommandSender sender, String name) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		koth.stop(sender);

	}

	@Override
	public void setCaptureSeconds(CommandSender sender, String name, int second) {

		Optional<Koth> optional = getKoth(name);
		if (!optional.isPresent()) {
			message(sender, Message.ZKOTH_DOESNT_EXIST.replace("%name%", name));
			return;
		}

		Koth koth = optional.get();
		koth.setCapture(second);
		message(sender, "§aYou have just modified the capture time of the koth §n%s§a to §f%s§a.", koth.getName(),
				TimerBuilder.getStringTime(second));
	}

	@Override
	public List<String> getKothNames() {
		return koths.stream().map(e -> e.getName()).collect(Collectors.toList());
	}

}
