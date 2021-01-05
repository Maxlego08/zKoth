package fr.maxlego08.zkoth.hooks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.FactionListener;
import me.glaremasters.guilds.api.GuildsAPI;
import me.glaremasters.guilds.guild.Guild;

public class GuildsHook implements FactionListener {

	private GuildsAPI api = me.glaremasters.guilds.Guilds.getApi();

	@Override
	public String getFactionTag(Player player) {

		if (api == null)
			api = me.glaremasters.guilds.Guilds.getApi();

		Guild guild = api.getGuild(player);
		
		if (guild == null)
			return player.getName();
		
		return guild.getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {
		
		if (api == null)
			api = me.glaremasters.guilds.Guilds.getApi();
		
		Guild guild = api.getGuild(player);
		
		if (guild == null)
			return Arrays.asList(player);
		
		return guild.getOnlineAsPlayers();
	}

}
