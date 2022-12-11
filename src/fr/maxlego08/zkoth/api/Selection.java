package fr.maxlego08.zkoth.api;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.Action;

public interface Selection {

	/**
	 * 
	 * @return {@link Location}
	 */
	public Location getRightLocation();

	/**
	 * 
	 * @return {@link Location}
	 */
	public Location getLeftLocation();

	/**
	 * 
	 * @param action
	 * @param location
	 */
	public void action(Action action, Location location, LivingEntity livingEntity);

	/**
	 * 
	 * @return true is selection is valid
	 */
	public boolean isValid();
	
	public boolean isCorrect();
	
	public LivingEntity getRightEntity();
	
	public LivingEntity getLeftEntity();
	
	public void clear();
	

}
