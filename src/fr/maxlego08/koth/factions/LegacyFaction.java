package fr.maxlego08.koth.factions;

import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.koth.FactionListener;
import fr.maxlego08.koth.zcore.ZPlugin;
import net.redstoneore.legacyfactions.entity.FPlayerColl;

public class LegacyFaction implements FactionListener {

	public LegacyFaction() {
		ZPlugin.z().addListener(this);
	}

	@Override
	public String getFactionTag(Player player) {
		return FPlayerColl.get(player).getFaction().getTag();
	}

	@Override
	public String getFactionTag(String player) {
		return FPlayerColl.get(player).getFaction().getTag();
	}

	@Override
	public List<Player> getOnlinePlayer(String player) {
		return FPlayerColl.get(player).getFaction().getOnlinePlayers();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return getOnlinePlayer(player.getName());
	}

}
