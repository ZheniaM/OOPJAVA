package Labyrinth.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
		/*
		Scanner scanner;
		String result;
		try {
			scanner = new Scanner(new File("resources/tgid.txt"));
			result = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e) {
			result = "";
			System.out.println(e);
		}
		return result;
		*/
		return "";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String input = update.getMessage().getText();

            if (input.equals("q") || input.equals("quit")) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Вы вышли из игры.");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                app.processTelegramInput(input);
                String mapOutput = app.getMapOutput();
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(mapOutput);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}