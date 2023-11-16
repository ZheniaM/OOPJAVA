package Labyrinth;

import Labyrinth.Cell.CellType;

public class Player extends Point {
	// static public final int maxHp = 100;
	private int xp = 0;
	private int hp = 14;
	private int lvl = 1;
	private int last_x;
	private int last_y;
	private Direction direction;
	private Cell standsOn;
	private Direction orientation = Direction.EAST;

	public Player(int x, int y) {
		super(x, y);
		this.standsOn = Cell.FLOOR;
	}

	public Player(Point point) {
		super(point);
		this.standsOn = Cell.FLOOR;
	}

	public int getHp() {
		return this.hp;
	}

	public void reduseHp(int value) {
		this.hp -= value;
	}

	public int getXp() {
		return this.xp;
	}

	public int getLVL() {
		return this.lvl;
	}

	public void increseXp(int value) {
		this.xp += value;
		this.lvl = xp/2 +1;
	}

	public void increseHp(int lvl) {
		this.hp = 14 + lvl;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean setDirection(String sDirection) {
		switch (sDirection) {
			case "n":
			case "/north":
				this.direction = Direction.NORTH;
				return true;
			case "s":
			case "/south":
				this.direction = Direction.SOUTH;
				return true;
			case "e":
			case "/east":
				this.direction = Direction.EAST;
				this.orientation = Direction.EAST;
				return true;
			case "w":
			case "/west":
				this.direction = Direction.WEST;
				this.orientation = Direction.WEST;
				return true;
			default:
				return false;
		}
	}

	public void move() {
		this.last_y = this.y;
		this.last_x = this.x;
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
		}
	}

	public void returnToPreviousPoint() {
		int x = this.x;
		int y = this.y;
		this.x = this.last_x;
		this.y = this.last_y;
		this.last_x = x;
		this.last_y = y;
	}

	public void setStandsOnCell(Cell cell) {
		this.standsOn = cell;
	}

	public Cell getStandsOnCell() {
		return this.standsOn;
	}

	public Direction getOrientation() {
		return this.orientation;
	}

	public void goTo(Point point) {
		this.last_x = this.x;
		this.last_y = this.y;
		this.x = point.getX();
		this.y = point.getY();
	}

	public Cell getCell(CellType cellType) {
		if (cellType == CellType.NOTHING) {
			if (this.orientation == Direction.EAST) {
				return Cell.PLAYERR_FLOOR;
			} else {
				return Cell.PLAYERL_FLOOR;
			}
		}
		if (cellType == CellType.EXIT) {
			if (this.orientation == Direction.EAST) {
				return Cell.PLAYERR_EXIT;
			} else {
				return Cell.PLAYERL_EXIT;
			}
		}
		return Cell.EMPTY;
	}
}
