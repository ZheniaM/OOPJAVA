package Labyrinth;

public class Plane {
	private final int width;
	private final int height;
	private Type[][] cells;

	public enum Type {
		EMPTY, WALL, ENTITY,
	}

	public Plane(int height, int width) {
		if (width < 0) {
			width = 1;
		}
		if (height < 0) {
			height = 1;
		}
		this.width = width;
		this.height = height;
		this.cells = new Type[height][width];
		this.clear();
	}

	public Plane(byte[][] map) {
		this.height = map.length;
		this.width = map[0].length;
		this.cells = new Type[height][width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.cells[i][j] = (map[i][j] == 0) ? Type.EMPTY : Type.WALL;
			}
		}
	}

	public void clear() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				cells[y][x] = Type.EMPTY;
			}
		}
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

	public Type getCell(int x, int y) {
		return this.cells[y][x];
	}

	public String show() {
		StringBuilder result = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				switch (cells[y][x]) {
					case EMPTY:
						result.append(" ");
						break;
					case WALL:
						result.append("#");
						break;
					case ENTITY:
						result.append("E");
						break;
				}
			}
			result.append("\n");
		}
		return result.toString();
	}
}
