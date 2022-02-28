package fr.maxlego08.zkoth.zcore.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.enums.MessageType;
import fr.maxlego08.zkoth.zcore.utils.nms.NMSUtils;

public enum Message {

	PREFIX("§8(§fzKoth§8) "),
	
	INVENTORY_NULL("§cImpossible de trouver l'inventaire avec l'id §6%id%§c.", false),
	INVENTORY_CLONE_NULL("§cLe clone de l'inventaire est null !", false),
	INVENTORY_OPEN_ERROR("§cUne erreur est survenu avec l'ouverture de l'inventaire §6%id%§c.", false),
	INVENTORY_BUTTON_PREVIOUS("§f» §7Page précédente", false),
	INVENTORY_BUTTON_NEXT("§f» §7Page suivante", false),
	
	TIME_DAY("%02dd %02dh %02dm %02ds"),
	TIME_HOUR("%02dh %02dm %02ds"),
	TIME_HOUR_SIMPLE("%02d:%02d:%02d"),
	TIME_MINUTE("%02dm %02ds"),
	TIME_SECOND("%02ds"),
	
	COMMAND_SYNTAXE_ERROR("§cYou have to execute the command like this§7: §2%syntax%"),
	COMMAND_NO_PERMISSION("§cYou do not have permission to execute this command.."),
	COMMAND_NO_CONSOLE("§cOnly a player can execute this command."),
	COMMAND_NO_ARG("§cImpossible to find the command with its arguments."),
	COMMAND_SYNTAXE_HELP("§f%command% §8» §7%description%"), 
	COMMAND_HELP_HEADER("§7Commands informations§8:"),
	
	ZKOTH_AXE_RECEIVE("§7You have just received the axe for zone selection."), 
	ZKOTH_AXE_POS1("§7You have just put the first position in §f%world%§7, §f%x%§7, §f%y%§7, §f%z%§7."),
	ZKOTH_AXE_POS2("§7You have just put the second position in §f%world%§7, §f%x%§7, §f%y%§7, §f%z%§7."), 
	ZKOTH_ALREADY_EXIST("§cThe koth §f%name% §calready exists."),
	ZKOTH_DOESNT_EXIST("§cThe koth §f%name% §cdoesnt exists."),
	
	ZKOTH_CREATE_SUCCESS("§7You just created the koth §f%name%§7."), 
	ZKOTH_DELETE_SUCCESS("§7You just deleted the koth §f%name%§7."), 
	ZKOTH_MOVE_SUCCESS("§7You have just moved the koth §f%name%§7."),
	ZKOTH_CREATE_ERROR_SELECTION("§cYou must select a zone with the command §f/zkoth axe§c."), 
	
	
	ZKOTH_SPAWN_ERROR("§cImpossible to spawn the koth, positions do not work. You have to do §f/zkoth move <name>§c."), 
	ZKOTH_SPAWN_COOLDOWN("§cthe countdown to the appearance of the koth is already underway"),
	ZKOTH_SPAWN_ALREADY("§cThe koth is running."),
	
	ZKOTH_EVENT_START(MessageType.CENTER, 
			"§8§m-+------------------------------+-",
			"",
			"§fThe koth §b%name% §has just appeared!",
			"§fCoordinate§8: §7%x%, %y%, %z%.",
			"",
			"§8§m-+------------------------------+-"
			),
	
	ZKOTH_EVENT_WIN(MessageType.CENTER, 
			"§8§m-+------------------------------+-",
			"",
			"§d%player% §fof faction §7%faction% §fhas just captured",
			"§fthe koth, and §nwins§f the event!",
			"",
			"§8§m-+------------------------------+-"
			),
	
	ZKOTH_EVENT_COOLDOWN(MessageType.CENTER, 
			"§8§m-+------------------------------+-",
			"",
			"§fThe koth §n%name%§f will appear in §d%capture%",
			"§fCoordinate§8: §7%x%, %y%, %z%.",
			"",
			"§8§m-+------------------------------+-"
			),
	
	ZKOHT_EVENT_STOP(MessageType.CENTER, "§8§m-+------------------------------+-", "", "§fkoth §n%name%§f has just been stopped.", "", "§8§m-+------------------------------+-"),
	
	ZKOHT_EVENT_CATCH(MessageType.ACTION, "§d%player% §fjust started capturing the koth §n%name%§f. §8(§7%x%, %y%, %z%§8)"),
	ZKOHT_EVENT_LOOSE(MessageType.ACTION, "§d%player% §fjust loose koth §n%name%§f. §8(§7%x%, %y%, %z%§8)"),
	ZKOHT_EVENT_TIMER(MessageType.ACTION, "§fAnother §b%capture% §fbefore §d%player% §fwins the koth §n%name%§e. §8(§7%x%, %y%, %z%§8)"),
	
	ZKOHT_EVENT_FACION("No faction"),
	ZKOHT_EVENT_PLAYER("Person"), 
	ZKOTH_EVENT_DISABLE("§cThe event is not enable."),
	
	ZKOTH_SCHEDULER_CREATE("§eYou have just created a scheduler for the koth §6%s§e."),
	ZKOTH_SCHEDULER_EMPTY("§cNo scheduler available"),
	ZKOTH_SCHEDULER_REMOVE_HOVER("§7Click to delete the scheduler"),
	ZKOTH_SCHEDULER_LIST("§6%totemName%§7, §f%type%§7, §e%day%§7, §e%hour%§7, §e%minute%"),
	ZKOTH_SCHEDULER_LIST_REPEAT("§6%totemName%§7, §f%type%§7, §e%timer%"),
	ZKOTH_SCHEDULER_REMOVE_ERROR("§cYou must make /koth scheduler list and then click on one of the koths to be able to delete it"),
	ZKOTH_SCHEDULER_REMOVE_SUCCESS("§eYou have just deleted the scheduler"),	
	ZKOTH_SCHEDULER_ERROR("§cImpossible to find the day §f%day%§c."),
	
	ZKOTH_COMMAND_CREATE("§7You have just added the command §8\"§f%command%§8\""),
	ZKOTH_COMMAND_DELETE("§aYou have just deleted a command."),
	ZKOTH_LOOT_EDIT("§aYou have just set the type to §f%type%§a."),
	ZKOTH_LOOT_INVENTORY("§7Loots §b%name%"),
	ZKOTH_LOOT_CHANGE("§aYou have just modified the loots of the koth §2%name%§a."),
	ZKOTH_CAPUTRE_EDIT("§aYou have just modified the capture time of the koth §n%name%§a to §f%seconds%§a."),
	ZKOTH_LIST_CONSOLE("§fKoths§8: §f%koth%"),
	ZKOTH_AXE_NAME("§6✤ §ezKoth axe §6✤"),
	ZKOTH_AXE_DESCRIPTION("§8§m-+------------------------------+-", "",
			"",
			"§f§l» §7Allows you to select a zone to create a koth",
			" §7§oYou must select an area with the right click",
			" §7§oand left then do the command /koth create <name>",
			"",
			"§8§m-+------------------------------+-"),
	
	DESCRIPTION_SCHEDULER("Show scheduler commands"),
	DESCRIPTION_SCHEDULER_LIST("Show scheduler list"),
	DESCRIPTION_SCHEDULER_ADD("Add a scheduler"),
	DESCRIPTION_SCHEDULER_REMOVE("Remove a scheduler"),
	DESCRIPTION_VERSION("Show plugin version"),
	DESCRIPTION_ADDCOMMAND("Allows you to add a command to a koth"),
	DESCRIPTION_REMOVECOMMAND("Allows you to remove a command to a koth"),
	DESCRIPTION_AXE("Getting the selection axe"),
	DESCRIPTION_CREATE("Create new koth"),
	DESCRIPTION_DELETE("Delete a koth"),
	DESCRIPTION_INFO("Show information about a koth"),
	DESCRIPTION_LIST("Get koth list"),
	DESCRIPTION_LOOT("Change loot for a koth"),
	DESCRIPTION_MOVE("Move a koth"),
	DESCRIPTION_NOW("Spawn a koth without cooldown"),
	DESCRIPTION_STOP("Stop a koth"),
	DESCRIPTION_SPAWN("Spawn a koth with cooldown"),
	DESCRIPTION_CAPTURE("Set capture time for a koth"),
	DESCRIPTION_TYPE("Set loot type for a koth"),
	
	;

	private List<String> messages;
	private String message;
	private Map<String, Object> titles = new HashMap<>();
	private boolean use = true;
	private MessageType type = MessageType.TCHAT;

	private ItemStack itemStack;
	
	/**
	 * 
	 * @param message
	 */
	private Message(String message) {
		this.message = message;
		this.use = true;
	}

	/**
	 * 
	 * @param title
	 * @param subTitle
	 * @param a
	 * @param b
	 * @param c
	 */
	private Message(String title, String subTitle, int a, int b, int c) {
		this.use = true;
		this.titles.put("title", title);
		this.titles.put("subtitle", subTitle);
		this.titles.put("start", a);
		this.titles.put("time", b);
		this.titles.put("end", c);
		this.titles.put("isUse", true);
		this.type = MessageType.TITLE;
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
	 */
	private Message(MessageType type, String... message) {
		this.messages = Arrays.asList(message);
		this.use = true;
		this.type = type;
	}
	
	/**
	 * 
	 * @param message
	 */
	private Message(MessageType type, String message) {
		this.message = message;
		this.use = true;
		this.type = type;
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
		return this.messages == null ? Arrays.asList(this.message) : this.messages;
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
		this.type = MessageType.TITLE;
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

	public MessageType getType() {
		return type.equals(MessageType.ACTION) && NMSUtils.isVeryOldVersion() ? MessageType.TCHAT : type;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
	
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

}

