package fr.maxlego08.zkoth;

import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.KothManager;
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
		return plugin.getDescription().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {

		List<Koth> koths = manager.getEnableKoths();
		if (koths.size() >= 1) {

			Koth koth = koths.get(0);
			if (params.equals("name"))
				return koth.getName();
			else if (params.equals("capture"))
				return String.valueOf(koth.getCurrentSecond());
			else if (params.equals("x"))
				return String.valueOf(koth.getCenter().getBlockX());
			else if (params.equals("y"))
				return String.valueOf(koth.getCenter().getBlockY());
			else if (params.equals("z"))
				return String.valueOf(koth.getCenter().getBlockZ());
			else if (params.equals("faction"))
				return String.valueOf(koth.getCurrentFaction());
			else if (params.equals("player"))
				return koth.getCurrentPlayer();

		}

		if (!params.contains("_"))
			return null;

		String[] args = params.split("_");

		if (args.length != 2)
			return null;

		String kothName = args[0];
		String string = args[1];

		Optional<Koth> optional = manager.getKoth(kothName);
		if (optional.isPresent()) {

			Koth koth = optional.get();

			if (string.equals("name"))
				return koth.getName();
			else if (string.equals("capture"))
				return String.valueOf(koth.getCurrentSecond());
			else if (string.equals("x"))
				return String.valueOf(koth.getCenter().getBlockX());
			else if (string.equals("y"))
				return String.valueOf(koth.getCenter().getBlockY());
			else if (string.equals("z"))
				return String.valueOf(koth.getCenter().getBlockZ());
			else if (string.equals("faction"))
				return String.valueOf(koth.getCurrentFaction());			
			else if (string.equals("player"))
				return koth.getCurrentPlayer();

		}

		return null;
	}

}
