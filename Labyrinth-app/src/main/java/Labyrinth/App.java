package Labyrinth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
	private Plane map;
	private Character player;

	static private final String helpMessage =
			"type:\n\ts or south to go South\n\tn or north to go North\n\tw or west to go West\n\te or east to go East\n\nyou can't go trouth walls (# = Wall)";

	static public void main(String[] args) throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(new TelegramBot(new App()));
	}

	public App() {
		this.player = new Character(1, 1, 0);
		this.map = new Plane(new byte[][] {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 1, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 1, 0, 0, 1}, {1, 0, 1, 0, 1, 1, 0, 0, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1},});
		this.map.setCell(this.player, Plane.Type.ENTITY);
	}

	private void movePlayer() {
		this.map.setCell(this.player, Plane.Type.EMPTY);
		this.player.move();
		if (this.map.getCell(this.player) == Plane.Type.WALL) {
			this.player.returnToPreviousPoint();
		}
		this.map.setCell(this.player, Plane.Type.ENTITY);
	}

	public String getMapHTML(String input) {
		if (input.equals("q") || input.equals("quit")) {
			return "game over";
		}
		if (this.player.setDirection(input)) {
			movePlayer();
			return map.showHTML();
		}
		return "[ERROR] incorrect input\n\n" + App.helpMessage;
	}

	public String getHelp() {
		return helpMessage;
	}
}
