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
}
