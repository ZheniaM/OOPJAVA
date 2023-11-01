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

	public void makeTurn(String input) {
		if (isGameOver) {
			sendText("Game over");
			return;
		}
		String output = app.movePlayer(input);
		if (app.levelIsChanged()) {
			sendPhoto("", app.getCurrentLevel() - 1);
			if (!app.changeLevel()) {
				isGameOver = true;
				sendAnimation("congrats.gif");
				return;
			}
		}
		sendPhoto(output, app.getCurrentLevel());
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

	private void sendText(String massege) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(massege);
		try {
			tBot.execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendPhoto(String caption, int level) {
		byte[] imageData = app.getOutputStreamForImage(level).toByteArray();
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
		sendPhoto.setCaption(caption);
		// end of image
		try {
			tBot.execute(sendPhoto);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendAnimation(String fileName) {
		SendAnimation animation = new SendAnimation();
		animation.setChatId(chatId);
		ClassLoader classLoader = TelegramBot.class.getClassLoader();
		File fAnimation = new File(classLoader.getResource(fileName).getFile());
		animation.setAnimation(new InputFile(fAnimation));
		try {
			tBot.execute(animation);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
