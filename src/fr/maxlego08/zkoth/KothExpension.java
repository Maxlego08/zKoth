package fr.maxlego08.zkoth;

import java.util.List;
import java.util.Optional;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.KothManager;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.zcore.utils.builder.TimerBuilder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class KothExpension extends PlaceholderExpansion {

	private final Plugin plugin;
	private final KothManager manager;

	/**
	 * @param plugin
	 * @param manager
	 */
	public KothExpension(Plugin plugin, KothManager manager) {
		super();
		this.plugin = plugin;
		this.manager = manager;
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
		return this.plugin.getDescription().getVersion();
	}

	@Override
	public String onRequest(OfflinePlayer player, String params) {

		List<Koth> koths = this.manager.getEnableKoths();
		if (koths.size() >= 1) {

			Koth koth = koths.get(0);
			String value = this.getExpension(koth, params);
			// Pour éviter de ne pas allez à la suitr
			if (value != null) {
				return value;
			}

		}

		if (!params.contains("_")) {
			return Config.defaultNoKoth;
		}

		String[] args = params.split("_");

		if (args.length < 2) {
			return Config.defaultNoKoth;
		}

		String kothName = args[0];
		StringBuilder builder = new StringBuilder();
		for (int a = 1; a < args.length; a++) {
			builder.append(args[a]);
			if (a != args.length - 1) {
				builder.append("_");
			}
		}

		Optional<Koth> optional = this.manager.getKoth(kothName);
		if (optional.isPresent()) {

			Koth koth = optional.get();
			return this.getExpension(koth, builder.toString());

		}

		return Config.defaultNoKoth;
	}

	/**
	 * Permet d'obtenir les placeholders pour un koth
	 * 
	 * @param koth
	 * @param string
	 * @return true
	 */
	private String getExpension(Koth koth, String string) {
		if (string.equals("name")) {

			return koth.getName();
		} else if (string.equals("capture")) {

			return String.valueOf(koth.getCurrentSecond());
		} else if (string.equals("capture_formated")) {

			return TimerBuilder.getStringTime(koth.getCurrentSecond());
		} else if (string.equals("x")) {

			return String.valueOf(koth.getCenter().getBlockX());
		} else if (string.equals("y")) {

			return String.valueOf(koth.getCenter().getBlockY());
		} else if (string.equals("z")) {

			return String.valueOf(koth.getCenter().getBlockZ());
		} else if (string.equals("world")) {

			return koth.getCenter().getWorld().getName();
		} else if (string.equals("faction")) {

			return String.valueOf(koth.getCurrentFaction());
		} else if (string.equals("player")) {

			return koth.getCurrentPlayer();
		} else if (string.equals("classic_progress")) {

			return String.valueOf(koth.getClassicProgressBar());
		} else if (string.equals("max_points")) {

			return String.valueOf(koth.getMaxPoints());
		} else if (string.equals("max_timer")) {

			return String.valueOf(koth.getMaxSecondsCap());

		} else if (string.equals("max_timer_format")) {

			return String.valueOf(koth.getMaxTimerFormat());

		} else if (string.startsWith("points_percent_")) {

			try {
				int position = Integer.valueOf(string.replace("points_percent_", ""));
				return String.valueOf(koth.getPointsPercentAt(position));
			} catch (Exception e) {
			}

		} else if (string.startsWith("points_progress_")) {

			try {
				int position = Integer.valueOf(string.replace("points_progress_", ""));
				return String.valueOf(koth.getPointsProgressBarAt(position));
			} catch (Exception e) {
			}

		} else if (string.startsWith("timer_percent_")) {

			try {
				int position = Integer.valueOf(string.replace("timer_percent_", ""));
				return String.valueOf(koth.getTimerPercentAt(position));
			} catch (Exception e) {
			}

		} else if (string.startsWith("timer_progress_")) {

			try {
				int position = Integer.valueOf(string.replace("timer_progress_", ""));
				return String.valueOf(koth.getTimerProgressBarAt(position));
			} catch (Exception e) {
			}

		} else if (string.startsWith("timer_format_")) {

			try {
				int position = Integer.valueOf(string.replace("timer_format_", ""));
				return String.valueOf(koth.getTimerFormatAt(position));
			} catch (Exception e) {
			}

		} else if (string.startsWith("timer_name_")) {

			try {
				int position = Integer.valueOf(string.replace("timer_name_", ""));
				return koth.getTimerNameAt(position);
			} catch (Exception e) {
			}

		} else if (string.startsWith("points_name_")) {

			try {
				int position = Integer.valueOf(string.replace("points_name_", ""));
				return koth.getPointsNameAt(position);
			} catch (Exception e) {
			}

		} else if (string.startsWith("points_")) {

			try {
				int position = Integer.valueOf(string.replace("points_", ""));
				return String.valueOf(koth.getPointsAt(position));
			} catch (Exception e) {
			}

		} else if (string.startsWith("timer_")) {

			try {
				int position = Integer.valueOf(string.replace("timer_", ""));
				return String.valueOf(koth.getTimerAt(position));
			} catch (Exception e) {
			}
		}
		return null;
	}
}
