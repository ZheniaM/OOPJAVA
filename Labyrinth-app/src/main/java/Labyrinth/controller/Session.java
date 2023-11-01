package Labyrinth.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Labyrinth.App;
import Labyrinth.Maps;
import Labyrinth.Plane;
import Labyrinth.Player;

public class Session {
	static private HashMap<String, App> hashMap = new HashMap<String, App>();
	private boolean isGameOver = false;
	private TelegramBot tBot;
	private String chatId;
	private App app;

	Session(String id, TelegramBot telegramBot) {
		if (hashMap.containsKey(id)) {
			this.app = hashMap.get(id);
			this.tBot = telegramBot;
			this.chatId = id;
			return;
		}
		
		this.tBot = telegramBot;
		this.chatId = id;
		this.app = createLevels();
		hashMap.put(id, this.app);
	}

	private App createLevels() {
		Plane[] maps = new Plane[Maps.length];
		for (int i = 0; i < Maps.length; i++) {
			Plane map = new Plane(Maps.maps[i], Maps.starts[i]);
			maps[i] = map;
		}
		Player p = new Player(maps[0].getStart(), 0);
		return new App(maps, p);
	}
	
	public void makeTurn(String input) {
		if (isGameOver) {
				SendMessage message = new SendMessage();
				message.setChatId(chatId);
				message.setText("Game Over");
				try {
					tBot.execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				return;
			}
			String output = app.movePlayer(input);
			if (app.levelIsChanged()) {
				// create simple image
				byte[] imageData = app.getOutputStreamForImage(app.getCurrentLevel() - 1).toByteArray();
				SendPhoto sendPhoto = new SendPhoto();
				sendPhoto.setChatId(chatId);
				sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
				// end of image
				try {
					tBot.execute(sendPhoto);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

				if (!app.changeLevel()) {
					isGameOver = true;
					SendAnimation congrats = new SendAnimation();
					congrats.setChatId(chatId);
					ClassLoader classLoader = TelegramBot.class.getClassLoader();
					File fCongrats = new File(classLoader.getResource("congrats.gif").getFile());
					congrats.setAnimation(new InputFile(fCongrats));
					try {
						tBot.execute(congrats);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
					return;
				}
			}

			// create simple image
			byte[] imageData = app.getOutputStreamForImage(app.getCurrentLevel()).toByteArray();
			SendPhoto sendPhoto = new SendPhoto();
			sendPhoto.setChatId(chatId);
			sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
			sendPhoto.setCaption(output);
			// end of image
			try {
				tBot.execute(sendPhoto);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
	}
}
