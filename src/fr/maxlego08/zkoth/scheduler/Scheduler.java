package fr.maxlego08.zkoth.scheduler;

import java.util.Calendar;

import fr.maxlego08.zkoth.zcore.utils.ZUtils;

public class Scheduler extends ZUtils{

	private final SchedulerType type;
	private final String day;
	private final int hour;
	private final int minutes;
	private final String totemName;
	private long lastCreate;

	/**
	 * 
	 * @param type
	 * @param day
	 * @param hour
	 * @param minutes
	 * @param totemName
	 */
	public Scheduler(SchedulerType type, String day, int hour, int minutes, String totemName) {
		super();
		this.type = type;
		this.day = day;
		this.hour = hour;
		this.minutes = minutes;
		this.totemName = totemName;
	}

	/**
	 * 
	 * @param type
	 * @param hour
	 * @param totemName
	 */
	public Scheduler(SchedulerType type, int minute, String totemName) {
		this(type, null, 0, minute, totemName);
		lastCreate = System.currentTimeMillis() + (1000 * 60 * minute);
	}

	/**
	 * @return the lastCreate
	 */
	public long getLastCreate() {
		return lastCreate;
	}

	/**
	 * @return the totemName
	 */
	public String getTotemName() {
		return totemName;
	}

	/**
	 * @return the type
	 */
	public SchedulerType getType() {
		return type;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * 
	 * @param calendar
	 * @return
	 */
	public boolean toggle(Calendar calendar) {

		switch (type) {
		case DELAY:
			return (calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.MINUTE) == minutes
					&& super.getDay().equalsIgnoreCase(day));
		case REPEAT:
			if (lastCreate != 0 && System.currentTimeMillis() > lastCreate) {
				lastCreate = System.currentTimeMillis() + (1000 * 60 * minutes);
				return true;
			}
			return false;
		default:
			return false;
		}
	}

}
