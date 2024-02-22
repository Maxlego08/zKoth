package fr.maxlego08.koth.api;

import org.bukkit.entity.Player;

import java.util.function.Consumer;


public interface KothScoreboard {

	void toggle(Player player, Consumer<Player> after);

	void hide(Player player, Consumer<Player> after);

}
