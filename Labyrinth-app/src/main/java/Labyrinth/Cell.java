package Labyrinth;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum Cell {
	/**
	 * 0 floor
	 */
	FLOOR(0b0000, "\u2591", "tiles/32px/walls/wall16.png", false),
	/**
	 * 1 exit or descent to the next level
	 */
	EXIT(0b0001, "X", "tiles/32px/exit.png", false),
	/**
	 * 2 empty
	 */
	EMPTY(0b0010, " ", "tiles/32px/empty.png", false),
	/**
	 * 4 top or botom
	 */
	WALL_TB(0b0100, "\u2588", "tiles/32px/walls/wall1.png", true),
	/**
	 * 5 left
	 */
	WALL_LEFT(0b0101, "\u2588", "tiles/32px/walls/wall11.png", true),
	/**
	 * 6 right
	 */
	WALL_RIGHT(0b0110, "\u2588", "tiles/32px/walls/wall12.png", true),
	/**
	 * 7 regular wall
	 */
	WALL(0b0111, "\u2588", "tiles/32px/walls/wall7.png", true),
	/**
	 * 8 botom right inner corner
	 */
	WALL_BRI(0b1000, "\u2588", "tiles/32px/walls/wall15.png", true),
	/**
	 * 9 botom left inner corner
	 */
	WALL_BLI(0b1001, "\u2588", "tiles/32px/walls/wall14.png", true),
	/**
	 * 10 upper right corner
	 */
	WALL_UR(0b1010, "\u2588", "tiles/32px/walls/wall8.png", true),
	/**
	 * 11 upper left corner
	 */
	WALL_UL(0b1011, "\u2588", "tiles/32px/walls/wall9.png", true),
	/**
	 * 12 right and left at the same time
	 */
	WALL_RL(0b1100, "\u2588", "tiles/32px/walls/wall13.png", true),
	/**
	 * 13 right and left upper corner
	 */
	WALL_URL(0b1101, "\u2588", "tiles/32px/walls/wall10.png", true),
	/**
	 * 16 player
	 */
	PLAYER(0x10, "P", "tiles/32px/player.png", true);

	public final BufferedImage tile;
	public final int number;
	public final String symbol;
	public final boolean hasCollision;
	static public final int SIZE = 32;

	private Cell(int number, String symbol, String imagePath, boolean hasCollision) {
		this.number = number;
		this.symbol = symbol;
		this.hasCollision = hasCollision;
		ClassLoader classLoader = Cell.class.getClassLoader();
		BufferedImage tile = null;
		try {
			File file = new File(classLoader.getResource(imagePath).getFile());
			tile = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tile = tile;
	}
}
