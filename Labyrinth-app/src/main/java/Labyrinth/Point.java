package Labyrinth;

public class Point {
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

	public boolean equals(Point point) {
		return this.x == point.x && this.y == point.y;
	}

}
