package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothStatus;
import fr.maxlego08.koth.placeholder.LocalPlaceholder;
import fr.maxlego08.koth.placeholder.ReturnBiConsumer;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.zcore.utils.ReturnConsumer;
import fr.maxlego08.koth.zcore.utils.builder.TimerBuilder;
import org.bukkit.entity.Player;

import java.util.Optional;

public class KothPlaceholder {

    private final KothManager kothManager;

    public KothPlaceholder(KothManager kothManager) {
        this.kothManager = kothManager;
    }

    public void register() {

        this.register("name", Koth::getName);
        this.register("world", koth -> koth.getCenter().getWorld().getName());
        this.register("min_x", koth -> String.valueOf(koth.getMinLocation().getBlockX()));
        this.register("min_y", koth -> String.valueOf(koth.getMinLocation().getBlockY()));
        this.register("min_z", koth -> String.valueOf(koth.getMinLocation().getBlockZ()));
        this.register("max_x", koth -> String.valueOf(koth.getMaxLocation().getBlockX()));
        this.register("max_y", koth -> String.valueOf(koth.getMaxLocation().getBlockY()));
        this.register("max_z", koth -> String.valueOf(koth.getMaxLocation().getBlockZ()));
        this.register("center_x", koth -> String.valueOf(koth.getCenter().getBlockX()));
        this.register("center_y", koth -> String.valueOf(koth.getCenter().getBlockY()));
        this.register("center_z", koth -> String.valueOf(koth.getCenter().getBlockZ()));

        this.register("spawn_seconds", koth -> String.valueOf(koth.getRemainingSeconds() == null ? koth.getCaptureSeconds() : koth.getRemainingSeconds().get()));
        this.register("spawn_format", koth -> TimerBuilder.getStringTime(koth.getRemainingSeconds() == null ? koth.getCaptureSeconds() : koth.getRemainingSeconds().get()));
        this.register("capture_format", koth -> String.valueOf(koth.getRemainingSeconds() == null ? koth.getCaptureSeconds() : koth.getRemainingSeconds().get()));
        this.register("capture_seconds", koth -> TimerBuilder.getStringTime(koth.getRemainingSeconds() == null ? koth.getCaptureSeconds() : koth.getRemainingSeconds().get()));
        this.register("capture_max_seconds", koth -> String.valueOf(koth.getCaptureSeconds()));
        this.register("capture_max_formats", koth -> TimerBuilder.getStringTime(koth.getCaptureSeconds()));

        this.registerPosition("score_player_", (position, koth) -> koth.getPlayer(position).getPlayerName());
        this.registerPosition("score_points_", (position, koth) -> String.valueOf(koth.getPlayer(position).getPoints()));
        this.registerPosition("score_team_name_", (position, koth) -> koth.getPlayer(position).getTeamName());
        this.registerPosition("score_team_id_", (position, koth) -> koth.getPlayer(position).getTeamId());
        this.registerPosition("score_team_leader_", (position, koth) -> koth.getPlayer(position).getTeamLeader());
        this.register("score", (player, koth) -> String.valueOf(koth.getScore(player)));

        this.register("player_name", koth -> koth.getCurrentPlayer() != null ? koth.getCurrentPlayer().getName() : Config.noPlayer);
        this.register("team_name", koth -> koth.getCurrentPlayer() != null ? this.kothManager.getKothTeam().getTeamName(koth.getCurrentPlayer()) : Config.noFaction);
        this.register("team_leader", koth -> koth.getCurrentPlayer() != null ? this.kothManager.getKothTeam().getLeaderName(koth.getCurrentPlayer()) : Config.noFaction);
        this.register("team_id", koth -> koth.getCurrentPlayer() != null ? this.kothManager.getKothTeam().getTeamId(koth.getCurrentPlayer()) : Config.noFaction);

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.register("active_", (player, args) -> {
            Optional<Koth> optional = this.kothManager.getKoth(args);
            return String.valueOf(optional.filter(koth -> koth.getStatus() != KothStatus.STOP).isPresent());
        });
        placeholder.register("cooldown_", (player, args) -> {
            Optional<Koth> optional = this.kothManager.getKoth(args);
            return String.valueOf(optional.filter(koth -> koth.getStatus() == KothStatus.COOLDOWN).isPresent());
        });
        placeholder.register("start_", (player, args) -> {
            Optional<Koth> optional = this.kothManager.getKoth(args);
            return String.valueOf(optional.filter(koth -> koth.getStatus() == KothStatus.START).isPresent());
        });
    }

    private void register(String key, ReturnConsumer<Koth> consumer) {
        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.register(key, (a, b) -> onFirstKoth(consumer));
    }

    private void register(String key, ReturnBiConsumer<Player, Koth, String> consumer) {
        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.register(key, (a, b) -> onFirstKoth(a, consumer));
    }

    private void registerS(String key, ReturnBiConsumer<String, Koth, String> consumer) {
        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.register(key, (a, b) -> onFirstKoth(b, consumer));
    }

    private void registerPosition(String key, ReturnBiConsumer<Integer, Koth, String> consumer) {
        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.register(key, (a, b) -> onFirstKothPosition(b, consumer));
    }

    public String onFirstKoth(ReturnConsumer<Koth> consumer) {
        Optional<Koth> optional = this.kothManager.getActiveKoths().stream().findFirst();
        if (optional.isPresent()) {
            return consumer.accept(optional.get());
        } else return Config.noKoth;
    }

    public String onFirstKoth(Player player, ReturnBiConsumer<Player, Koth, String> consumer) {
        Optional<Koth> optional = this.kothManager.getActiveKoths().stream().findFirst();
        if (optional.isPresent()) {
            return consumer.accept(player, optional.get());
        } else return Config.noKoth;
    }

    public String onFirstKoth(String argument, ReturnBiConsumer<String, Koth, String> consumer) {
        Optional<Koth> optional = this.kothManager.getActiveKoths().stream().findFirst();
        if (optional.isPresent()) {
            return consumer.accept(argument, optional.get());
        } else return Config.noKoth;
    }

    public String onFirstKothPosition(String argument, ReturnBiConsumer<Integer, Koth, String> consumer) {
        Optional<Koth> optional = this.kothManager.getActiveKoths().stream().findFirst();
        if (optional.isPresent()) {
            try {
                return consumer.accept(Integer.parseInt(argument), optional.get());
            } catch (Exception exception) {
                return consumer.accept(-1, optional.get());
            }
        } else return Config.noKoth;
    }

}
