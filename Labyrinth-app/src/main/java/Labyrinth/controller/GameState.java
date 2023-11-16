package Labyrinth.controller;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public enum GameState {
	MAP(new String[][] { //
			{"/north"}, //
			{"/west", "/east"}, //
			{"/south"} //
	}), //
	START(new String[][] { //
			{"/start"} //
	}), //
	BATTLE(new String[][] { //
			{"/punch", "/fire_ball"}, //
			{"/heal", "/block"}, //
			{"/skip_turn"} //
	}); //

	private final ReplyKeyboardMarkup markup;

	GameState(String[][] names) {
		this.markup = getKeyboardMarkup(names);
	}

	public ReplyKeyboardMarkup getMarkup() {
		return this.markup;
	}

	private ReplyKeyboardMarkup getKeyboardMarkup(String[][] keyboardArray) {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		for (String[] rowArray : keyboardArray) {
			KeyboardRow row = new KeyboardRow();
			for (String button : rowArray) {
				row.add(button);
			}
			keyboard.add(row);
		}
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

}
