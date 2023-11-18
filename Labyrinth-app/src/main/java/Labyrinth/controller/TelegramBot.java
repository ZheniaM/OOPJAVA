package Labyrinth.controller;

import java.io.IOException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import Labyrinth.ReaderFromResoucre;

public class TelegramBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		return "example_demobot";
	}

	@Override
	public String getBotToken() {
		/*
		ClassLoader classLoader = getClass().getClassLoader();
		File fileToken = new File(classLoader.getResource("tgToken.txt").getFile());
		try {
			String token = FileUtils.readFileToString(fileToken, "UTF-8");
			return token;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		*/
		try {
			return new ReaderFromResoucre("tgToken.txt").getString();
		} catch (IOException e) {
			return "";
		}
	}


	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String chatId = update.getMessage().getChatId().toString();
			String input = update.getMessage().getText();
			Session session = new Session(chatId, this);
			session.makeTurn(input);
		}
	}
}
