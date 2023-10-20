package fr.maxlego08.zkoth.api;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.enums.KothType;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;
import fr.maxlego08.zkoth.zcore.utils.interfaces.CollectionConsumer;

public interface Koth {

	/**
	 * 
	 * @return koth name
	 */
	public String getName();

	/**
	 * 
	 * @return {@link Location}
	 */
	public Location getMinLocation();

	/**
	 * 
	 * @return {@link Location}
	 */
	public Location getMaxLocation();

	/**
	 * 
	 * @return {@link Cuboid}
	 */
	public Cuboid getCuboid();

	/**
	 * Get center location
	 * 
	 * @return {@link Location}
	 */
	public Location getCenter();

	/**
	 * 
	 * @return
	 */
	public int getCaptureSeconds();

	/**
	 * 
	 * @return commands
	 */
	public List<String> getCommands();

	/**
	 * 
	 * @return itemstacks
	 */
	public List<ItemStack> getItemStacks();
	
	public List<ItemStack> getAllItemStacks();
	

	/**
	 * 
	 * @return {@link LootType}
	 */
	public LootType getLootType();

	/**
	 * 
	 * @param type
	 */
	public void setLootType(LootType type);

	/**
	 * Move koth
	 * 
	 * @param minLocation
	 * @param maxLocation
	 */
	public void move(Location minLocation, Location maxLocation);

	/**
	 * 
	 * @return true if is enable
	 */
	public boolean isEnable();

	/**
	 * 
	 * @return true if is in cooldown
	 */
	public boolean isCooldown();

	/**
	 * Spawn koth
	 * 
	 * @param sender
	 * @param now
	 */
	public void spawn(CommandSender sender, boolean now);

	/**
	 * 
	 * @param now
	 */
	public void spawn(boolean now);

	/**
	 * 
	 * @param player
	 * @param factionListener
	 */
	public void playerMove(Player player, FactionListener factionListener);

	/**
	 * 
	 * @return
	 */
	public CollectionConsumer<Player> onScoreboard();

	/**
	 * 
	 * @param command
	 */
	public void addCommand(String command);

	/**
	 * 
	 * @param id
	 */
	public void removeCommand(int id);

	/**
	 * 
	 * @param itemStacks
	 */
	public void setItemStacks(List<ItemStack> itemStacks);

	/**
	 * 
	 * @param sender
	 */
	public void stop(CommandSender sender);

	/**
	 * 
	 * @param second
	 */
	public void setCapture(int second);

	/**
	 * 
	 * @return
	 */
	public int getCurrentSecond();

	/**
	 * 
	 * @return
	 */
	public String getCurrentPlayer();

	/**
	 * 
	 * @return
	 */
	public String getCurrentFaction();

	/**
	 * 
	 * @return
	 */
	public KothType getType();

	/**
	 * 
	 * @return
	 */
	public int getMaxSecondsCap();

	/**
	 * 
	 * @param seconds
	 */
	public void setMaxSecondsCap(int seconds);

	/**
	 * 
	 * @return
	 */
	public int getMaxPoints();

	/**
	 * 
	 * @param points
	 */
	public void setMaxPoints(int points);

	/**
	 * 
	 * @param task
	 * @param cuboid
	 * @param player
	 */
	public void endKoth(TimerTask task, Cuboid cuboid, Player player);

	/**
	 * 
	 * @return
	 */
	public Map<UUID, Integer> getValues();

	/**
	 * 
	 * @param player
	 * @return
	 */
	public int getValue(Player player);

	/**
	 * 
	 * @param player
	 */
	public void onPlayerLeave(Player player);
	
	public void setType(KothType type);
	
	/**
	 * 
	 * @return
	 */
	public boolean hasWin();

	/**
	 * 
	 * @param position
	 * @return
	 */
	public int getPointsAt(int position);

	public int getTimerAt(int position);

	public String getPointsPercentAt(int position);
	
	public String getTimerPercentAt(int position);
	
	public String getTimerFormatAt(int position);
	
	public String getMaxTimerFormat();
	
	public String getPointsProgressBarAt(int position);
	
	public String getTimerProgressBarAt(int position);
	
	public String getClassicProgressBar();

	public String getTimerNameAt(int position);
	
	public String getPointsNameAt(int position);
	
	public int getRandomItemStacks();
	
	public void setRandomItemStacks(int value);
	
	public List<Location> getBlockLocations();
	
	public void changeBlocks(Material material);
	
	public void resetBlocks();

}
