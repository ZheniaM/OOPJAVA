package Labyrinth;

import java.io.ByteArrayOutputStream;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
	private Plane[] maps;
	private Player player;
	private int playerOnLevel;
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
		this.maps = new Plane[1];
		this.maps[0] = map;
	}

	public App(Plane[] maps, Player player) {
		this.playerOnLevel = 0;
		this.player = player;
		this.maps = maps;
	}

	private void movePlayer() {
		Plane map = this.maps[this.playerOnLevel];
		map.setCell(this.player, this.player.getStandsOnCell());
		this.player.move();
		if (this.maps[this.playerOnLevel].getCell(this.player).getType() == Cell.CellType.WALL) {
			this.player.returnToPreviousPoint();
		}

		this.player.setStandsOnCell(map.getCell(this.player));
		if (map.getCell(this.player) == Cell.FLOOR) {
			map.setCell(this.player,
					(this.player.getOrientation() == Player.Direction.EAST) ? Cell.PLAYERR_FLOOR
							: Cell.PLAYERL_FLOOR);
			playerChangedLevel = false;
		} else if (map.getCell(this.player) == Cell.EXIT) {
			map.setCell(this.player,
					(this.player.getOrientation() == Player.Direction.EAST) ? Cell.PLAYERR_EXIT
							: Cell.PLAYERL_EXIT);
			this.playerOnLevel++;
			playerChangedLevel = true;
		} else {
			map.setCell(this.player, Cell.EMPTY);
			playerChangedLevel = false;
		}
	}

	public String movePlayer(String direction) {
		if (this.player.setDirection(direction)) {
			movePlayer();
			return "Level: " + String.valueOf(playerOnLevel);
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
		if (this.playerOnLevel == this.maps.length) {
			return false;
		}
		if (this.playerChangedLevel) {
			Plane map = this.maps[this.playerOnLevel];
			this.player.goTo(map.getStart());
			this.player.setStandsOnCell(Cell.FLOOR);
			map.setCell(this.player,
					(this.player.getOrientation() == Player.Direction.EAST) ? Cell.PLAYERR_FLOOR
							: Cell.PLAYERL_FLOOR);
		}
		return true;

	}

	public String getHTML() {
		return this.maps[this.playerOnLevel].showHTML();
	}

	public String getHelp() {
		return App.helpMessage;
	}

	public String getMapStr() {
		return this.maps[this.playerOnLevel].showStr();
	}

	public ByteArrayOutputStream getOutputStreamForImage(int level) {
		return this.maps[level].writeImage();
	}
}
