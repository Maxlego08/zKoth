package fr.maxlego08.koth;

import fr.maxlego08.koth.command.commands.CommandKoth;
import fr.maxlego08.koth.placeholder.LocalPlaceholder;
import fr.maxlego08.koth.save.MessageLoader;
import fr.maxlego08.koth.zcore.ZPlugin;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class KothPlugin extends ZPlugin {

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("zkoth");

        this.preEnable();

        this.registerCommand("zkoth", new CommandKoth(this), "koth");

        this.saveDefaultConfig();
        // this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));

        this.loadFiles();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

}
