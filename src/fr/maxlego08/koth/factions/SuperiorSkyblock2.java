package fr.maxlego08.koth.factions;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import fr.maxlego08.koth.FactionListener;

public class SuperiorSkyblock2 implements FactionListener {

	private SuperiorSkyblock plugin = SuperiorSkyblockAPI.getSuperiorSkyblock();

	private Island getIsland(Player player) {
		
		System.out.println("Plugin - test1 " + plugin);
		
		if (plugin == null)
			plugin = SuperiorSkyblockAPI.getSuperiorSkyblock();
		
		System.out.println("Plugin - test2 " + plugin);
		
		System.out.println("Plugin - test3 " + SuperiorSkyblockAPI.getPlayer(player));
		
		
		if (plugin != null){
			
			System.out.println("Plugin - test4 " + plugin.getGrid());
			
			if (plugin.getGrid() != null)
			
				System.out.println("Plugin - test5 " + plugin.getGrid().getIsland(SuperiorSkyblockAPI.getPlayer(player)));
			
		}
		
		return plugin.getGrid().getIsland(SuperiorSkyblockAPI.getPlayer(player));
	}

	@Override
	public String getFactionTag(Player player) {
		return getIsland(player).getName();
	}

	@Override
	public String getFactionTag(String player) {
		return null;
	}

	@Override
	public List<Player> getOnlinePlayer(String player) {
		return null;
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		Island island = getIsland(player);
		List<Player> players = island.getIslandMembers(true).stream().map(p -> Bukkit.getOfflinePlayer(p.getUniqueId()))
				.filter(p -> p.isOnline()).map(p -> p.getPlayer()).collect(Collectors.toList());
		return players;
	}

}
