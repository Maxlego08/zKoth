package fr.maxlego08.koth;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface FactionListener extends Listener{

	/**
	 * 
	 * @param player
	 * @return
	 */
	String getFactionTag(Player player);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	String getFactionTag(String player);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	List<Player> getOnlinePlayer(String player);
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	List<Player> getOnlinePlayer(Player player);
	
	/**
	 * 
	 */
	//void factionTagChange(TotemFaction totemFaction, String newName);
	
}