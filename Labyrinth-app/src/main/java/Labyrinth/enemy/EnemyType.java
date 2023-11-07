package Labyrinth.enemy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum EnemyType {
	ENEMY_ERROR, BAT, GOO;

	static private final Random rand = new Random();

	static public EnemyType getRandom() {
		List<EnemyType> types = Arrays.asList(values());
		EnemyType t = ENEMY_ERROR;
		while (t == ENEMY_ERROR) {
			t = types.get(rand.nextInt(types.size()));
		}
		return t;
	}
}
