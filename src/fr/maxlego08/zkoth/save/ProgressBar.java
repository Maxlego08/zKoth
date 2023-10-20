package fr.maxlego08.zkoth.save;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.maxlego08.zkoth.zcore.utils.ZUtils;

public class ProgressBar extends ZUtils{

	private final int lenght;
	private final char symbol;
	private final String completedColor;
	private final String notCompletedColor;

	/**
	 * @param lenght
	 * @param symbol
	 * @param completedColor
	 * @param notCompletedColor
	 */
	public ProgressBar(int lenght, char symbol, String completedColor, String notCompletedColor) {
		super();
		this.lenght = lenght;
		this.symbol = symbol;
		this.completedColor = completedColor;
		this.notCompletedColor = notCompletedColor;
	}

	public ProgressBar(YamlConfiguration configuration, String path) {
		this.lenght = configuration.getInt(path + "lenght");
		this.symbol = configuration.getString(path + "symbol").charAt(0);
		this.completedColor = color(configuration.getString(path + "completedColor"));
		this.notCompletedColor = color(configuration.getString(path + "notCompletedColor"));
	}

	/**
	 * @return the lenght
	 */
	public int getLenght() {
		return lenght;
	}

	/**
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * @return the completedColor
	 */
	public String getCompletedColor() {
		return completedColor;
	}

	/**
	 * @return the notCompletedColor
	 */
	public String getNotCompletedColor() {
		return notCompletedColor;
	}

}
