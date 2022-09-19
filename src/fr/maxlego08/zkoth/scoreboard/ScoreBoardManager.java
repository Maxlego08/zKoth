package fr.maxlego08.zkoth.scoreboard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

import fr.maxlego08.zkoth.api.Scoreboard;
import fr.maxlego08.zkoth.save.Config;
import fr.maxlego08.zkoth.zcore.utils.ZUtils;
import fr.maxlego08.zkoth.zcore.utils.interfaces.CollectionConsumer;
import fr.mrmicky.fastboard.FastBoard;

public class ScoreBoardManager extends ZUtils {

	private final Map<Player, FastBoard> boards = new HashMap<Player, FastBoard>();
	private boolean isRunning = false;
	private CollectionConsumer<Player> lines;
	private Scoreboard scoreboard;

	/**
	 * Start scheduler
	 */
	public void schedule() {

		if (isRunning)
			return;

		isRunning = true;

		scheduleFix(Config.schedulerMillisecond, (task, canRun) -> {

			// If the task cannot continue then we do not update the scoreboard
			if (!canRun)
				return;

			if (!isRunning) {
				task.cancel();
				return;
			}

			// if the addition of the lines is null then we stop the task
			if (lines == null) {
				task.cancel();
				return;
			}

			Iterator<FastBoard> iterator = this.boards.values().iterator();
			while (iterator.hasNext()) {
				try {
					FastBoard b = iterator.next();
					if (b.isDeleted() || !b.getPlayer().isOnline()) {
						this.boards.remove(b.getPlayer());
					}
				} catch (Exception ignored) {
				}
			}

			this.boards.forEach((player, board) -> {
				board.updateLines(this.lines.accept(player));
			});

		});
	}

	/**
	 * Create a scoreboard for a player
	 * 
	 * @param player
	 * @param title
	 * @return {@link FastBoard}
	 */
	public FastBoard createBoard(Player player, String title) {

		if (this.hasBoard(player)) {
			return this.getBoard(player);
		}

		FastBoard board = new FastBoard(player);
		board.updateTitle(title);

		if (this.lines != null) {
			board.updateLines(this.lines.accept(player));
		}

		this.boards.put(player, board);
		this.scoreboard.hide(player, create(board, player, title));

		return board;

	}

	/**
	 * 
	 * @param player
	 * @param title
	 * @return
	 */
	private Consumer<Player> create(FastBoard current, Player player, String title) {
		return p -> {

			if (current != null)
				current.delete();
			this.boards.remove(player);

			FastBoard board = new FastBoard(player);
			board.updateTitle(title);

			if (this.lines != null) {
				board.updateLines(this.lines.accept(player));
			}

			this.boards.put(player, board);
		};
	}

	/**
	 * Delete player board
	 * 
	 * @param player
	 * @return
	 */
	public boolean delete(Player player) {

		if (!this.hasBoard(player)) {
			return false;
		}

		FastBoard board = getBoard(player);
		if (!board.isDeleted()) {
			board.delete();
		}

		this.scoreboard.toggle(player, p -> {

		});
		return true;
	}

	/**
	 * Update board title
	 * 
	 * @param player
	 * @param title
	 * @return
	 */
	public boolean updateTitle(Player player, String title) {
		if (!this.hasBoard(player)) {
			return false;
		}
		FastBoard board = getBoard(player);
		board.updateTitle(title);
		return true;
	}

	public void clearBoard() {
		this.isRunning = false;
		this.boards.keySet().forEach(key -> delete(key));
		this.boards.clear();
	}

	/**
	 * Update board line
	 * 
	 * @param player
	 * @param title
	 * @return
	 */
	public boolean updateLine(Player player, int line, String string) {
		if (!this.hasBoard(player)) {
			return false;
		}
		FastBoard board = getBoard(player);
		board.updateLine(line, string);
		return true;
	}

	/**
	 * 
	 * @param player
	 * @return {@link Boolean}
	 */
	public boolean hasBoard(Player player) {
		return this.boards.containsKey(player);
	}

	/**
	 * 
	 * @param player
	 * @return {@link FastBoard}
	 */
	public FastBoard getBoard(Player player) {
		return this.boards.getOrDefault(player, null);
	}

	/**
	 * @return the boards
	 */
	public Map<Player, FastBoard> getBoards() {
		return this.boards;
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return this.isRunning;
	}

	/**
	 * @return the lines
	 */
	public CollectionConsumer<Player> getLines() {
		return this.lines;
	}

	/**
	 * @param isRunning
	 *            the isRunning to set
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * @param lines
	 *            the lines to set
	 */
	public void setLines(CollectionConsumer<Player> lines) {
		this.lines = lines;
	}

	/**
	 * @param lines
	 *            the lines to set
	 */
	public void setLinesAndSchedule(CollectionConsumer<Player> lines) {
		this.lines = lines;
		this.schedule();
	}

	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public void setDefaultScoreboard() {
		if (this.scoreboard == null) {
			this.scoreboard = new Scoreboard() {
				@Override
				public void toggle(Player player, Consumer<Player> after) {
				}

				@Override
				public void hide(Player player, Consumer<Player> after) {
				}
			};
		}
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

}