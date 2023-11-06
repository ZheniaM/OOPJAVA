package Labyrinth;

import java.util.Random;

public enum AbilityE {
	PUNCH(1), //
	FIRE_BALL(1), //
	HEAL(-2), //
	BLOCK(0), //
	SKIP_TURN(0); //

	private static final Random rand = new Random();
	private final int damage;
	AbilityE(int damage) {
		this.damage = damage;
	}
	

	public int getDamage() {
		return damage;
	}

	static public AbilityE getRandom() {
		AbilityE[] abils = values();
		return abils[rand.nextInt(abils.length)];
	}
}
