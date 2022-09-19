package fr.maxlego08.zkoth.scoreboard.implementations;

import java.util.function.Consumer;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Scoreboard;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;

public class TabPremiumHook implements Scoreboard {

	@Override
	public void toggle(Player player, Consumer<Player> after) {

		TabAPI api = TabAPI.getInstance();
		TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());
		ScoreboardManager manager = TabAPI.getInstance().getScoreboardManager();
		if (tabPlayer != null && manager != null) {
			manager.toggleScoreboard(tabPlayer, true);
		}
	}

	@Override
	public void hide(Player player, Consumer<Player> after) {
		TabAPI api = TabAPI.getInstance();
		TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());
		ScoreboardManager manager = TabAPI.getInstance().getScoreboardManager();
		if (tabPlayer != null && manager != null) {
			manager.toggleScoreboard(tabPlayer, false);
		}
	}

}
