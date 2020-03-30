package fr.maxlego08.koth.zcore.enums;

public enum Permission {

	ZKOTH_USE, ZKOTH_HELP,

	ZKOTH_CREATE, ZKOTH_DELETE,

	ZKOTH_SHOW, ZKOTH_MOVE,

	ZKOTH_RELOAD, ZKOTH_NOW,

	ZKOTH_SPAWN, ZKOTH_STOP,
	
	ZKOTH_SET,

	ZKOTH_SCHEDULER, ZKOTH_LOOT,

	;

	private final String permission;

	private Permission(String permission) {
		this.permission = permission;
	}

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
