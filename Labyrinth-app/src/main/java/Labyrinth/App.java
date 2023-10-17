package Labyrinth;

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
	private static Scanner scanner;
	private static Plane map;
	private static Character player;
	private static TelegramBot bot;



	static public void main(String[] args) throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(new TelegramBot(null));
		App app = new App();
		App.bot = new TelegramBot(app);
		initScene();
		mainLoop();
		scanner.close();
	}

	private static void mainLoop() {
		String input;
		System.out.println(App.map.show());
		while (true) {
			input = App.scanner.nextLine();
			if (input.equals("q") || input.equals("quit")) {
				break;
			}
			App.player.setDirection(input);
			moveCharacter(player);
			System.out.println(App.map.show());
		}
	}

	private static void initScene() {
		App.scanner = new Scanner(System.in);
		App.player = new Character(1, 1, 0);
		App.map = new Plane(new byte[][] {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 1, 0, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 1, 0, 0, 1}, {1, 0, 1, 0, 1, 1, 0, 0, 1},
				{1, 1, 1, 1, 1, 1, 1, 1, 1},});
		App.map.setCell(App.player, Plane.Type.ENTITY);
	}

	private static void moveCharacter(Character character) {
		App.map.setCell(character, Plane.Type.EMPTY);
		character.move();
		if (App.map.getCell(character) == Plane.Type.WALL) {
			character.returnToPreviousPoint();
		}
		App.map.setCell(character, Plane.Type.ENTITY);
	}

	public void processTelegramInput(String input) {
		player.setDirection(input);
		moveCharacter(player);
	}

	public String getMapOutput() {
		return map.show();
	}
}
