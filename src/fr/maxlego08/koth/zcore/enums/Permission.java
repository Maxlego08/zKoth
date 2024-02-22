package fr.maxlego08.koth.zcore.enums;

public enum Permission {

	ZKOTH_USE,
	ZKOTH_RELOAD,
	ZKOTH_AXE,

	ZKOTH_CREATE;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
