package fr.maxlego08.zkoth.zcore.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.potion.PotionEffectType;

import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import fr.maxlego08.zkoth.ZKothPlugin;
import fr.maxlego08.zkoth.api.enums.DefaultFontInfo;
import fr.maxlego08.zkoth.save.ProgressBar;
import fr.maxlego08.zkoth.zcore.ZPlugin;
import fr.maxlego08.zkoth.zcore.enums.EnumInventory;
import fr.maxlego08.zkoth.zcore.enums.Permission;
import fr.maxlego08.zkoth.zcore.utils.builder.CooldownBuilder;
import fr.maxlego08.zkoth.zcore.utils.builder.TimerBuilder;
import fr.maxlego08.zkoth.zcore.utils.nms.ItemStackUtils;
import fr.maxlego08.zkoth.zcore.utils.nms.NMSUtils;
import fr.maxlego08.zkoth.zcore.utils.players.ActionBar;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public abstract class ZUtils extends MessageUtils {

	protected transient ZKothPlugin plugin = (ZKothPlugin) ZPlugin.z();

	/**
	 * @param item
	 * @return the encoded item
	 */
	protected String encode(ItemStack item) {
		return ItemStackUtils.serializeItemStack(item);
	}

	/**
	 * @param item
	 * @return the decoded item
	 */
	protected ItemStack decode(String item) {
		return ItemStackUtils.deserializeItemStack(item);
	}

	/**
	 * @param a
	 * @param b
	 * @return number between a and b
	 */
	protected int getNumberBetween(int a, int b) {
		return ThreadLocalRandom.current().nextInt(a, b);
	}

	/**
	 * @param player
	 * @return true if the player's inventory is full
	 */
	protected boolean hasInventoryFull(Player player) {
		int slot = 0;
		PlayerInventory inventory = player.getInventory();
		for (int a = 0; a != 36; a++) {
			ItemStack itemStack = inventory.getContents()[a];
			if (itemStack == null)
				slot++;
		}
		return slot == 0;
	}

	protected boolean give(ItemStack item, Player player) {
		if (hasInventoryFull(player))
			return false;
		player.getInventory().addItem(item);
		return true;
	}

	/**
	 * Gives an item to the player, if the player's inventory is full then the
	 * item will drop to the ground
	 * 
	 * @param player
	 * @param item
	 */
	protected void give(Player player, ItemStack item) {
		if (hasInventoryFull(player))
			player.getWorld().dropItem(player.getLocation(), item);
		else
			player.getInventory().addItem(item);
	}

	private static transient Material[] byId;

	static {
		if (!NMSUtils.isNewVersion()) {
			byId = new Material[0];
			for (Material material : Material.values()) {
				if (byId.length > material.getId()) {
					byId[material.getId()] = material;
				} else {
					byId = Arrays.copyOfRange(byId, 0, material.getId() + 2);
					byId[material.getId()] = material;
				}
			}
		}
	}

	/**
	 * @param id
	 * @return the material according to his id
	 */
	protected Material getMaterial(int id) {
		return byId.length > id && id >= 0 ? byId[id] : Material.AIR;
	}

	/**
	 * Check if the item name is the same as the given string
	 * 
	 * @param stack
	 * @param name
	 * @return true if the item name is the same as string
	 */
	protected boolean same(ItemStack stack, String name) {
		return stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()
				&& stack.getItemMeta().getDisplayName().equals(name);
	}

	/**
	 * Check if the item name contains the given string
	 * 
	 * @param stack
	 * @param name
	 * @return true if the item name contains the string
	 */
	protected boolean contains(ItemStack stack, String name) {
		return stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()
				&& stack.getItemMeta().getDisplayName().contains(name);
	}

	/**
	 * Remove the item from the player's hand
	 * 
	 * @param player
	 * @param number
	 *            of items to withdraw
	 */
	protected void removeItemInHand(Player player) {
		removeItemInHand(player, 64);
	}

	/**
	 * Remove the item from the player's hand
	 * 
	 * @param player
	 * @param number
	 *            of items to withdraw
	 */
	protected void removeItemInHand(Player player, int how) {
		if (player.getItemInHand().getAmount() > how)
			player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
		else
			player.setItemInHand(new ItemStack(Material.AIR));
		player.updateInventory();
	}

	/**
	 * Check if two locations are identical
	 * 
	 * @param first
	 *            location
	 * @param second
	 *            location
	 * @return true if both rentals are the same
	 */
	protected boolean same(Location l, Location l2) {
		return (l.getBlockX() == l2.getBlockX()) && (l.getBlockY() == l2.getBlockY())
				&& (l.getBlockZ() == l2.getBlockZ()) && l.getWorld().getName().equals(l2.getWorld().getName());
	}

	/**
	 * Format a double in a String
	 * 
	 * @param decimal
	 * @return formatting current duplicate
	 */
	protected String format(double decimal) {
		return format(decimal, "#.##");
	}

	/**
	 * Format a double in a String
	 * 
	 * @param decimal
	 * @param format
	 * @return formatting current double according to the given format
	 */
	protected String format(double decimal, String format) {
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(decimal);
	}

	/**
	 * 
	 * @param player
	 * @param item
	 * @param itemStack
	 */
	protected void removeItems(Player player, int item, ItemStack itemStack) {
		int slot = 0;
		for (ItemStack is : player.getInventory().getContents()) {
			if (is != null && is.isSimilar(itemStack) && item > 0) {
				int currentAmount = is.getAmount() - item;
				item -= is.getAmount();
				if (currentAmount <= 0) {
					if (slot == 40)
						player.getInventory().setItemInOffHand(null);
					else
						player.getInventory().removeItem(is);
				} else
					is.setAmount(currentAmount);
			}
			slot++;
		}
		player.updateInventory();
	}

	/**
	 * @param delay
	 * @param runnable
	 */
	protected void schedule(long delay, Runnable runnable) {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				if (runnable != null)
					runnable.run();
			}
		}, delay);
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	protected String name(String string) {
		String name = string.replace("_", " ").toLowerCase();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	protected String name(Material string) {
		String name = string.name().replace("_", " ").toLowerCase();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	protected String name(ItemStack itemStack) {
		return this.getItemName(itemStack);
	}

	/**
	 * 
	 * @param items
	 * @return
	 */
	protected int getMaxPage(Collection<?> items) {
		return (items.size() / 45) + 1;
	}

	/**
	 * 
	 * @param items
	 * @param a
	 * @return
	 */
	protected int getMaxPage(Collection<?> items, int a) {
		return (items.size() / a) + 1;
	}

	/**
	 * 
	 * @param value
	 * @param total
	 * @return
	 */
	protected double percent(double value, double total) {
		return total == 0 ? 0 : (double) ((value * 100) / total);
	}

	/**
	 * 
	 * @param total
	 * @param percent
	 * @return
	 */
	protected double percentNum(double total, double percent) {
		return (double) (total * (percent / 100));
	}

	/**
	 * 
	 * @param delay
	 * @param count
	 * @param runnable
	 */
	protected void schedule(long delay, int count, Runnable runnable) {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			int tmpCount = 0;

			@Override
			public void run() {

				if (!ZPlugin.z().isEnabled()) {
					cancel();
					return;
				}

				if (tmpCount > count) {
					cancel();
					return;
				}

				tmpCount++;
				Bukkit.getScheduler().runTask(ZPlugin.z(), runnable);

			}
		}, 0, delay);
	}

	/**
	 * 
	 * @param player
	 * @param inventoryId
	 */
	protected void createInventory(Player player, EnumInventory inventory) {
		createInventory(player, inventory, 1);
	}

	/**
	 * 
	 * @param player
	 * @param inventoryId
	 * @param page
	 */
	protected void createInventory(Player player, EnumInventory inventory, int page) {
		createInventory(player, inventory, page, new Object() {
		});
	}

	/**
	 * 
	 * @param player
	 * @param inventoryId
	 * @param page
	 * @param objects
	 */
	protected void createInventory(Player player, EnumInventory inventory, int page, Object... objects) {
		plugin.getInventoryManager().createInventory(inventory, player, page, objects);
	}

	/**
	 * 
	 * @param player
	 * @param inventory
	 * @param page
	 * @param objects
	 */
	protected void createInventory(Player player, int inventory, int page, Object... objects) {
		plugin.getInventoryManager().createInventory(inventory, player, page, objects);
	}

	/**
	 * 
	 * @param permissible
	 * @param permission
	 * @return
	 */
	protected boolean hasPermission(Permissible permissible, Permission permission) {
		return permissible.hasPermission(permission.getPermission());
	}

	/**
	 * 
	 * @param permissible
	 * @param permission
	 * @return
	 */
	protected boolean hasPermission(Permissible permissible, String permission) {
		return permissible.hasPermission(permission);
	}

	/**
	 * @param delay
	 * @param runnable
	 */
	protected void scheduleFix(long delay, BiConsumer<TimerTask, Boolean> runnable) {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!ZPlugin.z().isEnabled()) {
					cancel();
					runnable.accept(this, false);
					return;
				}
				Bukkit.getScheduler().runTask(ZPlugin.z(), () -> runnable.accept(this, true));
			}
		}, delay, delay);
	}

	/**
	 * @param delay
	 * @param runnable
	 */
	protected void scheduleFix(long start, long delay, BiConsumer<TimerTask, Boolean> runnable) {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (!ZPlugin.z().isEnabled()) {
					cancel();
					runnable.accept(this, false);
					return;
				}
				Bukkit.getScheduler().runTask(ZPlugin.z(), () -> runnable.accept(this, true));
			}
		}, start, delay);
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	protected <T> T randomElement(List<T> element) {
		if (element.size() == 0)
			return null;
		if (element.size() == 1)
			return element.get(0);
		Random random = new Random();
		return element.get(random.nextInt(element.size() - 1));
	}

	/**
	 * 
	 * @param item
	 * @return
	 */
	protected String getItemName(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName())
			return item.getItemMeta().getDisplayName();
		String name = item.serialize().get("type").toString().replace("_", " ").toLowerCase();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected String color(String message) {

		if (message == null) {
			return null;
		}

		if (NMSUtils.isHexColor()) {
			Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
			Matcher matcher = pattern.matcher(message);
			while (matcher.find()) {
				String color = message.substring(matcher.start(), matcher.end());
				message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
				matcher = pattern.matcher(message);
			}
		}

		return message.replace("&", "§");
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected String colorReverse(String message) {
		return message.replace("§", "&");
	}

	/**
	 * 
	 * @param messages
	 * @return
	 */
	protected List<String> color(List<String> messages) {
		return messages.stream().map(message -> color(message)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param messages
	 * @return
	 */
	protected List<String> colorReverse(List<String> messages) {
		return messages.stream().map(message -> colorReverse(message)).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param flagString
	 * @return
	 */
	protected ItemFlag getFlag(String flagString) {
		for (ItemFlag flag : ItemFlag.values()) {
			if (flag.name().equalsIgnoreCase(flagString))
				return flag;
		}
		return null;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	protected <T> List<T> reverse(List<T> list) {
		List<T> tmpList = new ArrayList<>();
		for (int index = list.size() - 1; index != -1; index--)
			tmpList.add(list.get(index));
		return tmpList;
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	protected String price(long price) {
		return String.format("%,d", price);
	}

	/**
	 * Permet de générer un string
	 * 
	 * @param length
	 * @return
	 */
	protected String generateRandomString(int length) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected TextComponent buildTextComponent(String message) {
		return new TextComponent(message);
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected TextComponent setHoverMessage(TextComponent component, String... messages) {
		BaseComponent[] list = new BaseComponent[messages.length];
		for (int a = 0; a != messages.length; a++)
			list[a] = new TextComponent(messages[a] + (messages.length - 1 == a ? "" : "\n"));
		component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, list));
		return component;
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected TextComponent setHoverMessage(TextComponent component, List<String> messages) {
		BaseComponent[] list = new BaseComponent[messages.size()];
		for (int a = 0; a != messages.size(); a++)
			list[a] = new TextComponent(messages.get(a) + (messages.size() - 1 == a ? "" : "\n"));
		component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, list));
		return component;
	}

	/**
	 * 
	 * @param component
	 * @param action
	 * @param command
	 * @return
	 */
	protected TextComponent setClickAction(TextComponent component, net.md_5.bungee.api.chat.ClickEvent.Action action,
			String command) {
		component.setClickEvent(new ClickEvent(action, command));
		return component;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	protected String getDisplayBalence(double value) {
		if (value < 10000)
			return format(value, "#.#");
		else if (value < 1000000)
			return String.valueOf(Integer.valueOf((int) (value / 1000))) + "k ";
		else if (value < 1000000000)
			return String.valueOf(format((value / 1000) / 1000, "#.#")) + "m ";
		else if (value < 1000000000000l)
			return String.valueOf(Integer.valueOf((int) (((value / 1000) / 1000) / 1000))) + "M ";
		else
			return "to much";
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	protected String getDisplayBalence(long value) {
		if (value < 10000)
			return format(value, "#.#");
		else if (value < 1000000)
			return String.valueOf(Integer.valueOf((int) (value / 1000))) + "k ";
		else if (value < 1000000000)
			return String.valueOf(format((value / 1000) / 1000, "#.#")) + "m ";
		else if (value < 1000000000000l)
			return String.valueOf(Integer.valueOf((int) (((value / 1000) / 1000) / 1000))) + "M ";
		else
			return "to much";
	}

	/**
	 * Permet de conter le nombre d'item
	 * 
	 * @param inventory
	 * @param material
	 * @return
	 */
	protected int count(org.bukkit.inventory.Inventory inventory, Material material) {
		int count = 0;
		for (ItemStack itemStack : inventory.getContents())
			if (itemStack != null && itemStack.getType().equals(material))
				count += itemStack.getAmount();
		return count;
	}

	protected Enchantment enchantFromString(String str) {
		for (Enchantment enchantment : Enchantment.values())
			if (enchantment.getName().equalsIgnoreCase(str))
				return enchantment;
		return null;
	}

	/**
	 * 
	 * @param direction
	 * @return
	 */
	protected BlockFace getClosestFace(float direction) {

		direction = direction % 360;

		if (direction < 0)
			direction += 360;

		direction = Math.round(direction / 45);

		switch ((int) direction) {
		case 0:
			return BlockFace.WEST;
		case 1:
			return BlockFace.NORTH_WEST;
		case 2:
			return BlockFace.NORTH;
		case 3:
			return BlockFace.NORTH_EAST;
		case 4:
			return BlockFace.EAST;
		case 5:
			return BlockFace.SOUTH_EAST;
		case 6:
			return BlockFace.SOUTH;
		case 7:
			return BlockFace.SOUTH_WEST;
		default:
			return BlockFace.WEST;
		}
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	protected String betterPrice(long price) {
		String betterPrice = "";
		String[] splitPrice = String.valueOf(price).split("");
		int current = 0;
		for (int a = splitPrice.length - 1; a > -1; a--) {
			current++;
			if (current > 3) {
				betterPrice += ".";
				current = 1;
			}
			betterPrice += splitPrice[a];
		}
		StringBuilder builder = new StringBuilder().append(betterPrice);
		builder.reverse();
		return builder.toString();
	}

	/**
	 * 
	 * @param enchantment
	 * @param itemStack
	 * @return
	 */
	protected boolean hasEnchant(Enchantment enchantment, ItemStack itemStack) {
		return itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchants()
				&& itemStack.getItemMeta().hasEnchant(enchantment);
	}

	/**
	 * 
	 * @param player
	 * @param cooldown
	 * @return
	 */
	protected String timerFormat(Player player, String cooldown) {
		return TimerBuilder.getStringTime(CooldownBuilder.getCooldownPlayer(cooldown, player) / 1000);
	}

	/**
	 * 
	 * @param player
	 * @param cooldown
	 * @return
	 */
	protected boolean isCooldown(Player player, String cooldown) {
		return isCooldown(player, cooldown, 0);
	}

	/**
	 * 
	 * @param player
	 * @param cooldown
	 * @param timer
	 * @return
	 */
	protected boolean isCooldown(Player player, String cooldown, int timer) {
		if (CooldownBuilder.isCooldown(cooldown, player)) {
			ActionBar.sendActionBar(player,
					String.format("§cVous devez attendre encore §6%s §cavant de pouvoir faire cette action.",
							timerFormat(player, cooldown)));
			return true;
		}
		if (timer > 0)
			CooldownBuilder.addCooldown(cooldown, player, timer);
		return false;
	}

	/**
	 * @param list
	 * @return
	 */
	protected String toList(Stream<String> list) {
		return toList(list.collect(Collectors.toList()), "§e", "§6");
	}

	/**
	 * @param list
	 * @return
	 */
	protected String toList(List<String> list) {
		return toList(list, "§e", "§6§n");
	}

	/**
	 * @param list
	 * @param color
	 * @param color2
	 * @return
	 */
	protected String toList(List<String> list, String color, String color2) {
		if (list == null || list.size() == 0)
			return null;
		if (list.size() == 1)
			return list.get(0);
		String str = "";
		for (int a = 0; a != list.size(); a++) {
			if (a == list.size() - 1 && a != 0)
				str += color + " et " + color2;
			else if (a != 0)
				str += color + ", " + color2;
			str += list.get(a);
		}
		return str;
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected String removeColor(String message) {
		for (ChatColor color : ChatColor.values())
			message = message.replace("§" + color.getChar(), "").replace("&" + color.getChar(), "");
		return message;
	}

	/**
	 * 
	 * @param l
	 * @return
	 */
	protected String format(long l) {
		return format(l, ' ');
	}

	/**
	 * 
	 * @param l
	 * @param c
	 * @return
	 */
	protected String format(long l, char c) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(c);
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(l);
	}

	/**
	 * 
	 * @param itemStack
	 * @param player
	 * @return itemstack
	 */
	public ItemStack playerHead(ItemStack itemStack, OfflinePlayer player) {
		String name = itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()
				? itemStack.getItemMeta().getDisplayName() : null;
		if (NMSUtils.isNewVersion()) {
			if (itemStack.getType().equals(Material.PLAYER_HEAD) && name != null && name.startsWith("HEAD")) {
				SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
				name = name.replace("HEAD", "");
				if (name.length() == 0)
					meta.setDisplayName(null);
				else
					meta.setDisplayName(name);
				meta.setOwningPlayer(player);
				itemStack.setItemMeta(meta);
			}
		} else {
			if (itemStack.getType().equals(getMaterial(397)) && itemStack.getData().getData() == 3 && name != null
					&& name.startsWith("HEAD")) {
				SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
				name = name.replace("HEAD", "");
				if (name.length() == 0)
					meta.setDisplayName(null);
				else
					meta.setDisplayName(name);
				meta.setOwner(player.getName());
				itemStack.setItemMeta(meta);
			}
		}
		return itemStack;
	}

	/**
	 * 
	 * @param itemStack
	 * @param player
	 * @return itemstack
	 */
	protected ItemStack playerHead() {
		return NMSUtils.isNewVersion() ? new ItemStack(Material.PLAYER_HEAD)
				: new ItemStack(getMaterial(397), 1, (byte) 3);
	}

	/**
	 * 
	 * @param plugin
	 * @param classz
	 * @return T
	 */
	protected <T> T getProvider(Plugin plugin, Class<T> classz) {
		RegisteredServiceProvider<T> provider = plugin.getServer().getServicesManager().getRegistration(classz);
		if (provider == null)
			return null;
		return provider.getProvider() != null ? (T) provider.getProvider() : null;
	}

	/**
	 * 
	 * @param configuration
	 * @return
	 */
	protected PotionEffectType getPotion(String configuration) {
		for (PotionEffectType effectType : PotionEffectType.values())
			if (effectType.getName().equalsIgnoreCase(configuration))
				return effectType;
		return null;
	}

	/**
	 * 
	 * @param runnable
	 */
	protected void runAsync(Runnable runnable) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, runnable);
	}

	/**
	 * 
	 * @param second
	 * @return
	 */
	protected String getStringTime(long second) {
		return TimerBuilder.getStringTime(second);
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	protected ItemStack createSkull(String url) {

		ItemStack head = playerHead();
		if (url.isEmpty())
			return head;

		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);

		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);

		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
			error.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return head;
	}

	/**
	 * 
	 * @param itemStack
	 * @return boolean
	 */
	protected boolean isPlayerHead(ItemStack itemStack) {
		Material material = itemStack.getType();
		if (NMSUtils.isNewVersion())
			return material.equals(Material.PLAYER_HEAD);
		return (material.equals(getMaterial(397))) && (itemStack.getDurability() == 3);
	}

	protected Object getPrivateField(Object object, String field)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = field.equals("commandMap") ? clazz.getDeclaredField(field)
				: field.equals("knownCommands") ? NMSUtils.isNewVersion()
						? clazz.getSuperclass().getDeclaredField(field) : clazz.getDeclaredField(field) : null;
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;
	}

	protected void unRegisterBukkitCommand(PluginCommand cmd) {
		try {
			Object result = getPrivateField(plugin.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;

			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.remove(cmd.getName());
			for (String alias : cmd.getAliases())
				knownCommands.remove(alias);
			knownCommands.remove("zshop:" + cmd.getName());
			for (String alias : cmd.getAliases())
				knownCommands.remove("zshop:" + alias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final transient static int CENTER_PX = 154;

	/**
	 * 
	 * @param message
	 * @return message
	 */
	protected String getCenteredMessage(String message) {
		if (message == null || message.equals(""))
			return "";
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : message.toCharArray()) {
			if (c == '§') {
				previousCode = true;
				continue;
			} else if (previousCode == true) {
				previousCode = false;
				if (c == 'l' || c == 'L') {
					isBold = true;
					continue;
				} else
					isBold = false;
			} else {
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while (compensated < toCompensate) {
			sb.append(" ");
			compensated += spaceLength;
		}
		return sb.toString() + message;
	}

	protected void broadcastCenterMessage(List<String> messages) {
		messages.stream().map(e -> e = getCenteredMessage(e)).forEach(e -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				messageWO(player, e);
			}
		});
	}

	protected String getDay() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		int today = calendar.get(Calendar.DAY_OF_WEEK);
		switch (today) {
		case GregorianCalendar.MONDAY:
			return "MONDAY";
		case GregorianCalendar.TUESDAY:
			return "TUESDAY";
		case GregorianCalendar.WEDNESDAY:
			return "WEDNESDAY";
		case GregorianCalendar.THURSDAY:
			return "THURSDAY";
		case GregorianCalendar.FRIDAY:
			return "FRIDAY";
		case GregorianCalendar.SATURDAY:
			return "SATURDAY";
		case GregorianCalendar.SUNDAY:
			return "SUNDAY";
		default:
			return null;
		}
	}

	protected boolean isDay(String day) {
		return day.equalsIgnoreCase("MONDAY") || day.equalsIgnoreCase("TUESDAY") || day.equalsIgnoreCase("WEDNESDAY")
				|| day.equalsIgnoreCase("THURSDAY") || day.equalsIgnoreCase("FRIDAY")
				|| day.equalsIgnoreCase("SATURDAY") || day.equalsIgnoreCase("SUNDAY");
	}

	public String getProgressBar(int current, int max, int totalBars, char symbol, String completedColor,
			String notCompletedColor) {
		try {
			float percent = (float) current / max;
			int progressBars = (int) (totalBars * percent);
			return Strings.repeat(completedColor + symbol, progressBars)
					+ Strings.repeat(notCompletedColor + symbol, totalBars - progressBars);
		} catch (Exception e) {
			return Strings.repeat(notCompletedColor + symbol, totalBars);
		}
	}

	public String getProgressBar(int current, int max, ProgressBar progressBar) {
		return this.getProgressBar(current, max, progressBar.getLenght(), progressBar.getSymbol(),
				progressBar.getCompletedColor(), progressBar.getNotCompletedColor());
	}

}
