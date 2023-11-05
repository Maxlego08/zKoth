package fr.maxlego08.zkoth.hologram;

import fr.maxlego08.zkoth.api.Koth;

public interface ZHologram {

	void start(Koth koth);
	
	void end(Koth koth);
	
	void update(Koth koth);
	
	void onDisable();
}
