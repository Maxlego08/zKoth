package fr.maxlego08.koth.event;

import fr.maxlego08.koth.KothLootManager;

public class KothLootEvent extends KothEvent {

	private final KothLootManager kothLootManager;

	public KothLootEvent(KothLootManager kothLootManager) {
		super();
		this.kothLootManager = kothLootManager;
	}

	/**
	 * @return the kothLootManager
	 */
	public KothLootManager getKothLootManager() {
		return kothLootManager;
	}

}
