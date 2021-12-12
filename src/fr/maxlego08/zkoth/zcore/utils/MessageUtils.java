package fr.maxlego08.zkoth.zcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.zcore.enums.Message;
import fr.maxlego08.zkoth.zcore.utils.players.ActionBar;

public abstract class MessageUtils extends LocationUtils {

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void message(CommandSender player, Message message) {
		player.sendMessage(Message.PREFIX.msg() + papi(message.msg(), player));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void message(CommandSender player, String message) {
		player.sendMessage(Message.PREFIX.msg() + papi(message, player));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void broadcast(String message) {
		Bukkit.broadcastMessage(Message.PREFIX.msg() + message);
	}

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void message(CommandSender player, String message, Object... args) {
		player.sendMessage(Message.PREFIX.msg() + String.format(papi(message, player), args));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void messageWO(CommandSender player, Message message) {
		player.sendMessage(papi(message.msg(), player));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void messageWO(CommandSender player, String message) {
		player.sendMessage(papi(message, player));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 */
	protected void messageWO(CommandSender player, String message, Object... args) {
		player.sendMessage(String.format(papi(message, player), args));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 * @param args
	 */
	protected void messageWO(CommandSender player, Message message, Object... args) {
		player.sendMessage(String.format(papi(message.getMessage(), player), args));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 * @param args
	 */
	protected void message(CommandSender player, Message message, Object... args) {
		player.sendMessage(Message.PREFIX.msg() + String.format(papi(message.msg(), player), args));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 * @param args
	 */
	protected void actionMessage(Player player, Message message, Object... args) {
		ActionBar.sendActionBar(player, String.format(papi(message.msg(), player), args));
	}

	/**
	 * 
	 * @param player
	 * @param message
	 * @param args
	 */
	protected void broadcastAction(String message) {
		for (Player player : Bukkit.getOnlinePlayers())
			ActionBar.sendActionBar(player, papi(message, player));
	}

}
