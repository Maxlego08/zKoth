package fr.maxlego08.zkoth.scoreboard.implementations;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Scoreboard;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;

public class TitleManagerHook implements Scoreboard {

	private TitleManagerAPI api;

	@Override
	public void toggle(Player player, Consumer<Player> after) {
		if (api == null)
			api = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
		
		api.giveDefaultScoreboard(player);
	}

	@Override
	public void hide(Player player, Consumer<Player> after) {
		if (api == null)
			api = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
		
		api.removeScoreboard(player);
	}

}
