package Labyrinth;

import java.io.ByteArrayOutputStream;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
	private Plane[] maps;
	private Player player;
	private int playerOnLevel;
	private boolean playerChangedLevel = false;

	static private final String helpMessage =
			"type:\n\ts or south to go South\n\tn or north to go North\n\tw or west to go West\n\te or east to go East\n\nyou can't go trouth walls (# = Wall)";

	static public void main(String[] args) throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		int[][] localMapData1 = { //
				{5, 4, 4, 16, 4, 4, 4, 4, 6}, //
				{5, 0, 0, 4, 0, 0, 0, 0, 6}, //
				{5, 0, 0, 0, 0, 12, 0, 0, 6}, //
				{5, 0, 12, 0, 11, 17, 0, 1, 6}, //
				{9, 4, 19, 4, 8, 9, 4, 4, 8}}; //

		int[][] localMapData2 = { //
				{5, 4, 4, 4, 6}, //
				{5, 0, 0, 1, 6}, //
				{9, 4, 4, 4, 8}}; //

		Plane localMap1 = new Plane(localMapData1);
		Plane localMap2 = new Plane(localMapData2);
		Plane[] maps = {localMap2, localMap1};
		Player localPlayer = new Player(1, 1, 0);
		telegramBotsApi.registerBot(new TelegramBot(new App(maps, localPlayer)));
	}

	public App(Plane map, Player player) {
		this.playerOnLevel = 0;
		this.player = player;
		this.maps = new Plane[1];
		this.maps[0] = map;
		this.player.setStandsOnCell(this.maps[0].getCell(this.player));
	}

	public App(Plane[] maps, Player player) {
		this.playerOnLevel = 0;
		this.player = player;
		this.maps = maps;
		this.player.setStandsOnCell(this.maps[0].getCell(this.player));
	}

	private void movePlayer() {
		this.maps[this.playerOnLevel].setCell(this.player, this.player.getStandsOnCell());
		this.player.move();
		if (this.maps[this.playerOnLevel].getCell(this.player).type == Cell.CellType.WALL) {
			this.player.returnToPreviousPoint();
		}
		this.player.setStandsOnCell(this.maps[this.playerOnLevel].getCell(this.player));
		if (this.maps[this.playerOnLevel].getCell(this.player) == Cell.FLOOR) {
			this.maps[this.playerOnLevel].setCell(this.player, Cell.PLAYERONFLOOR);
			playerChangedLevel = false;
		} else if (this.maps[this.playerOnLevel].getCell(this.player) == Cell.EXIT) {
			this.maps[this.playerOnLevel].setCell(this.player, Cell.PLAYERONEXIT);
			this.playerOnLevel++;
			playerChangedLevel = true;
		} else {
			this.maps[this.playerOnLevel].setCell(this.player, Cell.EMPTY);
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
			this.player = new Player(1, 1, 0);
			this.player.setStandsOnCell(this.maps[this.playerOnLevel].getCell(this.player));
			this.maps[this.playerOnLevel].setCell(player, Cell.PLAYERONFLOOR);
			//this.maps[this.playerOnLevel].setPlayer(this.player);
		}
		return true;

	}

	public String moveAndGetMapHTML(String direction) {
		if (direction.equals("q") || direction.equals("quit")) {
			return "game over";
		}
		if (this.player.setDirection(direction)) {
			movePlayer();
			return maps[this.playerOnLevel].showHTML();
		}
		return "[ERROR] incorrect input\n\n" + App.helpMessage;
	}

	public String getHelp() {
		return helpMessage;
	}

	public String getMapStr() {
		return this.maps[this.playerOnLevel].showStr();
	}

	public ByteArrayOutputStream getOutputStremForImage(int level) {
		return this.maps[level].writeImage();
	}

	public void movePlayerForTestCollision(String direction) {
		this.player.setDirection(direction);
		movePlayer();
	}
}
