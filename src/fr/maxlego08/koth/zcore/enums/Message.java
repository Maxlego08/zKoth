package fr.maxlego08.koth.zcore.enums;

public enum Message {

	PREFIX("§8(§6zKoth§8)", true),
	
	TELEPORT_MOVE("§cVous ne devez pas bouger !", false),
	TELEPORT_MESSAGE("§7Téléportatio dans §3%s §7secondes !", false),
	TELEPORT_ERROR("§cVous avez déjà une téléportation en cours !", false),
	TELEPORT_SUCCESS("§7Téléportation effectué !", false),
	
	INVENTORY_NULL("§cImpossible de trouver l'inventaire avec l'id §6%s§c.", false),
	INVENTORY_CLONE_NULL("§cLe clone de l'inventaire est null !", false),
	INVENTORY_OPEN_ERROR("§cUne erreur est survenu avec l'ouverture de l'inventaire §6%s§c.", false),
	INVENTORY_BUTTON_PREVIOUS("§f» §7Page précédente", false),
	INVENTORY_BUTTON_NEXT("§f» §7Page suivante", false),
	
	TIME_DAY("%02d jour(s) %02d heure(s) %02d minute(s) %02d seconde(s)", true),
	TIME_HOUR("%02d heure(s) %02d minute(s) %02d seconde(s)", true),
	TIME_HOUR_SIMPLE("%02d:%02d:%02d", true),
	TIME_MINUTE("%02d minute(s) %02d seconde(s)", true),
	TIME_SECOND("%02d seconde(s)", true),
	
	COMMAND_SYNTAXE_ERROR("§cVous devez exécuter la commande comme ceci§7: §a%s", true),
	COMMAND_NO_PERMISSION("§cVous n'avez pas la permission d'exécuter cette commande.", true),
	COMMAND_NO_CONSOLE("§cSeul un joueur peut exécuter cette commande.", true),
	COMMAND_NO_ARG("§cImpossible de trouver la commande avec ses arguments.", true),
	COMMAND_SYNTAXE_HELP("§a%s §b» §7%s", true),
	
	KOTH_LIST("§eAvailable koths§8:"),
	KOTH_EMPTY("§cNo koth available."),
	KOTH_CREATE("§eYou have just created the koth §6%s§e. Now you must do §f/koth set pos1/pos2 <koth name>§e."),
	KOTH_DELETE("§eYou have just deleted the koth §6%s§e."),
	KOTH_ALREADY_EXIST("§cThe koth §6%s §calready exist."),
	KOTH_DOESNT_EXIST("§cThe koth §6%s §cdoesn't exist."),
	KOTH_SET_FIRST_POSITION("§eYou just put the first position for the koth §6%s§e."),
	KOTH_SET_SECOND_POSITION("§eYou just put the second position for the koth §6%s§e."),
	
	KOTH_LOCATION_NULL("You did not put this location"),
	KOTH_FIRST("First location"),
	KOTH_SECOND("Second location"),
	
	DESCRIPTION_SPAWN("Spawn a koth"),
	DESCRIPTION_CREATE("Create a koth"),
	DESCRIPTION_DELETE("Delete a koth"),
	DESCRIPTION_NOW("Spawn koth without cooldown"),
	DESCRIPTION_STOP("Stop a koth"),
	DESCRIPTION_RELOAD("Reload plugin"),
	DESCRIPTION_VERSION("Show plugin version"),
	DESCRIPTION_HELP("Show commands"),
	DESCRIPTION_SHOW("Show koth"),
	DESCRIPTION_SET("Set koth locations"),
	DESCRIPTION_SCHEDULER("Show scheduler commands"),
	DESCRIPTION_SCHEDULER_LIST("Show scheduler list"),
	DESCRIPTION_SCHEDULER_ADD("Add a scheduler"),
	DESCRIPTION_SCHEDULER_REMOVE("Remove a scheduler"),
	
	;

	private String message;
	private boolean use = true;

	private Message(String message) {
		this.message = message;
		this.use = true;
	}

	private Message(String message, boolean use) {
		this.message = message;
		this.use = use;
	}

	public String getMessage() {
		return message;
	}

	public String toMsg() {
		return message;
	}

	public String msg() {
		return message;
	}
	public boolean isUse() {
		return use;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}

