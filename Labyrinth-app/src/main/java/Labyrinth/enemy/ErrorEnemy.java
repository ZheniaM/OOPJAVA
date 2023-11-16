package Labyrinth.enemy;

import java.util.Arrays;
import java.util.List;
import Labyrinth.Cell;
import Labyrinth.Point;

public class ErrorEnemy extends AbstrEnemy {
	static private final List<Cell> possibleCells;
	static {
		Cell[] c = {Cell.ENEMY_ERROR};
		possibleCells = Arrays.asList(c);
	}

	public ErrorEnemy(Point point, int hp,int lvl) {
		super(point, hp, lvl, null, EnemyType.ENEMY_ERROR);
		this.cell = getRandomCell();
	}

	@Override
	public void changeCell() {}

	@Override
	public Cell getRandomCell() {
		return possibleCells.get(0);
	}
}
