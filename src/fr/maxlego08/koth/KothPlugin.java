package fr.maxlego08.koth;

import fr.maxlego08.koth.api.KothScoreboard;
import fr.maxlego08.koth.command.commands.CommandKoth;
import fr.maxlego08.koth.hook.ScoreboardPlugin;
import fr.maxlego08.koth.hook.scoreboard.DefaultHook;
import fr.maxlego08.koth.placeholder.LocalPlaceholder;
import fr.maxlego08.koth.save.Config;
import fr.maxlego08.koth.save.MessageLoader;
import fr.maxlego08.koth.storage.StorageManager;
import fr.maxlego08.koth.zcore.ZPlugin;
import fr.maxlego08.koth.zcore.logger.Logger;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class KothPlugin extends ZPlugin {

    private KothManager kothManager;
    private StorageManager storageManager;
    private KothScoreboard kothScoreboard = new DefaultHook();

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("zkoth");

        this.preEnable();

        this.storageManager = new StorageManager(this);
        this.kothManager = new KothManager(this);

        this.registerCommand("zkoth", new CommandKoth(this), "koth");

        this.saveDefaultConfig();
        // this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));
        this.addSave(this.kothManager);

        this.addListener(new KothListener(this, this.kothManager));

        Config.getInstance().load(this);
        this.loadFiles();

        for (ScoreboardPlugin value : ScoreboardPlugin.values()) {
            if (value.isEnable()) {
                kothScoreboard = value.init(this);
                Logger.info("Register " + value.getPluginName() + " scoreboard implementation.", Logger.LogType.INFO);
                break;
            }
        }

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

    public KothManager getKothManager() {
        return this.kothManager;
    }

    public KothScoreboard getKothScoreboard() {
        return this.kothScoreboard;
    }

    public StorageManager getStorageManager() {
        return this.storageManager;
    }
}
