package fr.maxlego08.koth;

import java.util.List;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class KothPlaceholderExpansion extends PlaceholderExpansion {

	private final KothManager manager;
	private final String version;

	public KothPlaceholderExpansion(KothManager manager, String version) {
		super();
		this.manager = manager;
		this.version = version;
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
		return version;
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {

		if (player == null)
			return null;

		if (manager.hasKothEnable()) {
			List<Koth> collection = (List<Koth>) manager.getKoths();
			if (collection.size() == 0)
				return null;

			Koth koth = collection.get(0);

			if (params.equals("name")) {
				return koth.getName();
			} else if (params.equals("centerX")) {
				return String.valueOf(koth.getCuboid().getCenter().getBlockX());
			} else if (params.equals("centerY")) {
				return String.valueOf(koth.getCuboid().getCenter().getBlockY());
			} else if (params.equals("centerZ")) {
				return String.valueOf(koth.getCuboid().getCenter().getBlockZ());
			} else if (params.equals("currentPlayer")) {
				return koth.getCurrentPlayer().getName();
			}
		}

		return null;

	}

}
