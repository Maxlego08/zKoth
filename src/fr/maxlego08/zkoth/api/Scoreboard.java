package fr.maxlego08.zkoth.api;

import java.util.function.Consumer;

import org.bukkit.entity.Player;


public interface Scoreboard {

	/**
	 * 
	 * @param player
	 */
	public void toggle(Player player, Consumer<Player> after);
	
	/**
	 * 
	 * @param player
	 */
	public void hide(Player player, Consumer<Player> after);
	
}
