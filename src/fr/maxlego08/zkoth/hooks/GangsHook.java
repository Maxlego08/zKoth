package fr.maxlego08.zkoth.hooks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;
import net.brcdev.gangs.GangsPlugin;
import net.brcdev.gangs.gang.Gang;

public class GangsHook implements FactionListener {

	public GangsHook() {
	}

	@Override
	public String getFactionTag(Player player) {
		Gang gang = GangsPlugin.getInstance().getGangManager().getPlayersGang(player);
		return gang != null ? gang.getName() : player.getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		Gang gang = GangsPlugin.getInstance().getGangManager().getPlayersGang(player);
		return gang != null ? new ArrayList<>(gang.getOnlineMembers()) : Arrays.asList(player);
	}

}
