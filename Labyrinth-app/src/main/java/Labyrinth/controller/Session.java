package Labyrinth.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Labyrinth.AbilityP;
import Labyrinth.App;
import Labyrinth.Battlefield;
import Labyrinth.Plane;
import Labyrinth.Player;

public class Session {
	static private HashMap<String, App> hashMap = new HashMap<String, App>();
	static private final String[] adminId;
	static {
		ClassLoader classLoader = Session.class.getClassLoader();
		File fileToken = new File(classLoader.getResource("premium_id's.txt").getFile());
		String[] premiums = null;
		try {
			premiums = FileUtils.readFileToString(fileToken, "UTF-8").split("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		adminId = premiums;

	}
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
		this.app = null;
	}

	public void makeTurn(String input) {
		/*
		 * if (chatId.equals(adminId[0]) && input.equals("kill bot")) { System.exit(0); }
		 */

		if (this.app == null) {
			if (!input.equals("/start")) {
				sendText("There is no session\nType /start to start the game", GameState.START);
				return;
			}
			this.app = createApp();
			hashMap.put(chatId, app);
			sendPhoto("Welcome!\nLevel: 0", 0, GameState.MAP);
			return;
		}

		switch (app.getGameState()) {
			case MAP:
				mapState(input);
				break;
			case BATTLE:
				battleState(input);
				break;
			case START:
				// assert (false) : "there are no START state";
				break;
			default:
				// assert (false);

		}
	}

	private void mapState(String input) {
		String output = "";
		try {
			output = app.movePlayer(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (app.getGameState() == GameState.BATTLE) {
			sendText("Battle time!", GameState.BATTLE);
			return;
		}
		if (!app.levelIsChanged()) {
			app.moveEnemies();
			if (app.getGameState() == GameState.BATTLE) {
				sendText("Battle time!", GameState.BATTLE);
				return;
			}
			sendPhoto(output, app.getCurrentLevel(), GameState.MAP);
			return;
		}
		sendPhoto("", app.getCurrentLevel() - 1, null);
		try {
			this.app.loadNewLevel(loadMap(this.app.getCurrentLevel()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!app.changeLevel()) {
				sendAnimation("congrats.gif", "You Win!\nType /start to play again",
						GameState.START);
				hashMap.put(chatId, null);
				return;
			}
		}
		sendPhoto(output, app.getCurrentLevel(), GameState.MAP);
	}

	private void battleState(String input) {
		Battlefield battlefield = this.app.getBattlefield();
		battlefield.setAbility(AbilityP.PUNCH);
		String massege = battlefield.buttle();
		sendText(massege, null);
		if (battlefield.isOver()) {
			app.setGameState(GameState.MAP);
			app.destroyEnemy(battlefield.getEnemy());
			int curLevel = app.getCurrentLevel();
			sendPhoto(String.format("Level: %d", curLevel), curLevel, GameState.MAP);
		}
	}

	private App createApp() {
		Plane map = null;
		Player player = null;
		try {
			map = loadMap(0);
			player = new Player(map.getStart());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new App(map, player);
	}

	static public Plane loadMap(int i) throws IOException, IllegalArgumentException, Exception {
		ClassLoader cl = Session.class.getClassLoader();
		String name = String.format("maps/%s", (App.mapsNames[i]));
		String json = cl.getResource(name).getFile();
		if (json == null) {
			throw new Exception("end of maps");
		}
		return new Plane(json);
	}

	private void sendText(String massege, GameState gs) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(massege);
		if (gs != null) {
			message.setReplyMarkup(gs.getMarkup());
		}
		try {
			tBot.execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendPhoto(String caption, int level, GameState gs) {
		byte[] imageData = app.getOutputStreamForImage(level).toByteArray();
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
		sendPhoto.setCaption(caption);
		if (gs != null) {
			sendPhoto.setReplyMarkup(gs.getMarkup());
		}
		try {
			tBot.execute(sendPhoto);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void sendAnimation(String fileName, String caption, GameState gs) {
		SendAnimation animation = new SendAnimation();
		animation.setChatId(chatId);
		ClassLoader classLoader = TelegramBot.class.getClassLoader();
		File fAnimation = new File(classLoader.getResource(fileName).getFile());
		animation.setAnimation(new InputFile(fAnimation));
		animation.setCaption(caption);
		if (gs != null) {
			animation.setReplyMarkup(gs.getMarkup());
		}
		try {
			tBot.execute(animation);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
