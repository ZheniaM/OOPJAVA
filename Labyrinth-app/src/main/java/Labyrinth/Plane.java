package Labyrinth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

public class Plane {
	static private final int WALL = Cell.WALL.number;
	private final int width;
	private final int height;
	private Cell[][] cells;

	public Plane(int height, int width) {
		if (width < 0) {
			width = 1;
		}
		if (height < 0) {
			height = 1;
		}
		this.width = width;
		this.height = height;
		this.cells = new Cell[height][width];
		this.clear();
	}

	/**
	 * @see 0 = floor
	 * @see 1 = exit
	 * @see 4 = top or botom wall
	 * @see 5 = left wall
	 * @see 6 = right wall
	 * @see 7 = common wall
	 * @see 8 = botom right inner corner
	 * @see 9 = botom left inner corner
	 * @see 10 = upper right inner corner
	 * @see 11 = upper left inner corner
	 */
	public Plane(int[][] map) {
		this.height = map.length;
		this.width = map[0].length;
		this.cells = new Cell[height][width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (map[i][j] == Cell.WALL.number) {
					this.cells[i][j] = Cell.WALL;
				} else if (map[i][j] == Cell.WALL_TB.number) {
					this.cells[i][j] = Cell.WALL_TB;
				} else if (map[i][j] == Cell.WALL_LEFT.number) {
					this.cells[i][j] = Cell.WALL_LEFT;
				} else if (map[i][j] == Cell.WALL_RIGHT.number) {
					this.cells[i][j] = Cell.WALL_RIGHT;
				} else if (map[i][j] == Cell.FLOOR.number) {
					this.cells[i][j] = Cell.FLOOR;
				} else if (map[i][j] == Cell.EXIT.number) {
					this.cells[i][j] = Cell.EXIT;
				} else if (map[i][j] == Cell.WALL_BLI.number) {
					this.cells[i][j] = Cell.WALL_BLI;
				} else if (map[i][j] == Cell.WALL_BRI.number) {
					this.cells[i][j] = Cell.WALL_BRI;
				} else if (map[i][j] == Cell.WALL_UL.number) {
					this.cells[i][j] = Cell.WALL_UL;
				} else if (map[i][j] == Cell.WALL_UR.number) {
					this.cells[i][j] = Cell.WALL_UR;
				} else if (map[i][j] == Cell.WALL_RL.number) {
					this.cells[i][j] = Cell.WALL_RL;
				} else if (map[i][j] == Cell.WALL_URL.number) {
					this.cells[i][j] = Cell.WALL_URL;
				}
			}
		}
	}

	public void clear() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				cells[y][x] = Cell.FLOOR;
			}
		}
	}

	public boolean setCell(Point point, Cell type) {
		if (point.x >= this.width || point.y >= this.height) {
			return false;
		}
		this.cells[point.y][point.x] = type;
		return true;
	}

	public boolean setCell(int x, int y, Cell type) {
		if (x >= this.width || y >= this.height) {
			return false;
		}
		this.cells[y][x] = type;
		return true;
	}

	public Cell getCell(Point point) {
		return this.cells[point.y][point.x];
	}

	public Cell getCell(int x, int y) {
		return this.cells[y][x];
	}

	public String showHTML() {
		StringBuilder result = new StringBuilder();
		for (int y = 0; y < this.height; y++) {
			result.append("<a style='font-family: monospace'>");
			for (int x = 0; x < this.width; x++) {
				result.append(cells[y][x].symbol);
			}
			result.append("</a>\n");
		}
		return result.toString();
	}

	public String showStr() {
		StringBuilder result = new StringBuilder(width * (height + 1));
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				result.append(cells[y][x].symbol);
			}
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * @return ByteArrayOutputStream.
	 * @see name Name of image is "image.png"
	 */
	public ByteArrayOutputStream writeImage() {
		BufferedImage image = new BufferedImage(this.width * Cell.SIZE, this.height * Cell.SIZE,
		//		BufferedImage.TYPE_INT_RGB);
				Transparency.TRANSLUCENT);
		image.setAccelerationPriority(1);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.GRAY);
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				g2d.drawImage(cells[y][x].tile, x * Cell.SIZE, y * Cell.SIZE, Cell.SIZE, Cell.SIZE,
						Color.BLACK, null);
			}
		}
		g2d.dispose();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream;
	}
}
