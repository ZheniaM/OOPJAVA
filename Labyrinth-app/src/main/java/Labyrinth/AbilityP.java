package Labyrinth;

import java.util.Random;

public enum AbilityP {
	PUNCH("/punch", 2), //
	FIRE_BALL("/fire_ball", 2), //
	HEAL("/heal", -4), //
	BLOCK("/block", 0), //
	LEAVE("/leave", 0), //
	SKIP_TURN("/skip_turn", 0); //

	private static final Random rand = new Random();
	private final String command;
	private final int damage;
	AbilityP(String command, int damage) {
		this.command = command;
		this.damage = damage;
	}
	

	public int getDamage() {
		return damage;
	}

	public String getCommand() {
		return command;
	}

	static public AbilityP getRandom() {
		AbilityP[] abils = values();
		return abils[rand.nextInt(abils.length)];
	}
}
