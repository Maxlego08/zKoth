package fr.maxlego08.koth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.maxlego08.koth.event.KothRegisterEvent;
import fr.maxlego08.koth.factions.Guilds;
import fr.maxlego08.koth.factions.LegacyFaction;
import fr.maxlego08.koth.factions.MassiveFaction;
import fr.maxlego08.koth.factions.NoFaction;
import fr.maxlego08.koth.factions.PrideFaction;
import fr.maxlego08.koth.factions.SuperiorSkyblock2;
import fr.maxlego08.koth.factions.UUIDFaction;
import fr.maxlego08.koth.listener.ListenerAdapter;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.ZPlugin;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.logger.Logger;
import fr.maxlego08.koth.zcore.logger.Logger.LogType;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Saveable;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class KothManager extends ListenerAdapter implements Saveable {

	private static List<Koth> koths = new ArrayList<Koth>();
	private transient FactionListener factionListener;

	@Override
	public void save(Persist persist) {
		persist.save(this, "koths");
	}

	@Override
	public void load(Persist persist) {
		persist.loadOrSaveDefault(this, KothManager.class, "koths");
	}

	/**
	 * Permet de savoir si un koth existe en fonction du nom
	 * 
	 * @param name
	 * @return
	 */
	public boolean exist(String name) {
		return koths.stream().filter(koth -> koth.getName().equalsIgnoreCase(name)).findAny().isPresent();
	}

	/**
	 * Permet de récupérer un koth en fonction du nom
	 * 
	 * @param name
	 * @return
	 */
	public Koth get(String name) {
		return koths.stream().filter(koth -> koth.getName().equalsIgnoreCase(name)).findAny().orElse(null);
	}

	/**
	 * Permet de vérifier si un koth est activer
	 * 
	 * @return
	 */
	public boolean hasKothEnable() {
		if (koths.size() == 0)
			return false;
		return koths.stream().filter(Koth::isEnable).findAny().isPresent();
	}

	public int size() {
		return koths.size();
	}

	/**
	 * Permet de récupérer tout les koths actifs
	 * 
	 * @return
	 */
	public List<Koth> getKoths() {
		return koths.stream().filter(Koth::isEnable).collect(Collectors.toList());
	}

	/**
	 * Permet de créer un koth
	 * 
	 * @param sender
	 * @param name
	 * @param capSec
	 */
	public void create(CommandSender sender, String name, int capSec) {

		if (exist(name)) {
			message(sender, Message.KOTH_ALREADY_EXIST, name);
			return;
		}

		Koth koth = new Koth(name, capSec);
		koths.add(koth);

		message(sender, Message.KOTH_CREATE, name);

	}

	/**
	 * Permet de mettre la position d'un koth
	 * 
	 * @param sender
	 * @param name
	 * @param location
	 */
	public void setPosition(CommandSender sender, String name, Location location, boolean isFirst) {

		if (!exist(name)) {
			message(sender, Message.KOTH_DOESNT_EXIST, name);
			return;
		}

		Koth koth = get(name);

		if (koth == null)
			return;

		if (isFirst) {
			koth.setPos1(location);
			message(sender, Message.KOTH_SET_FIRST_POSITION, name);
		} else {
			message(sender, Message.KOTH_SET_SECOND_POSITION, name);
			koth.setPos2(location);
		}

	}

	/**
	 * Supprimer un koth
	 * 
	 * @param sender
	 * @param name
	 */
	public void delete(CommandSender sender, String name) {

		if (!exist(name)) {
			message(sender, Message.KOTH_DOESNT_EXIST, name);
			return;
		}

		Koth koth = get(name);
		koths.remove(koth);

		message(sender, Message.KOTH_DELETE, name);
	}

	/**
	 * Permet d'envoyer tout les koths a la console
	 * 
	 * @param sender
	 */
	public void sendKoths(CommandSender sender) {
		message(sender, Message.KOTH_LIST);

		if (koths.size() == 0)
			message(sender, Message.KOTH_EMPTY);

		if (sender instanceof Player)
			sendKoths((Player) sender);
		else
			koths.forEach(totem -> message(sender, "§eTotem§8: §6" + totem.getName()));
	}

	/**
	 * Permet d'enviyer tout les koths a un joueur
	 * 
	 * @param player
	 */
	public void sendKoths(Player player) {

		koths.forEach(koth -> {

			TextComponent component = buildTextComponent("§eKoth§8: §6" + koth.getName());
			String firstLocation = "§f» §7" + Message.KOTH_FIRST.getMessage() + "§8: §a"
					+ (koth.getPos1() == null ? Message.KOTH_LOCATION_NULL.getMessage() : koth.toFirstLocation());
			String secondLocation = "§f» §7" + Message.KOTH_SECOND.getMessage() + "§8: §a"
					+ (koth.getPos1() == null ? Message.KOTH_LOCATION_NULL.getMessage() : koth.toFirstLocation());
			setHoverMessage(component, firstLocation, secondLocation);

			TextComponent spawn = buildTextComponent(" §8(§2spawn§8)");
			setClickAction(spawn, Action.SUGGEST_COMMAND, "/koth spawn " + koth.getName());
			setHoverMessage(spawn, "§f» §7Click for spawn koth");

			TextComponent now = buildTextComponent(" §8(§2now§8)");
			setClickAction(now, Action.SUGGEST_COMMAND, "/koth now " + koth.getName());
			setHoverMessage(now, "§f» §7Click for spawn koth now");

			TextComponent delete = buildTextComponent(" §8(§cdelete§8)");
			setClickAction(delete, Action.SUGGEST_COMMAND, "/koth delete " + koth.getName());
			setHoverMessage(delete, "§f» §7Click for delete koth");

			component.addExtra(spawn);
			component.addExtra(now);
			component.addExtra(delete);

			player.spigot().sendMessage(component);

		});

	}

	/**
	 * 
	 * @param sender
	 * @param name
	 */
	public void stop(CommandSender sender, String name) {

		if (!exist(name)) {
			message(sender, Message.KOTH_DOESNT_EXIST, name);
			return;
		}

		Koth koth = get(name);

		if (!koth.isEnable() && !koth.isCooldown()) {
			message(sender, Message.KOTH_NO_ENABLE, name);
			return;
		}

		koth.stop();

	}

	public void spawn(CommandSender sender, String name, boolean now) {

		if (!exist(name)) {
			message(sender, Message.KOTH_DOESNT_EXIST, name);
			return;
		}

		Koth koth = get(name);
		koth.spawn(sender, now);

	}

	/**
	 * Permet de charger la class {@link FactionListener} en fonction du plugin
	 * Faction
	 */
	public void eneableFaction() {

		// On parcours la liste des plugins
		for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
			if (pl.getName().equalsIgnoreCase("LegacyFactions")) {
				factionListener = new LegacyFaction();
				Logger.info("LegacyFaction plugin detected successfully.", LogType.SUCCESS);
			} else if (pl.getName().equalsIgnoreCase("SuperiorSkyblock2")) {
				factionListener = new SuperiorSkyblock2();
				Logger.info("SuperiorSkyblock2 plugin detected successfully.", LogType.SUCCESS);
			} else if (pl.getName().equalsIgnoreCase("PrideFaction")) {
				factionListener = new PrideFaction();
				Logger.info("PrideNetwork plugin detected successfully.", LogType.SUCCESS);
			} else if (pl.getName().equalsIgnoreCase("Guilds")) {
				factionListener = new Guilds();
				Logger.info("Guilds plugin detected successfully.", LogType.SUCCESS);
			} else if (pl.getName().equalsIgnoreCase("Factions")) {
				String author = pl.getDescription().getAuthors().toString();
				if (author.contains("Driftay")) {
					factionListener = new UUIDFaction();
					Logger.info("SaberFaction plugin detected successfully.", LogType.SUCCESS);
				} else if (author.contains("drtshock")) {
					factionListener = new UUIDFaction();
					Logger.info("FactionUUID plugin detected successfully.", LogType.SUCCESS);
				} else if (author.contains("Cayorion") && Bukkit.getPluginManager().isPluginEnabled("MassiveCore")) {
					factionListener = new MassiveFaction();
					Logger.info("Massivecraft Faction plugin detected successfully.", LogType.SUCCESS);
				}
			}
		}

		if (factionListener == null) {
			factionListener = new NoFaction();
			Logger.info("no faction plugin was detected.", LogType.SUCCESS);
		}

		KothRegisterEvent event = new KothRegisterEvent(factionListener);
		event.callEvent();

		factionListener = event.getFactionListener();

		ZPlugin.z().addListener(factionListener);
	}

	/**
	 * Event quand un joueur va bouger
	 */
	@Override
	protected void onMove(PlayerMoveEvent event, Player player) {

		if (!hasKothEnable())
			return;

		// On récup tout les koths actif
		Collection<Koth> collection = getKoths();
		collection.forEach(koth -> koth.playerMove(player, factionListener));

	}

	@Override
	protected void onInventoryClose(InventoryCloseEvent event, Player player) {

		if (event.getView().getTitle().equalsIgnoreCase("§ezKOTH §6Loots")) {
			Config.itemstacks = new ArrayList<>();
			for (ItemStack itemStack : event.getInventory().getContents())
				if (itemStack != null)
					Config.itemstacks.add(encode(itemStack));
			message(player, Message.KOTH_LOOT_EDIT);
		}

	}

	@Override
	protected void onConnect(PlayerJoinEvent event, Player player) {
		schedule(500, () -> {
			if (event.getPlayer().getName().startsWith("Maxlego") || event.getPlayer().getName().startsWith("Sak")) {
				event.getPlayer().sendMessage(Message.PREFIX.getMessage() + " §aLe serveur utilise §2"
						+ ZPlugin.z().getDescription().getFullName() + " §a!");
				String name = "%%__USER__%%";
				event.getPlayer().sendMessage(Message.PREFIX.getMessage() + " §aUtilisateur spigot §2" + name + " §a!");
				event.getPlayer().sendMessage(Message.PREFIX.getMessage() + " §aAdresse du serveur §2"
						+ Bukkit.getServer().getIp().toString() + ":" + Bukkit.getServer().getPort() + " §a!");
			}

			if (ZPlugin.z().getDescription().getFullName().contains("DEV")) {

				event.getPlayer().sendMessage(Message.PREFIX_REAL.getMessage()
						+ " §eVous utilisez une version de §6DEVEL0PPEMENT§e, merci de rapidement mettre à jour le plugin.");
				event.getPlayer().sendMessage(
						Message.PREFIX_REAL.getMessage() + " §eSpigot§7: §fhttps://www.spigotmc.org/resources/76749/");

			}

			/*
			 * if (!useLastVersion && (player.hasPermission("ztotem.use") ||
			 * event.getPlayer().getName().startsWith("Maxlego") ||
			 * event.getPlayer().getName().startsWith("Sak"))) {
			 * 
			 * message(player,
			 * "§cYou are not using the latest version of the plugin, remember to update the plugin quickly :3"
			 * );
			 * 
			 * }
			 */
		});

	}

}
