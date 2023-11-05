package Labyrinth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
	static public final String[] mapsNames;
	static public final int numberOfLevels;
	static {
		String[] a = new File(App.class.getClassLoader().getResource("maps/").getFile()).list();
		Arrays.sort(a);
		mapsNames = a.clone();
		numberOfLevels = mapsNames.length;
	}
	private Plane map;
	private Player player;
	private int playerOnLevel = 0;
	private boolean playerChangedLevel = false;

	static private final String helpMessage = "help:\n" + "\ts or south to go South\n"
			+ "\tn or north to go North\n" + "\tw or west to go West\n" + "\te or east to go East\n"
			+ "\n" + "you can't go trouth walls";

	static public void main(String[] args) throws Exception {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(new TelegramBot());
		
	}

	public App(Plane map, Player player) {
		this.playerOnLevel = 0;
		this.player = player;
		this.map = map;
	}

	private void movePlayer() {
		map.setCell(this.player, this.player.getStandsOnCell());
		this.player.move();
		if (this.map.getCell(this.player).getType() == Cell.CellType.WALL) {
			this.player.returnToPreviousPoint();
		}

		this.player.setStandsOnCell(map.getCell(this.player));
		if (map.getCell(this.player) == Cell.FLOOR) {
			map.setCell(this.player,
					(this.player.getOrientation() == Player.Direction.EAST) ? Cell.PLAYERR_FLOOR
							: Cell.PLAYERL_FLOOR);
		} else if (map.getCell(this.player) == Cell.EXIT) {
			map.setCell(this.player,
					(this.player.getOrientation() == Player.Direction.EAST) ? Cell.PLAYERR_EXIT
							: Cell.PLAYERL_EXIT);
			this.playerOnLevel++;
			playerChangedLevel = true;
		} else {
			map.setCell(this.player, Cell.EMPTY);
		}
	}

	public String movePlayer(String direction) {
		if (this.player.setDirection(direction)) {
			movePlayer();
			return String.format("Level, %d", playerOnLevel);
		}
		return "[ERROR] incorrect input\n\n" + App.helpMessage;
	}

	public int getCurrentLevel() {
		return this.playerOnLevel;
	}

	public boolean levelIsChanged() {
		return playerChangedLevel;
	}

	public boolean changeLevel() {
		if (isGameOver()) {
			return false;
		}
		if (this.playerChangedLevel) {
			this.playerChangedLevel = false;
			this.player.goTo(map.getStart());
			this.player.setStandsOnCell(Cell.FLOOR);
			map.setCell(this.player,
					(this.player.getOrientation() == Player.Direction.EAST) ? Cell.PLAYERR_FLOOR
							: Cell.PLAYERL_FLOOR);
		}
		return true;
	}

	public boolean loadNewLevel(Plane level) {
		if (isGameOver()) {
			return false;
		}
		this.map = level;
		return true;
	}

	public String getHTML() {
		return this.map.showHTML();
	}

	public String getHelp() {
		return App.helpMessage;
	}

	public String getMapStr() {
		return this.map.showStr();
	}

	public ByteArrayOutputStream getOutputStreamForImage(int level) {
		return this.map.writeImage();
	}

	public boolean isGameOver() {
		return this.playerOnLevel == App.numberOfLevels;
	}
}
