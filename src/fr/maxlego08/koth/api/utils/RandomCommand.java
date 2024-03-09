package fr.maxlego08.koth.api.utils;

import fr.maxlego08.koth.zcore.utils.ZUtils;

import java.util.List;

public class RandomCommand extends ZUtils {

    private final int percent;
    private final List<String> commands;

    public RandomCommand(int percent, List<String> commands) {
        this.percent = percent;
        this.commands = commands;
    }

    public int getPercent() {
        return percent;
    }

    public List<String> getCommands() {
        return commands;
    }

    public boolean canUse() {
        return percent >= getNumberBetween(0, 100);
    }
}
