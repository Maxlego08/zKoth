package fr.maxlego08.zkoth.hologram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.save.HologramConfig;

public class DecentHologram implements ZHologram {

	private final Map<Koth, Hologram> holograms = new HashMap<Koth, Hologram>();

	@Override
	public void start(Koth koth) {

		Optional<HologramConfig> optional = Config.hologramConfigs.stream()
				.filter(e -> e.getKothName().equalsIgnoreCase(koth.getName())).findFirst();
		if (!optional.isPresent())
			return;

		HologramConfig config = optional.get();
		Hologram hologram = DHAPI.createHologram("ZKOTH-" + config.getKothName(), config.getLocation());
		this.holograms.put(koth, hologram);
		updateLine(koth, hologram);
	}

	@Override
	public void end(Koth koth) {
		if (this.holograms.containsKey(koth)) {
			Hologram hologram = this.holograms.get(koth);
			hologram.destroy();
		}
		this.holograms.remove(koth);
	}

	@Override
	public void update(Koth koth) {
		if (!this.holograms.containsKey(koth))
			return;
		Hologram hologram = this.holograms.get(koth);
		updateLine(koth, hologram);
	}

	private void updateLine(Koth koth, Hologram hologram) {
		Optional<HologramConfig> optional = Config.hologramConfigs.stream()
				.filter(e -> e.getKothName().equalsIgnoreCase(koth.getName())).findFirst();		
		if (!optional.isPresent())
			return;

		HologramConfig config = optional.get();

		List<String> lines = config.getLinge();
		DHAPI.setHologramLines(hologram, lines.stream().map(e -> koth.replaceMessage(e)).collect(Collectors.toList()));
	}

	@Override
	public void onDisable() {
		this.holograms.values().forEach(Hologram::destroy);
		this.holograms.clear();
	}

}
