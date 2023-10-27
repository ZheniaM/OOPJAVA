package Labyrinth;

import java.io.ByteArrayOutputStream;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
	private Plane map;
	private Player player;

	static private final String helpMessage =
			"type:\n\ts or south to go South\n\tn or north to go North\n\tw or west to go West\n\te or east to go East\n\nyou can't go trouth walls (# = Wall)";

	static public void main(String[] args) throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		Plane localMap = new Plane(new int[][] { //
				{5, 4, 4, 16, 4, 4, 4, 4, 6}, //
				{5, 0, 0, 4, 0, 0, 0, 0, 6}, //
				{5, 0, 0, 0, 0, 12, 0, 0, 6}, //
				{5, 0, 12, 0, 11, 17, 0, 1, 6}, //
				{9, 4, 19, 4, 8, 9, 4, 4, 8},}); //
		Player localPlayer = new Player(1, 1, 0);
		telegramBotsApi.registerBot(new TelegramBot(new App(localMap, localPlayer)));
	}

	public App(Plane map, Player player) {
		this.player = player;
		this.map = map;
		this.map.setPlayer(this.player);
		this.player.setStandsOnCell(this.map.getCell(this.player));
	}

	private void movePlayer() {
		this.map.setCell(this.player, this.player.getStandsOnCell());
		this.player.move();
		if (this.map.getCell(this.player).type == Cell.CellType.WALL) {
			this.player.returnToPreviousPoint();
		}
		this.player.setStandsOnCell(this.map.getCell(this.player));
		if (this.map.getCell(this.player) == Cell.FLOOR) {
			this.map.setCell(this.player, Cell.PLAYERONFLOOR);
		} else if (this.map.getCell(this.player) == Cell.EXIT) {
			this.map.setCell(this.player, Cell.PLAYERONEXIT);
		} else {
			this.map.setCell(this.player, Cell.EMPTY);
		}
	}

	public String moveAndGetMapHTML(String direction) {
		if (direction.equals("q") || direction.equals("quit")) {
			return "game over";
		}
		if (this.player.setDirection(direction)) {
			movePlayer();
			return map.showHTML();
		}
		return "[ERROR] incorrect input\n\n" + App.helpMessage;
	}

	public String getHelp() {
		return helpMessage;
	}

	public String getMapStr() {
		return this.map.showStr();
	}

	public ByteArrayOutputStream getOutputStremForImage() {
		return this.map.writeImage();
	}

	public void movePlayerForTestCollision(String direction) {
		this.player.setDirection(direction);
		movePlayer();
	}
}
