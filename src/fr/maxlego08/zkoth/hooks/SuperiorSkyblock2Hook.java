package fr.maxlego08.zkoth.hooks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import fr.maxlego08.zkoth.api.FactionListener;

public class SuperiorSkyblock2Hook implements FactionListener {

	private SuperiorSkyblock plugin = SuperiorSkyblockAPI.getSuperiorSkyblock();

	private Island getIsland(Player player) {
		if (plugin == null)
			plugin = SuperiorSkyblockAPI.getSuperiorSkyblock();
		return plugin.getGrid().getIsland(SuperiorSkyblockAPI.getPlayer(player));
	}

	@Override
	public String getFactionTag(Player player) {
		Island island = getIsland(player);
		return island == null ? player.getName() : getIsland(player).getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		Island island = getIsland(player);
		if (island == null)
			return Arrays.asList(player);
		List<Player> players = island.getIslandMembers(true).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId()))
				.filter(p -> p.isOnline()).map(p -> p.getPlayer()).collect(Collectors.toList());
		return players;
	}

}
