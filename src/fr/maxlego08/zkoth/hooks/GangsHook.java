package fr.maxlego08.zkoth.hooks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;
import net.brcdev.gangs.GangsPlugin;

public class GangsHook implements FactionListener {

	public GangsHook() {
	}

	@Override
	public String getFactionTag(Player player) {
		return GangsPlugin.getInstance().getGangManager().getPlayersGang(player).getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return new ArrayList<Player>(
				GangsPlugin.getInstance().getGangManager().getPlayersGang(player).getOnlineMembers());
	}

}
