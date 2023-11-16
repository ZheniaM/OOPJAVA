package Labyrinth.enemy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import Labyrinth.Cell;
import Labyrinth.Point;

public class Goo extends AbstrEnemy {
	static private final List<Cell> possibleCells;
	static private final Random rand = new Random();
	static {
		Cell[] c = {Cell.GOO1, Cell.GOO2, Cell.GOO3};
		possibleCells = Arrays.asList(c);
	}

	public Goo(Point point, int hp, int lvl) {
		super(point, hp, lvl, null, EnemyType.GOO);
		this.cell = getRandomCell();
	}

	@Override
	public void changeCell() {
		int index = possibleCells.indexOf(this.cell);
		index = (index + 1) % possibleCells.size();
		this.cell = possibleCells.get(index);
	}

	@Override
	public Cell getRandomCell() {
		int index = rand.nextInt(possibleCells.size());
		return possibleCells.get(index);
	}
}
