package fr.maxlego08.zkoth.scoreboard.implementations;

import org.bukkit.entity.Player;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import fr.maxlego08.zkoth.api.Scoreboard;

public class FeatherBoardHook implements Scoreboard {

	public FeatherBoardHook() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void toggle(Player player) {
		if (!FeatherBoardAPI.isToggled(player))
			FeatherBoardAPI.toggle(player, true);
	}

	@Override
	public void hide(Player player) {
		if (FeatherBoardAPI.isToggled(player))
			FeatherBoardAPI.toggle(player, true);
	}

}
