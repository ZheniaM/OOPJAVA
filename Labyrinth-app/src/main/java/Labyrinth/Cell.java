package Labyrinth;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum Cell {
	/**
	 * 0 floor
	 */
	FLOOR(0b0000, "\u2591", "tiles/16px/floor.png", CellType.NOTHING),
	/**
	 * 1 exit or descent to the next level
	 */
	EXIT(0b0001, "X", "tiles/16px/exit.png", CellType.EXIT),
	/**
	 * 2 empty
	 */
	EMPTY(0b0010, "\u00A0", "tiles/16px/empty.png", CellType.NOTHING),
	/**
	 * 4 top or botom
	 */
	WALL_TB(0b0100, "\u2588", "tiles/16px/walls/wall1.png", CellType.WALL),
	/**
	 * 5 left
	 */
	WALL_LEFT(0b0101, "\u2588", "tiles/16px/walls/wall11.png", CellType.WALL),
	/**
	 * 6 right
	 */
	WALL_RIGHT(0b0110, "\u2588", "tiles/16px/walls/wall12.png", CellType.WALL),
	/**
	 * 7 regular wall
	 */
	WALL(0b0111, "\u2588", "tiles/16px/walls/wall7.png", CellType.WALL),
	/**
	 * 8 botom right inner corner
	 */
	WALL_BRI(0b1000, "\u2588", "tiles/16px/walls/wall15.png", CellType.WALL),
	/**
	 * 9 botom left inner corner
	 */
	WALL_BLI(0b1001, "\u2588", "tiles/16px/walls/wall14.png", CellType.WALL),
	/**
	 * 10 upper right corner
	 */
	WALL_UR(0b1010, "\u2588", "tiles/16px/walls/wall8.png", CellType.WALL),
	/**
	 * 11 upper left corner
	 */
	WALL_UL(0b1011, "\u2588", "tiles/16px/walls/wall9.png", CellType.WALL),
	/**
	 * 12 right and left upper corner
	 */
	WALL_URL(0b1100, "\u2588", "tiles/16px/walls/wall10.png", CellType.WALL),
	/**
	 * 16 right and left
	 */
	WALL_RL(0b10000, "\u2588", "tiles/16px/walls/wall13.png", CellType.WALL),
	/**
	 * 17 right and left_inner
	 */
	WALL_RLI(0b10001, "\u2588", "tiles/16px/walls/wall16.png", CellType.WALL),
	/**
	 * 18 right_inner and left
	 */
	WALL_RIL(0b10010, "\u2588", "tiles/16px/walls/wall17.png", CellType.WALL),
	/**
	 * 19 right_inner and left_inner
	 */
	WALL_RILI(0b10011, "\u2588", "tiles/16px/walls/wall18.png", CellType.WALL),
	/**
	 * 256 player
	 */
	PLAYERONFLOOR(0x100, "P", "tiles/16px/player_on_floor.png", CellType.PLAYER),
	/**
	 * 257 player on exit
	 */
	PLAYERONEXIT(0x101, "P", "tiles/16px/player_on_exit.png", CellType.PLAYER);

	public enum CellType {
		WALL,
		EXIT,
		NOTHING,
		PLAYER,
		ENEMY;
	}
	public final BufferedImage tile;
	public final int number;
	public final String symbol;
	public final CellType type;
	static public final int SIZE = 32;

	private Cell(int number, String symbol, String imagePath, CellType type) {
		this.number = number;
		this.symbol = symbol;
		this.type = type;
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
