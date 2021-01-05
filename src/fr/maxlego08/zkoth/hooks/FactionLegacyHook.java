package fr.maxlego08.zkoth.hooks;

import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;
import net.redstoneore.legacyfactions.entity.FPlayerColl;

public class FactionLegacyHook implements FactionListener {
	@Override
	public String getFactionTag(Player player) {
		return FPlayerColl.get(player).getFaction().getTag();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return FPlayerColl.get(player).getFaction().getOnlinePlayers();
	}

}
