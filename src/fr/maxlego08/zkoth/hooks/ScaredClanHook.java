package fr.maxlego08.zkoth.hooks;

import me.blueslime.scaredclans.api.ScaredClansAPI;
import me.blueslime.scaredclans.clan.Clan;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ScaredClanHook implements FactionListener {

	@Override
	public String getFactionTag(Player player) {
		Clan clan = ScaredClansAPI.getStorage().fetchClan(player);

		return (clan != null) ? clan.getTag() : player.getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		Clan clan = ScaredClansAPI.getStorage().fetchClan(player);

		return (clan != null) ? getOnlineMembers(clan) : Collections.emptyList();
	}

	public List<Player> getOnlineMembers(Clan clan) {
		List<Player> playerList = new ArrayList<>();
		for (String member : clan.getMembers()) {
			Player onlinePlayer = ScaredClansAPI.getStorage().isOnlineMode() ? Bukkit.getPlayer(UUID.fromString(member))
					: Bukkit.getPlayerExact(member);

			if (onlinePlayer != null) {
				playerList.add(onlinePlayer);
			}
		}
		return playerList;
	}
}