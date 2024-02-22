package fr.maxlego08.koth;

import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.api.events.KothCreateEvent;
import fr.maxlego08.koth.loader.KothLoader;
import fr.maxlego08.koth.zcore.enums.Message;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.builder.ItemBuilder;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import fr.maxlego08.koth.zcore.utils.storage.Persist;
import fr.maxlego08.koth.zcore.utils.storage.Savable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class KothManager extends ZUtils implements Savable {

    private final Loader<Koth> kothLoader = new KothLoader();
    private final List<Koth> koths = new ArrayList<>();
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

        File[] files = this.folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Koth koth = this.kothLoader.load(configuration, "", file);
            this.koths.add(koth);
        }

        System.out.println(koths);
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

    public void saveKoth(Koth koth) {

        File file = new File(this.folder, koth.getFileName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        this.kothLoader.save(koth, configuration, "");
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public void createKoth(Player player, String name, Location minLocation, Location maxLocation, int capture, KothType kothType) {

        Optional<Koth> optional = getKoth(name);
        if (optional.isPresent()) {
            message(player, Message.ALREADY_EXIST, "%name%", name);
            return;
        }

        int distance = Math.abs(minLocation.getBlockX() - maxLocation.getBlockY());
        if (distance <= 0) {
            message(player, Message.KOTH_SIZE);
            return;
        }

        String fileName = name.replace(" ", "_");
        Koth koth = new ZKoth(fileName, kothType, name, capture, minLocation, maxLocation, new ArrayList<>(), new ArrayList<>());

        KothCreateEvent event = new KothCreateEvent(koth);
        event.call();

        if (event.isCancelled()) return;

        Optional<Selection> optionalSelection = getSelection(player.getUniqueId());
        optionalSelection.ifPresent(Selection::clear);

        this.koths.add(koth);
        message(player, Message.CREATE_SUCCESS, "%name%", name);

        this.saveKoth(koth);
    }

    private Optional<Koth> getKoth(String name) {
        return this.koths.stream().filter(koth -> name != null && koth.getName().equalsIgnoreCase(name)).findFirst();
    }
}
