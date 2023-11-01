package Labyrinth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

public class Plane {
	private final int width;
	private final int height;
	private Cell[][] cells;
	private final Point start;

	public Plane(int height, int width, Point start) {
		this.start = start;
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
	 * @see 12 = right and left upper corner
	 * @see 16 = right and left
	 * @see 17 = right and left_inner
	 * @see 18 = right_inner and left
	 * @see 19 = right_inner and left_inner
	 */
	public Plane(int[][] map, Point start) throws IllegalArgumentException {
		if (map[start.getY()][start.getX()] != 0) {
			throw new IllegalArgumentException("the starting position should be on the floor (it's.getId() is 0)");
		}
		this.start = start;
		this.height = map.length;
		this.width = map[0].length;
		this.cells = new Cell[height][width];
		boolean hasExit = false;
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				Cell cell = Cell.ofId(map[i][j]);
				hasExit = hasExit || (cell.getId() == Cell.EXIT.getId());
				cells[i][j] = cell;
			}
		}
		if (!hasExit) {
			throw new IllegalArgumentException("Map doesn't have exit.getTile()");
		}
		this.cells[this.start.getY()][this.start.getX()] = Cell.PLAYERR_FLOOR;
	}

	public Point getStart() {
		return start;
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
				result.append(cells[y][x].getSymbol());
			}
			result.append("</a>\n");
		}
		return result.toString();
	}

	public String showStr() {
		StringBuilder result = new StringBuilder(width * (height + 1));
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				result.append(cells[y][x].getSymbol());
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
				// BufferedImage.TYPE_INT_RGB);
				Transparency.TRANSLUCENT);
		image.setAccelerationPriority(1);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.GRAY);
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				g2d.drawImage(cells[y][x].getTile(), x * Cell.SIZE, y * Cell.SIZE, Cell.SIZE, Cell.SIZE,
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
