package fr.maxlego08.koth.zcore.enums;

public enum Permission {

	
	;

	private final String permission;

	private Permission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return permission;
	}
	
}
