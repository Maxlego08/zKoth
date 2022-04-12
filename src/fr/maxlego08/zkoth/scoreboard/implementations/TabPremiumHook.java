package fr.maxlego08.zkoth.scoreboard.implementations;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Scoreboard;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import me.neznamy.tab.api.TABAPI;
import me.neznamy.tab.api.TabPlayer;

public class TabPremiumHook implements Scoreboard {

	@Override
	public void toggle(Player player, Consumer<Player> after) {
		TabPlayer tabPlayer = TABAPI.getPlayer(player.getUniqueId());
		if (tabPlayer != null && !tabPlayer.isScoreboardVisible()) {
			tabPlayer.toggleScoreboard(false);
		}
	}

	@Override
	public void hide(Player player, Consumer<Player> after) {
		TabPlayer tabPlayer = TABAPI.getPlayer(player.getUniqueId());
		if (tabPlayer != null && tabPlayer.isScoreboardVisible()) {
			tabPlayer.toggleScoreboard(false);
		} else {
			Bukkit.getScheduler().runTaskLater(ZPlugin.z(), () -> {
				final TabPlayer tabPlayer2 = TABAPI.getPlayer(player.getUniqueId());
				if (tabPlayer2 != null && tabPlayer2.isScoreboardVisible()) {
					tabPlayer2.toggleScoreboard(false);
					if (after != null)
						after.accept(player);
				}
			}, 20);
		}
	}

}
