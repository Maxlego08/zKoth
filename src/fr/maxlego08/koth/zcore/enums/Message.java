package fr.maxlego08.koth.zcore.enums;

public enum Message {

	PREFIX("§8(§6zKoth§8)", true),
	PREFIX_REAL("§8(§6zKoth§8)", false),
	
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
	
	KOTH_NOONE("Noboby"),
	KOTH_LIST("§eAvailable koths§8:"),
	KOTH_EMPTY("§cNo koth available."),
	KOTH_CREATE("§eYou have just created the koth §6%s§e. Now you must do §f/koth set pos1/pos2 <koth name>§e."),
	KOTH_DELETE("§eYou have just deleted the koth §6%s§e."),
	KOTH_ALREADY_EXIST("§cThe koth §6%s §calready exist."),
	KOTH_NO_ENABLE("§cThe koth §6%s §cis not enable."),
	KOTH_DOESNT_EXIST("§cThe koth §6%s §cdoesn't exist."),
	KOTH_SET_FIRST_POSITION("§eYou just put the first position for the koth §6%s§e."),
	KOTH_SET_SECOND_POSITION("§eYou just put the second position for the koth §6%s§e."),
	KOTH_SET_FIRST_POSITION_NULL("§cThe first location cannot be found, made §f/koth set pos1 %s§c."),
	KOTH_SET_FIRST_POSITION_WORLD_NULL("§cThe world of the first location is untraceable, impossible to spawn the koth."),
	KOTH_SET_SECOND_POSITION_NULL("§cThe second location cannot be found, made §f/koth set pos2 %s§c."),
	KOTH_SET_SECOND_POSITION_WORLD_NULL("§cThe world of the second location is untraceable, impossible to spawn the koth."),
	KOTH_SPAWN_COOLDOWN("§cthe countdown to the appearance of the koth is already underway"),
	KOTH_SPAWN_ALREADY("§cThe koth is running."),
	KOTH_SPAWN_ERROR("§cImpossible to spawn the koth, the cuboid can't be created."),
	KOTH_STOP("§ekoth §f%name% §ehas just been stopped."),
	KOTH_SPAWN_MESSAGE_COOLDOWN("§eKoth §6%name% §ewill appear in §6%cooldown% §8(§7%centerX%, %centerY%, %centerZ%§8)"), 
	KOTH_SPAWN_MESSAGE("§eKoth §6%name% §eappeared in §6%centerX%, %centerY%, %centerZ%§e."), 
	KOHT_CATCH("§6%player% §ejust started capturing the koth §6%name%§e. §8(§7%centerX%, %centerY%, %centerZ%§8)"),
	KOHT_LOOSE("§6%player% §ejust loose koth §6%name%§e. §8(§7%centerX%, %centerY%, %centerZ%§8)"),
	KOHT_TIMER("§eAnother §6%cooldown% §ebefore §6%player% §ewins the koth §6%name%§e. §8(§7%centerX%, %centerY%, %centerZ%§8)"),
	KOHT_END("§eThe §6%currentFaction% §efaction has just won the koth §6%name%§e."),
	
	KOTH_LOCATION_NULL("You did not put this location"),
	KOTH_FIRST("First location"),
	KOTH_SECOND("Second location"),
	KOTH_LOOT_EDIT("Loot save"),
	
	KOTH_SCHEDULER_CREATE("§eYou have just created a scheduler for the koth §6%s§e."),
	KOTH_SCHEDULER_EMPTY("§cNo scheduler available"),
	KOTH_SCHEDULER_REMOVE_HOVER("§7Click to delete the scheduler"),
	KOTH_SCHEDULER_LIST("§6%totemName%§7, §f%type%§7, §e%day%§7, §e%hour%§7, §e%minute%"),
	KOTH_SCHEDULER_LIST_REPEAT("§6%totemName%§7, §f%type%§7, §e%timer%"),
	KOTH_SCHEDULER_REMOVE_ERROR("§cYou must make /koth scheduler list and then click on one of the koths to be able to delete it"),
	KOTH_SCHEDULER_REMOVE_SUCCESS("§eYou have just deleted the scheduler"),
	
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
	DESCRIPTION_LOOT("Edit loot chest"),
	DESCRIPTION_SCHEDULER("Show scheduler commands"),
	DESCRIPTION_SCHEDULER_LIST("Show scheduler list"),
	DESCRIPTION_SCHEDULER_ADD("Add a scheduler"),
	DESCRIPTION_SCHEDULER_REMOVE("Remove a scheduler"), 
	KOTH_SCHEDULER_ERROR("§cImpossible to find the day §f%s§c."),
	
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

