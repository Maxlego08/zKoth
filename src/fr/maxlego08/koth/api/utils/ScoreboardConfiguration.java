package fr.maxlego08.koth.api.utils;

import java.util.Collections;
import java.util.List;

public class ScoreboardConfiguration {

    private final boolean isEnable;
    private final String title;
    private final List<String> lines;

    public ScoreboardConfiguration(boolean isEnable, String title, List<String> lines) {
        this.isEnable = isEnable;
        this.title = title;
        this.lines = lines;
    }

    public ScoreboardConfiguration() {
        this(false, "zKoth", Collections.singletonList("Default Configuration"));
    }

    public boolean isEnable() {
        return isEnable;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }
}
