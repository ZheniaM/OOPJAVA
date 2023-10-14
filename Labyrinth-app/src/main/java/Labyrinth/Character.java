package Labyrinth;

public class Character extends Point {
	private int damage;
	private int last_x;
	private int last_y;
	private Direction direction;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST,
	}

	public Character(int x, int y, int damage) {
		super(x, y);
		this.damage = damage < 0 ? 0 : damage;
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
			case "north":
				direction = Direction.NORTH;
				return true;
			case "s":
			case "south":
				direction = Direction.SOUTH;
				return true;
			case "e":
			case "east":
				direction = Direction.EAST;
				return true;
			case "w":
			case "west":
				direction = Direction.WEST;
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
				this.y++;
				break;
			case SOUTH:
				this.y--;
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
}
