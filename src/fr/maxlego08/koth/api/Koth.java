package fr.maxlego08.koth.api;

import fr.maxlego08.koth.api.discord.DiscordWebhookConfig;
import fr.maxlego08.koth.api.utils.HologramConfig;
import fr.maxlego08.koth.api.utils.ScoreboardConfiguration;
import fr.maxlego08.koth.zcore.utils.Cuboid;
import fr.maxlego08.koth.zcore.utils.interfaces.CollectionConsumer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Koth {

    String getFileName();

    KothType getKothType();

    String getName();

    void setName(String name);

    Location getMinLocation();

    Location getMaxLocation();

    Cuboid getCuboid();

    Location getCenter();

    List<String> getStartCommands();

    List<String> getEndCommands();

    void move(Location minLocation, Location maxLocation);

    int getCaptureSeconds();

    void setCaptureSeconds(int captureSeconds);

    ScoreboardConfiguration getCooldownScoreboard();

    ScoreboardConfiguration getStartScoreboard();

    KothStatus getStatus();

    void spawn(CommandSender sender, boolean now);

    void spawn(boolean now);

    void stop(CommandSender sender);

    void stop();

    void playerMove(Player player, KothTeam kothTeam);

    int getCooldownStart();

    int getStopAfterSeconds();

    boolean isEnableStartCapMessage();

    boolean isEnableLooseCapMessage();

    boolean isEnableEverySecondsCapMessage();

    CollectionConsumer<Player> onScoreboard();

    HologramConfig getHologramConfig();

    String replaceMessage(String string);

    DiscordWebhookConfig getDiscordWebhookConfig();

    List<ItemStack> getItemStacks();

    void setItemStacks(List<ItemStack> itemStacks);

    KothLootType getLootType();

    List<ItemStack> getRandomItemStacks();

    int getRandomItemStack();

    AtomicInteger getRemainingSeconds();

    Player getCurrentPlayer();
}
