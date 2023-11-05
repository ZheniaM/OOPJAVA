package Labyrinth.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername() {
		return "example_demobot";
	}

	@Override
	public String getBotToken() {
		ClassLoader classLoader = getClass().getClassLoader();
		File fileToken = new File(classLoader.getResource("tgToken.txt").getFile());
		try {
			String token = FileUtils.readFileToString(fileToken, "UTF-8");
			return token;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String chatId = update.getMessage().getChatId().toString();
			String input = update.getMessage().getText();
			if (input.equals("/Key")) {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Here is your keyboard");
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                row.add("North");
                keyboard.add(row);
                row = new KeyboardRow();
                row.add("West");
                row.add("South");
                row.add("East");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
			Session session = new Session(chatId, this);
			session.makeTurn(input);
		}
	}
}
