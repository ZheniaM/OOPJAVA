package Labyrinth;
<<<<<<< HEAD
=======

import java.util.Scanner;
>>>>>>> f36678b (terminal demo v0.0.0)

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.controller.TelegramBot;

@SpringBootApplication
public class App {
<<<<<<< HEAD
	static public void main(String args[]) throws TelegramApiException{
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(new TelegramBot(null));
		Scanner scan = new Scanner(System.in);
		System.out.println("Hello World");
		System.out.println("Choose a path");
		String input = scan.next();
		scan.close();
		System.out.println(destination(input));
	}

	public static String destination(String input) {
		switch (input) {
			case ("f"):
			case ("front"):
				return("You went forward");
			case ("r"):
			case ("right"):
				return("You went right");
			case ("l"):
			case ("left"):
				return("You went left");
			case ("--help"):
			case ("-h"):
				return(
						"Write 'l' if you want to go left, 'r' if you want to go right or 'f' if you want to go straight.");
			default:
				return("There is no such direction");
		}
	}
=======
	private static Scanner scanner;
	private static Plane map;
	private static Character player;

	static public void main(String[] args) {
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
		App.map = new Plane(new byte[][]{
			{1,1,1,1,1,1,1,1,1},
			{1,0,0,1,0,0,0,0,1},
			{1,0,0,0,0,1,0,0,1},
			{1,0,1,0,1,1,0,0,1},
			{1,1,1,1,1,1,1,1,1},
		});
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
>>>>>>> f36678b (terminal demo v0.0.0)
}
