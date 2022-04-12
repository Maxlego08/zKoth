package fr.maxlego08.zkoth.hooks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.maxlego08.zkoth.api.FactionListener;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

public class SimpleClanHook implements FactionListener {

	private final SimpleClans clan;

	/**
	 * @param clan
	 */
	public SimpleClanHook(Plugin clan) {
		super();
		this.clan = (SimpleClans) clan;
	}

	@Override
	public String getFactionTag(Player player) {

		ClanPlayer clanPlayer = this.clan.getClanManager().getClanPlayer(player);

		if (clanPlayer == null) {
			return player.getName();
		}

		Clan clan = clanPlayer.getClan();

		if (clan == null) {
			return player.getName();
		}

		return clan.getName();
	}

	@Override
	public List<Player> getOnlinePlayer(Player player) {

		ClanPlayer clanPlayer = this.clan.getClanManager().getClanPlayer(player);

		if (clanPlayer == null) {
			return Arrays.asList(player);
		}

		Clan clan = clanPlayer.getClan();

		if (clan == null) {
			return Arrays.asList(player);
		}

		return clan.getOnlineMembers().stream().map(e -> Bukkit.getOfflinePlayer(e.getUniqueId()))
				.filter(OfflinePlayer::isOnline).map(OfflinePlayer::getPlayer).collect(Collectors.toList());

	}

}
