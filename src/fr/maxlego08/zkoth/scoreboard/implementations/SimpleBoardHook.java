package fr.maxlego08.zkoth.scoreboard.implementations;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.r4g3baby.simplescore.SimpleScore;

import fr.maxlego08.zkoth.api.Scoreboard;

public class SimpleBoardHook implements Scoreboard {

	private final Plugin plugin;

	/**
	 * @param plugin
	 */
	public SimpleBoardHook(Plugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	public void toggle(Player player, Consumer<Player> after) {
		SimpleScore.Api.getManager().getPlayersData().get(player).show(this.plugin);
	}

	@Override
	public void hide(Player player, Consumer<Player> after) {
		SimpleScore.Api.getManager().getPlayersData().get(player).hide(this.plugin);
	}

}
