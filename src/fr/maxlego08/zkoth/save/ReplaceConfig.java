package fr.maxlego08.zkoth.save;

import org.bukkit.configuration.file.YamlConfiguration;

public class ReplaceConfig {

	private final String from;
	private final String to;

	/**
	 * @param from
	 * @param to
	 */
	public ReplaceConfig(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}

	public ReplaceConfig(YamlConfiguration configuration) {
		this.from = configuration.getString("replaceNoFaction.from");
		this.to = configuration.getString("replaceNoFaction.to");
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReplaceConfig [from=" + from + ", to=" + to + "]";
	}

}
