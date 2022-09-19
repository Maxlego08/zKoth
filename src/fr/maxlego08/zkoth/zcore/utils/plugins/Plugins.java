package fr.maxlego08.zkoth.zcore.utils.plugins;

public enum Plugins {

	VAULT("Vault"),
	ESSENTIALS("Essentials"), 
	FEATHERBOARD("FeatherBoard"),
	TAB("TAB"),
	PLACEHOLDERAPI("PlaceholderAPI"),
	TITLEMANAGER("TitleManager"),
	STERNALBOARD("SternalBoard"),
	SIMPLECORE("SimpleScore"),
	
	;

	private final String name;

	private Plugins(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
