package fr.maxlego08.koth.hook;

import fr.maxlego08.koth.KothPlugin;
import fr.maxlego08.koth.api.KothTeam;
import fr.maxlego08.koth.hook.teams.HuskTownHook;
import fr.maxlego08.koth.hook.teams.LandHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public enum TeamPlugin {

    LANDS("Lands", LandHook.class),
    HUSKTOWN("HuskTowns", HuskTownHook.class),

    ;

    private final String pluginName;
    private final Class<? extends KothTeam> teamHook;

    TeamPlugin(String pluginName, Class<? extends KothTeam> teamHook) {
        this.pluginName = pluginName;
        this.teamHook = teamHook;
    }

    public String getPluginName() {
        return pluginName;
    }

    public boolean isEnable() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(this.pluginName);
        return plugin != null && plugin.isEnabled();
    }

    public KothTeam init(KothPlugin plugin) {
        try {
            return teamHook.getConstructor(KothPlugin.class).newInstance(plugin);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
