package Labyrinth;

public class Player extends Point {
	private int damage;
	private int last_x;
	private int last_y;
	private Direction direction;
	private Cell standsOn;
	private Direction orientation = Direction.EAST;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST,
	}

	public Player(int x, int y, int damage) {
		super(x, y);
		this.damage = damage < 0 ? 0 : damage;
		this.standsOn = Cell.FLOOR;
	}

	public Player(Point point, int damage) {
		super(point);
		this.damage = damage < 0 ? 0 : damage;
		this.standsOn = Cell.FLOOR;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean setDirection(String sDirection) {
		switch (sDirection.toLowerCase()) {
			case "n":
			case "/n":
			case "/north":
				this.direction = Direction.NORTH;
				return true;
			case "s":
			case "/s":
			case "/south":
				this.direction = Direction.SOUTH;
				return true;
			case "e":
			case "/e":
			case "/east":
				this.direction = Direction.EAST;
				this.orientation = Direction.EAST;
				return true;
			case "w":
			case "/w":
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
		this.x = this.last_x;
		this.y = this.last_y;
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
}
