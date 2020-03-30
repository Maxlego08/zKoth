package fr.maxlego08.koth.zcore.enums;

public enum Inventory {

	INVENTORY_TEST(1),
	
	;
	
	private final int id;

	private Inventory(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
