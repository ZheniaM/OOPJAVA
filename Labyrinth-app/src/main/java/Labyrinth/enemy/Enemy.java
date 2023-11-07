package Labyrinth.enemy;

import Labyrinth.Cell;
import Labyrinth.Point;

public interface Enemy {
	public void walkRandom();

	public void returnToPreviousPoint();

	public void setStandsOnCell(Cell cell);

	public Cell getStandsOnCell();

	public Point getPoint();

	public Cell getCell();

	public EnemyType getType();

	public abstract void changeCell();

	public abstract Cell getRandomCell();
}
