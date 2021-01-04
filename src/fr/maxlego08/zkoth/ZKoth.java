package fr.maxlego08.zkoth;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import fr.maxlego08.zkoth.api.Koth;
import fr.maxlego08.zkoth.api.enums.LootType;
import fr.maxlego08.zkoth.zcore.utils.Cuboid;

public class ZKoth implements Koth {

	private final String name;
	private int captureSeconds;
	private Location minLocation;
	private Location maxLocation;
	private LootType type;
	private List<String> commands = new ArrayList<String>();
	private List<ItemStack> itemStacks = new ArrayList<ItemStack>();

	/**
	 * @param name
	 * @param captureSeconds
	 * @param minLocation
	 * @param maxLocation
	 */
	public ZKoth(String name, int captureSeconds, Location minLocation, Location maxLocation) {
		super();
		this.name = name;
		this.captureSeconds = captureSeconds;
		this.minLocation = minLocation;
		this.maxLocation = maxLocation;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getMinLocation() {
		return this.minLocation;
	}

	@Override
	public Location getMaxLocation() {
		return this.maxLocation;
	}

	@Override
	public Cuboid getCuboid() {
		return new Cuboid(this.maxLocation, this.minLocation);
	}

	@Override
	public Location getCenter() {
		Cuboid cuboid = getCuboid();
		return cuboid.getCenter();
	}

	@Override
	public int getCaptureSeconds() {
		return this.captureSeconds;
	}

	@Override
	public List<String> getCommands() {
		return this.commands;
	}

	@Override
	public List<ItemStack> getItemStacks() {
		return this.itemStacks;
	}

	@Override
	public LootType getLootType() {
		return this.type;
	}

	@Override
	public void setLootType(LootType type) {
		this.type = type;
	}

}
