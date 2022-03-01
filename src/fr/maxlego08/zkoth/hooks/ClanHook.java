package fr.maxlego08.zkoth.hooks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import Clans.ClanConfiguration;
import Clans.Main;
import fr.maxlego08.zkoth.api.FactionListener;

public class ClanHook implements FactionListener {

	public ClanHook() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFactionTag(Player player) {
		Main main = (Main) Bukkit.getPluginManager().getPlugin("Clans");
		ClanConfiguration clanConfiguration = main.getClanConfiguration();
		String clan;
		return (clan = clanConfiguration.getClan(player)) == null ? player.getName() : clan;
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		Main main = (Main) Bukkit.getPluginManager().getPlugin("Clans");
		ClanConfiguration clanConfiguration = main.getClanConfiguration();
		String currentClan = clanConfiguration.getClan(player);
		if (currentClan == null)
			return Arrays.asList(player);
		return Bukkit.getOnlinePlayers().stream().filter(e -> {
			String tmpClan = clanConfiguration.getClan(e);
			if (tmpClan == null)
				return false;
			return tmpClan.equals(currentClan);
		}).collect(Collectors.toList());
	}

}
