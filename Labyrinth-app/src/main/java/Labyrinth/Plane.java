package Labyrinth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import Labyrinth.enemy.Bat;
import Labyrinth.enemy.Enemy;
import Labyrinth.enemy.EnemyType;
import Labyrinth.enemy.ErrorEnemy;
import Labyrinth.enemy.Goo;

public class Plane {
	static private final double enemyOccupancy = 0.1; // must be in range from 0 to 1
	public static int numberOfEnemiesonLVL;
	private List<Enemy> enemies;
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
			throw new IllegalArgumentException(
					"the starting position should be on the floor (it's.getId() is 0)");
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
			throw new IllegalArgumentException("Map doesn't have exit cell");
		}
		this.cells[this.start.getY()][this.start.getX()] = Cell.PLAYERR_FLOOR;
	}

	public Plane(String jsonFileName) throws IOException, IllegalArgumentException {
		File f = new File(jsonFileName);
		if (!f.exists()) {
			throw new IllegalArgumentException(
					String.format("file %s does not exist", jsonFileName));
		}
		String json = FileUtils.readFileToString(f, "UTF-8");
		JSONObject obj = new JSONObject(json);

		int x = obj.getJSONArray("start").getInt(0);
		int y = obj.getJSONArray("start").getInt(1);
		this.start = new Point(x, y);
		this.height = obj.getInt("height");
		this.width = obj.getInt("width");
		List<Point> floorCells = new ArrayList<Point>();
		// read cells
		this.cells = new Cell[height][width];
		boolean hasExit = false;
		JSONArray arr = obj.getJSONArray("cells id's");
		for (int i = 0; i < arr.length(); i++) {
			JSONArray line = arr.getJSONArray(i);
			for (int j = 0; j < line.length(); j++) {
				Cell cell = Cell.ofId(line.getInt(j));
				if (cell == Cell.FLOOR && (i != y || j != x)) {
					floorCells.add(new Point(j, i));
				}
				hasExit = hasExit || (cell.getId() == Cell.EXIT.getId());
				cells[i][j] = cell;
			}
		}
		if (!hasExit) {
			throw new IllegalArgumentException("Map doesn't have exit cell");
		}
		int numberOfEnemies = (int) (floorCells.size() * enemyOccupancy);
		Plane.numberOfEnemiesonLVL=numberOfEnemies;
		this.enemies = new ArrayList<Enemy>();
		Random rand = new Random();
		while (numberOfEnemies-- != 0) {
			int randInt = rand.nextInt(floorCells.size());
			Point p = floorCells.get(randInt);
			floorCells.remove(randInt);
			Enemy enemy = null;
			switch (EnemyType.getRandom()) {
				case BAT:
					enemy = new Bat(p, 1, numberOfEnemies);
					break;
				case GOO:
					enemy = new Goo(p, 1, numberOfEnemies);
					break;
				default:
					enemy = new ErrorEnemy(p, 1,numberOfEnemies);
					break;
			}
			this.cells[p.getY()][p.getX()] = enemy.getCell();
			this.enemies.add(enemy);
		}
		this.cells[y][x] = Cell.PLAYERR_FLOOR;
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
				g2d.drawImage(cells[y][x].getTile(), x * Cell.SIZE, y * Cell.SIZE, Cell.SIZE,
						Cell.SIZE, Color.BLACK, null);
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

	public List<Enemy> getEnemies() {
		return this.enemies;
	}

	public Enemy getEnemy(Point point) {
		for (Enemy enemy : this.enemies) {
			if (point.equals(enemy.getPoint())) {
				return enemy;
			}
		}
		return null;
	}

	public void destroyEnemy(Enemy enemy) {
		this.enemies.remove(enemy);
	}
}
