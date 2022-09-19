package fr.maxlego08.zkoth.scoreboard.implementations;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.xism4.sternalboard.SternalBoard;
import com.xism4.sternalboard.managers.ScoreboardManager;

import fr.maxlego08.zkoth.api.Scoreboard;

public class SternalBoardHook implements Scoreboard {

	private final ScoreboardManager manager;

	/**
	 * @param manager
	 */
	public SternalBoardHook(Plugin plugin) {
		super();	
		
		this.manager = SternalBoard.getInstance().getScoreboardManager();
	}

	@Override
	public void toggle(Player player, Consumer<Player> after) {
		this.manager.setScoreboard(player);
	}

	@Override
	public void hide(Player player, Consumer<Player> after) {
		this.manager.removeScoreboard(player);
	}

}
