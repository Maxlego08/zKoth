package fr.maxlego08.koth.loader;

import fr.maxlego08.koth.ZKoth;
import fr.maxlego08.koth.api.Koth;
import fr.maxlego08.koth.api.KothType;
import fr.maxlego08.koth.zcore.utils.ZUtils;
import fr.maxlego08.koth.zcore.utils.loader.Loader;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class KothLoader extends ZUtils implements Loader<Koth> {

    private final Loader<Location> locationLoader = new LocationLoader();

    @Override
    public Koth load(YamlConfiguration configuration, String path, File file) {

        String fileName = getFileNameWithoutExtension(file);
        KothType kothType = KothType.valueOf(configuration.getString("type", KothType.CAPTURE.name()).toUpperCase());
        String name = configuration.getString("name");
        int captureSeconds = configuration.getInt("capture");
        List<String> startCommands = configuration.getStringList("startCommands");
        List<String> endCommands = configuration.getStringList("endCommands");
        Location minLocation = locationLoader.load(configuration, "minLocation.", file);
        Location manLocation = locationLoader.load(configuration, "maxLocation.", file);

        return new ZKoth(fileName, kothType, name, captureSeconds, minLocation, manLocation, startCommands, endCommands);
    }

    @Override
    public void save(Koth object, YamlConfiguration configuration, String path) {

        configuration.set("type", object.getKothType().name());
        configuration.set("name", object.getName());
        configuration.set("capture", object.getCaptureSeconds());
        configuration.set("startCommands", object.getStartCommands());
        configuration.set("endCommands", object.getEndCommands());
        locationLoader.save(object.getMinLocation(), configuration, "minLocation.");
        locationLoader.save(object.getMaxLocation(), configuration, "manLocation.");

    }
}
