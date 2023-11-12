package Labyrinth.enemy;

import Labyrinth.Cell;
import Labyrinth.Direction;
import Labyrinth.Point;

public abstract class AbstrEnemy extends Point implements Enemy {
	protected int hp;
	private int last_x;
	private int last_y;
	private Direction direction;
	private Cell standsOn;
	protected Cell cell;
	protected EnemyType type;

	protected AbstrEnemy(Point point, int hp, Cell cell, EnemyType type) {
		super(point);
		this.hp = hp;
		this.cell = cell;
		this.standsOn = Cell.FLOOR;
		this.type = type;
	}

	public void walkRandom() {
		this.last_x = this.x;
		this.last_y = this.y;
		this.direction = Direction.getRandom();
		switch (this.direction) {
			case NORTH:
				this.y--;
				break;
			case SOUTH:
				this.y++;
				break;
			case EAST:
				this.x++;
				break;
			case WEST:
				this.x--;
				break;
			default:
				// assert (false) : "getRandom() does not work";
		}
	}

	public void returnToPreviousPoint() {
		this.x = this.last_x;
		this.y = this.last_y;
	}

	public void setStandsOnCell(Cell cell) {
		this.standsOn = cell;
	}

	public Cell getStandsOnCell() {
		return this.standsOn;
	}

	public Point getPoint() {
		return new Point(this.x, this.y);
	}

	public Cell getCell() {
		return this.cell;
	}

	public EnemyType getType() {
		return type;
	}

	public int getHp() {
		return this.hp;
	}

	public void reduseHp(int value) {
		this.hp -= value;
	}
}
