package Labyrinth;

import java.util.Random;

public enum Direction {
	NORTH, SOUTH, EAST, WEST;

	static private final Random rand = new Random();

	static public Direction getRandom() {
		return values()[rand.nextInt(values().length)]; 

	}
}
