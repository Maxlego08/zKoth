package fr.maxlego08.zkoth.zcore.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {

	PREFIX("§8(§fzKoth§8)"),
	
	TELEPORT_MOVE("§cVous ne devez pas bouger !"),
	TELEPORT_MESSAGE("§7Téléportatio dans §3%s §7secondes !"),
	TELEPORT_ERROR("§cVous avez déjà une téléportation en cours !"),
	TELEPORT_SUCCESS("§7Téléportation effectué !"),
	
	INVENTORY_NULL("§cImpossible de trouver l'inventaire avec l'id §6%s§c."),
	INVENTORY_CLONE_NULL("§cLe clone de l'inventaire est null !"),
	INVENTORY_OPEN_ERROR("§cUne erreur est survenu avec l'ouverture de l'inventaire §6%s§c."),
	INVENTORY_BUTTON_PREVIOUS("§f» §7Page précédente"),
	INVENTORY_BUTTON_NEXT("§f» §7Page suivante"),
	
	TIME_DAY("%02d jour(s) %02d heure(s) %02d minute(s) %02d seconde(s)"),
	TIME_HOUR("%02d heure(s) %02d minute(s) %02d seconde(s)"),
	TIME_HOUR_SIMPLE("%02d:%02d:%02d"),
	TIME_MINUTE("%02d minute(s) %02d seconde(s)"),
	TIME_SECOND("%02d seconde(s)"),
	
	COMMAND_SYNTAXE_ERROR("§cYou have to execute the command like this§7: §2%s"),
	COMMAND_NO_PERMISSION("§cYou do not have permission to execute this command.."),
	COMMAND_NO_CONSOLE("§cOnly a player can execute this command."),
	COMMAND_NO_ARG("§cImpossible to find the command with its arguments."),
	COMMAND_SYNTAXE_HELP("§f%command% §8» §7%description%"), 
	
	
	ZKOTH_AXE_RECEIVE("§7You have just received the axe for zone selection."), 
	ZKOTH_AXE_POS1("§7You have just put the first position in §f%world%§7, §f%x%§7, §f%y%§7, §f%z%§7."),
	ZKOTH_AXE_POS2("§7You have just put the second position in §f%world%§7, §f%x%§7, §f%y%§7, §f%z%§7."), 
	ZKOTH_ALREADY_EXIST("§cThe koth §f%name% §calready exists."),
	ZKOTH_DOESNT_EXIST("§cThe koth §f%name% §cdoesnt exists."),
	
	ZKOTH_CREATE_SUCCESS("§7You just created the koth §f%name%§7."), 
	ZKOTH_DELETE_SUCCESS("§7You just deleted the koth §f%name%§7."), 
	ZKOTH_MOVE_SUCCESS("§7You have just moved the koth §f%name%§7."),
	ZKOTH_CREATE_ERROR_SELECTION("§cYou must select a zone with the command §f/zkoth axe§c."), 
	
	
	;

	private List<String> messages;
	private String message;
	private Map<String, Object> titles = new HashMap<>();
	private boolean use = true;

	/**
	 * 
	 * @param message
	 */
	private Message(String message) {
		this.message = message;
		this.use = true;
	}

	private Message(String title, String subTitle, int a, int b, int c) {
		this.use = true;
		this.titles.put("title", title);
		this.titles.put("subtitle", subTitle);
		this.titles.put("start", a);
		this.titles.put("time", b);
		this.titles.put("end", c);
		this.titles.put("isUse", true);
	}

	/**
	 * 
	 * @param message
	 */
	private Message(String... message) {
		this.messages = Arrays.asList(message);
		this.use = true;
	}

	/**
	 * 
	 * @param message
	 * @param use
	 */
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

	public List<String> getMessages() {
		return messages == null ? Arrays.asList(message) : messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public boolean isMessage() {
		return messages != null && messages.size() > 1;
	}

	public String getTitle() {
		return (String) titles.get("title");
	}

	public Map<String, Object> getTitles() {
		return titles;
	}

	public void setTitles(Map<String, Object> titles) {
		this.titles = titles;
	}

	public String getSubTitle() {
		return (String) titles.get("subtitle");
	}

	public boolean isTitle() {
		return titles.containsKey("title");
	}

	public int getStart() {
		return ((Number) titles.get("start")).intValue();
	}

	public int getEnd() {
		return ((Number) titles.get("end")).intValue();
	}

	public int getTime() {
		return ((Number) titles.get("time")).intValue();
	}

	public boolean isUseTitle() {
		return (boolean) titles.getOrDefault("isUse", "true");
	}

	public String replace(String a, String b) {
		return message.replace(a, b);
	}

}

