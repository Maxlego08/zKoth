package fr.maxlego08.zkoth.zcore.board;

import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ColorBoard extends ZBoard {

	private Team redTeam;
	private Team greenTeam;

	public ColorBoard() {

		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		this.greenTeam = board.getTeams().stream().filter(e -> {
			System.out.println(e);
			System.out.println(e.getName());
			return e.getName().equals("zkothgreenteam");
		}).findFirst().orElseGet(new Supplier<Team>() {
			@Override
			public Team get() {
				return board.registerNewTeam("zkothgreenteam");
			}
		});
		this.greenTeam.setColor(ChatColor.GREEN);

		this.redTeam = board.getTeams().stream().filter(e -> e.getName().equals("zkothredteam")).findFirst()
				.orElseGet(new Supplier<Team>() {
					@Override
					public Team get() {
						return board.registerNewTeam("zkothredteam");
					}
				});
		this.redTeam.setColor(ChatColor.RED);

	}

	@Override
	public void addEntity(boolean isGreen, LivingEntity entity) {

		if (entity == null) {
			return;
		}

		String uuid = entity.getUniqueId().toString();

		this.redTeam.removeEntry(uuid);
		this.greenTeam.removeEntry(uuid);

		if (isGreen) {
			this.greenTeam.addEntry(uuid);
		} else {
			this.redTeam.addEntry(uuid);
		}

	}

}
