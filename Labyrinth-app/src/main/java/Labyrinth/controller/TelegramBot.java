package Labyrinth.controller;

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
		return "";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			long chatId = update.getMessage().getChatId();
			String input = update.getMessage().getText();
			SendMessage message = new SendMessage();
			message.enableHtml(true);
			message.setChatId(String.valueOf(chatId));
			message.setText(app.moveAndGetMapHTML(input));
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}
}