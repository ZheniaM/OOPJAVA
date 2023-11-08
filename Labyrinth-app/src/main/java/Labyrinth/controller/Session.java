package Labyrinth.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Labyrinth.AbilityP;
import Labyrinth.App;
import Labyrinth.Battlefield;
import Labyrinth.NoMoreLevelException;
import Labyrinth.Plane;
import Labyrinth.Player;
import Labyrinth.ReaderFromResoucre;

public class Session {
	static private HashMap<String, App> hashMap = new HashMap<String, App>();
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
			app.changeLevel();
		} catch (NoMoreLevelException e) {
			String massege = String.format("You win!\n" //
					+ "Score:\n" //
					+ "\ttotal battles: %d\n" //
					+ "\tbattle wins: %d\n" //
					+ "\n" //
					+ "Type /start to play again", //
					app.getTotalBattles(), app.getEnemiesDefeated()); //
			sendAnimation("congrats.gif", massege, GameState.START);
			hashMap.put(chatId, null);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * finally { if (!app.changeLevel()) { String massege = String.format("You win!\n" // +
		 * "Score:\n" // + "\ttotal battles: %d\n" // + "\tbattle wins: %d\n" // + "\n" // +
		 * "Type /start to play again", // app.getTotalBattles(), app.getEnemiesDefeated()); //
		 * sendAnimation("congrats.gif", massege, GameState.START); hashMap.put(chatId, null);
		 * return; } }
		 */
		sendPhoto(output, app.getCurrentLevel(), GameState.MAP);
	}

	private void battleState(String input) {
		Battlefield battlefield = this.app.getBattlefield();
		battlefield.setAbility(AbilityP.PUNCH);
		String massege = battlefield.battle();
		sendText(massege, null);
		if (battlefield.isOver()) {
			app.setGameState(GameState.MAP);
			app.destroyEnemy(battlefield.getEnemy(), battlefield.winner());
			int curLevel = app.getCurrentLevel();
			sendPhoto(String.format("Enemies defeated: %d\nLevel: %d", app.getEnemiesDefeated(),
					curLevel), curLevel, GameState.MAP);
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

	static public Plane loadMap(int i) throws NoMoreLevelException, IllegalArgumentException {
		String name = String.format("maps/%d.json", i);
		return new Plane(name);
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
		animation.setAnimation(new ReaderFromResoucre(fileName).getAnimation());
		/*
		 * ClassLoader classLoader = TelegramBot.class.getClassLoader(); File fAnimation = new
		 * File(classLoader.getResource(fileName).getFile()); animation.setAnimation(new
		 * InputFile(fAnimation));
		 */
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
