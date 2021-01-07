package fr.maxlego08.zkoth.api;

import org.bukkit.entity.Player;

public interface Scoreboard {

	/**
	 * 
	 * @param player
	 */
	public void toggle(Player player);
	
	/**
	 * 
	 * @param player
	 */
	public void hide(Player player);
	
}
