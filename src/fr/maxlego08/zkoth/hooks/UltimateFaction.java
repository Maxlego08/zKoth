package fr.maxlego08.zkoth.hooks;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.miinoo.factions.FactionsSystem;
import fr.maxlego08.zkoth.api.FactionListener;

public class UltimateFaction implements FactionListener {

	public UltimateFaction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFactionTag(Player player) {
		return FactionsSystem.getFactions().getFaction(player).getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		return FactionsSystem.getFactions().getFaction(player).getPlayers().stream()
				.map(e -> Bukkit.getOfflinePlayer(e)).filter(e -> e.isOnline()).map(e -> e.getPlayer())
				.collect(Collectors.toList());
	}

}
