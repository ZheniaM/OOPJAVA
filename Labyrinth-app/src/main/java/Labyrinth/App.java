package Labyrinth;
import java.util.Scanner;

public class App {
	static public void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Hello World");
		System.out.println("Choose a path");
		String input = scan.next();
		scan.close();
		switch(input){
			case("f"):
			case("front"):
				System.out.println("You went forward");
				break;
			case("r"):
			case("right"):
				System.out.println("You went right");
				break;
			case("l"):
			case("left"):
				System.out.println("You went left");
				break;
			case("--help"):
			case("-h"):
				System.out.println("Write 'l' if you want to go left, 'r' if you want to go right or 'f' if you want to go straight.");
				break;
			default:
				System.out.println("There is no such direction");
				break;
		}
	}
	private boolean movePlayer(Player.Direction direction) {
		
		return false;
	}
}

class Labyrinth {
	private int width;
	private int height;
	private Type [][] cells;
	public enum Type {
		EMPTY,
		WALL,
		ENTITY,
	}
	public Labyrinth(int width, int height) {
		if (width < 0) {
			width = 1;
		}
		if (height < 0) {
			height = 1;
		}
		this.width = width;
		this.height = height;
		this.cells = new Type[height][width];
	}
	public boolean setCell(Point point, Type type) {
		if (point.x >= this.width || point.y >= this.height) {
			return false;
		} 
		this.cells[point.y][point.x] = type;
		return true;
	}
	public Type getCell(Point point) {
		return this.cells[point.y][point.x];
	}
}

class Point {
	protected int x;
	protected int y;
	public Point(int x, int y) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}

class Player extends Point {
	private int damage;
	protected int last_x;
	protected int last_y;
	private Direction direction;
	public enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST, 
	}
	public Player(int x, int y, int damage) {
		super(x, y);
		this.damage = damage < 0 ? 0 : damage;
	}
	public int getDamage() {
		return damage;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
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
	public void returnToLastPoint() {
		this.x = this.last_x;
		this.y = this.last_y;
	}
}