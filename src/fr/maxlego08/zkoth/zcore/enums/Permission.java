package fr.maxlego08.zkoth.zcore.enums;

public enum Permission {
	
	ZKOTH_USE, 
	ZKOTH_AXE, 
	ZKOTH_CREATE,
	ZKOTH_MOVE,
	ZKOTH_DELETE,
	ZKOTH_SPAWN,
	ZKOTH_STOP,
	ZKOTH_NOW,
	ZKOTH_RELOAD,

	;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
