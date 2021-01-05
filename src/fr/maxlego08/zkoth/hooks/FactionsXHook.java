package fr.maxlego08.zkoth.hooks;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;
import net.prosavage.factionsx.core.FPlayer;
import net.prosavage.factionsx.manager.PlayerManager;

public class FactionsXHook implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		FPlayer fPlayer = PlayerManager.INSTANCE.getFPlayer(player);
		return fPlayer.getFaction().getTag();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		FPlayer fPlayer = PlayerManager.INSTANCE.getFPlayer(player);
		return fPlayer.getFaction().getOnlineMembers().stream().map(e -> e.getPlayer()).collect(Collectors.toList());
	}

}
