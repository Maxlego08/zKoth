package fr.maxlego08.koth;

import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Savable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class KothManager extends ZUtils implements Savable {

    private final Map<UUID, Selection> selections = new HashMap<>();
    private final KothPlugin plugin;
    private final File folder;

    public KothManager(KothPlugin plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder(), "koths");
        if (!this.folder.exists()) this.folder.mkdir();
    }

    @Override
    public void save(Persist persist) {
        this.selections.values().forEach(Selection::clear);
    }

    @Override
    public void load(Persist persist) {

    }

    public Optional<Selection> getSelection(UUID uuid) {
        return Optional.ofNullable(this.selections.getOrDefault(uuid, null));
    }

    public ItemStack getKothAxe() {
        ItemBuilder builder = new ItemBuilder(Material.STONE_AXE, Message.AXE_NAME.getMessage());
        Message.AXE_DESCRIPTION.getMessages().forEach(builder::addLine);
        return builder.build();
    }

    public void createSelection(UUID uniqueId, Selection selection) {
        this.selections.put(uniqueId, selection);
    }
}
