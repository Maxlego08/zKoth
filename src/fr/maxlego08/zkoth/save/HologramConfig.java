package fr.maxlego08.zkoth.save;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.maxlego08.zkoth.zcore.utils.ZUtils;

public class HologramConfig extends ZUtils {

	private final boolean isEnable;
	private final String kothName;
	private final List<String> linge;
	private final Location location;

	/**
	 * @param isEnable
	 * @param kothName
	 * @param linge
	 */
	public HologramConfig(YamlConfiguration configuration, String path) {
		super();
		this.isEnable = configuration.getBoolean(path + "enable");
		this.kothName = configuration.getString(path + "koth");
		this.linge = configuration.getStringList(path + "lines");
		this.location = changeStringLocationToLocation(configuration.getString(path + "location"));
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the isEnable
	 */
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @return the kothName
	 */
	public String getKothName() {
		return kothName;
	}

	/**
	 * @return the linge
	 */
	public List<String> getLinge() {
		return linge;
	}

}
