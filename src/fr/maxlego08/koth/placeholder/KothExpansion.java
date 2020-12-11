package fr.maxlego08.koth.placeholder;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.maxlego08.koth.Koth;
import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.builder.TimerBuilder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class KothExpansion extends PlaceholderExpansion {

	private final ZKoth plugin;

	/**
	 * @param plugin
	 */
	public KothExpansion(ZKoth plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public String getAuthor() {
		return "Maxlego08";
	}

	@Override
	public String getIdentifier() {
		return "zkoth";
	}

	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {

		if (params.startsWith("enable_")) {

			String name = params.replace("enable_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return String.valueOf(koth.isEnable());
			}

		} else if (params.startsWith("cooldown_")) {

			String name = params.replace("cooldown_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return String.valueOf(koth.getCooldown());
			}

		} else if (params.startsWith("caps_")) {

			String name = params.replace("caps_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return String.valueOf(koth.getCapSec());
			}

		} else if (params.startsWith("timer_")) {

			String name = params.replace("timer_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return String.valueOf(koth.getTimer().get());
			}

		} else if (params.startsWith("timer_format_")) {

			String name = params.replace("timer_format_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return TimerBuilder.getStringTime(koth.getTimer().get());
			}

		} else if (params.startsWith("current_player_")) {

			String name = params.replace("current_player_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return koth.getCurrentPlayer() == null ? Message.KOTH_NONE.getMessage()
						: koth.getCurrentPlayer().getName();
			}

		} else if (params.startsWith("current_faction_")) {

			String name = params.replace("current_faction_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				return koth.getFaction();
			}

		} else if (params.startsWith("location_")) {

			String name = params.replace("location_", "");
			Optional<Koth> optional = plugin.getManager().getByName(name);
			if (optional.isPresent()) {
				Koth koth = optional.get();
				Location location = koth.getCuboid().getCenter();
				String l = Config.placeholderLocation;
				l = l.replace("%x%", String.valueOf(location.getBlockX()));
				l = l.replace("%y%", String.valueOf(location.getBlockY()));
				l = l.replace("%z%", String.valueOf(location.getBlockZ()));
				l = l.replace("%world%", location.getWorld().getName());
				return l;
			}

		} else if (plugin.getManager().hasKothEnable()) {

			List<Koth> collection = plugin.getManager().getKoths();
			if (collection.size() == 0)
				return null;

			Koth koth = collection.get(0);

			if (params.equals("name")) {
				return koth.getName();
			} else if (params.equals("x")) {
				return String.valueOf(koth.getCuboid().getCenter().getBlockX());
			} else if (params.equals("y")) {
				return String.valueOf(koth.getCuboid().getCenter().getBlockY());
			} else if (params.equals("z")) {
				return String.valueOf(koth.getCuboid().getCenter().getBlockZ());
			} else if (params.equals("player")) {
				return koth.getCurrentPlayer() == null ? Message.KOTH_NONE.getMessage()
						: koth.getCurrentPlayer().getName();
			} else if (params.equals("faction")) {
				return koth.getFaction();
			} else if (params.equals("timer_format")) {
				return TimerBuilder.getStringTime(koth.getTimer().get());
			} else if (params.equals("timer")) {
				return String.valueOf(koth.getTimer().get());
			}

		}

		return null;

	}

}
