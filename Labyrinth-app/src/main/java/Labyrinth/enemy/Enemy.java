package Labyrinth;


public class Enemy extends Point {
	private int hp;
	private int last_x;
	private int last_y;
	private Direction direction;
	private Cell standsOn;
	private Cell cell;

	public Enemy(Point point, int hp, Cell cell) {
		super(point);
		this.hp = hp;
		this.cell = cell;
		this.standsOn = Cell.FLOOR;
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

	public void changeCell() {
		if (this.cell == Cell.BAT1) {
			this.cell = Cell.BAT2;
			return;
		}
		if (this.cell == Cell.BAT2) {
			this.cell = Cell.BAT3;
			return;
		}
		if (this.cell == Cell.BAT3) {
			this.cell = Cell.BAT1;
		}
	}
}
