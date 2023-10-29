package Labyrinth.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
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
			long chatId = update.getMessage().getChatId();
			String input = update.getMessage().getText();
			String output = app.movePlayer(input);
			if (app.levelIsChanged()) {
				// create simple image
				byte[] imageData = app.getOutputStremForImage().toByteArray();
				SendPhoto sendPhoto = new SendPhoto();
				String id = update.getMessage().getChatId().toString();
				sendPhoto.setChatId(id);
				sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
				// end of image
				try {
					execute(sendPhoto);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
			if(!app.changeLevel()){
				SendMessage message = new SendMessage();
				message.setChatId(String.valueOf(chatId));
				message.setText("Game Over");
				try{
					execute(message);
				} catch(TelegramApiException e){
					e.printStackTrace();
				}
				return;
			}

			// create simple image
			byte[] imageData = app.getOutputStremForImage().toByteArray();
			SendPhoto sendPhoto = new SendPhoto();
			String id = update.getMessage().getChatId().toString();
			sendPhoto.setChatId(id);
			sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
			// end of image
			try {
				execute(sendPhoto);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}
}
