package Labyrinth.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Labyrinth.App;

@Component
public class TelegramBot extends TelegramLongPollingBot {
	private App app;

	public TelegramBot(App app) {
		this.app = app;
	}

	@Override
	public String getBotUsername() {
		return "example_demobot";
	}

	@Override
	public String getBotToken() {
		Scanner scanner;
		String result;
		try {
			scanner = new Scanner(new File("tgid.txt"));
			result = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e) {
			result = "";
			System.out.println(e);
		}
		return result;
	}

	@Override
	public void onUpdateReceived(Update update) {
		String chatId = update.getMessage().getChatId().toString();
		String text = update.getMessage().getText();
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(app.destination(text));
		try {
			this.execute(sendMessage);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}
}
