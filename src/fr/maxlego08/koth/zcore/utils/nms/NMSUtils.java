package fr.maxlego08.koth.zcore.utils.nms;

import org.bukkit.Bukkit;
import fr.maxlego08.koth.zcore.logger.Logger;

public class NMSUtils {

	public static double version = getNMSVersion();

	public static boolean isTwoHand() {
		return !(version == 1.7 || version == 1.8);
	}

	/**
	 * Get minecraft server version
	 *
	 * @return version
	 */
	public static double getNMSVersion() {
		if (version != 0)
			return version;
		String var1 = Bukkit.getServer().getClass().getPackage().getName();
		String[] arrayOfString = var1.replace(".", ",").split(",");
		if (arrayOfString.length > 3) {
			String[] versionParts = arrayOfString[3].split("_");
			if (versionParts.length >= 2) {
				String var2 = versionParts[0].replace("v", "");
				String var3 = versionParts[1];
				return version = Double.parseDouble(var2 + "." + var3);
			} else {
				Logger.info("Unexpected NMS version format: " + var1);
				return version = 0; // default to 0 if the format is unexpected
			}
		} else {
			Logger.info("Unexpected NMS version format: " + var1);
			return version = 0; // default to 0 if the format is unexpected
		}
	}

	/**
	 * Check if minecraft version has shulker
	 *
	 * @return boolean
	 */
	public static boolean hasShulker() {
		return !isOneHand();
	}

	/**
	 * Check if minecraft version has barrel
	 *
	 * @return boolean
	 */
	public static boolean hasBarrel() {
		final double version = getNMSVersion();
		return !(version == 1.7 || version == 1.8 || version == 1.9 || version == 1.10 || version == 1.11
				|| version == 1.12 || version == 1.13);
	}

	/**
	 * Check if version is greater than 1.13
	 *
	 * @return boolean
	 */
	public static boolean isNewVersion() {
		return !isOldVersion();
	}

	/**
	 * Check if version has one hand
	 *
	 * @return boolean
	 */
	public static boolean isOneHand() {
		return getNMSVersion() == 1.7 || getNMSVersion() == 1.8;
	}

	/**
	 * Check is version is minecraft 1.7
	 *
	 * @return boolean
	 */
	public static boolean isVeryOldVersion() {
		return getNMSVersion() == 1.7;
	}

	/**
	 * Check if version has itemmeta unbreakable
	 *
	 * @return boolean
	 */
	public static boolean isUnbreakable() {
		return version == 1.7 || version == 1.8 || version == 1.9 || version == 1.10;
	}

	/**
	 * Check if version is old version of minecraft with old material system
	 *
	 * @return boolean
	 */
	public static boolean isOldVersion() {
		return version == 1.7 || version == 1.8 || version == 1.9 || version == 1.10 || version == 1.12
				|| version == 1.11;
	}

	/**
	 *
	 * Check if server version is new version
	 *
	 * @return boolean
	 */
	public static boolean isNewNMSVersion() {
		final double version = getNMSVersion();
		return !(version == 1.7 || version == 1.8 || version == 1.9 || version == 1.10 || version == 1.11
				|| version == 1.12 || version == 1.13 || version == 1.14 || version == 1.15 || version == 1.16);
	}

	/**
	 *
	 * Check if server version is new version
	 *
	 * @return boolean
	 */
	public static boolean isNewNBTVersion() {
		final double version = getNMSVersion();
		return !(version == 1.7 || version == 1.8 || version == 1.9 || version == 1.10 || version == 1.11
				|| version == 1.12 || version == 1.13 || version == 1.14 || version == 1.15 || version == 1.16
				|| version == 1.17);
	}

	/**
	 * Allows to check if the version has the colors in hex
	 *
	 * @return boolean
	 */
	public static boolean isHexColor() {
		return !(version == 1.7 || version == 1.8 || version == 1.9 || version == 1.10 || version == 1.11
				|| version == 1.12 || version == 1.13 || version == 1.14 || version == 1.15);
	}

}
