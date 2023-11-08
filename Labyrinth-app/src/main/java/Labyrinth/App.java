package Labyrinth;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import Labyrinth.Cell.CellType;
import Labyrinth.controller.GameState;
import Labyrinth.controller.TelegramBot;
import Labyrinth.enemy.Enemy;

public class App {
	/*
	static public final List<String> mapsNames;
	static public final int numberOfLevels;
	static {
		mapsNames = new ReaderFromResoucre("maps/").getNames();
		numberOfLevels = mapsNames.size();
	}
	*/
	private GameState gameState = GameState.MAP;
	private Enemy battleWith = null;
	private Battlefield battlefield = null;
	private Plane map;
	private Player player;
	private int playerOnLevel = 0;
	private boolean playerChangedLevel = false;
	private int enemiesDefeated;
	private int totalBattles;

	static private final String helpMessage = "help:\n" //
			+ "\ts or south to go South\n" //
			+ "\tn or north to go North\n" //
			+ "\tw or west to go West\n" //
			+ "\te or east to go East\n" //
			+ "\n" //
			+ "you can't go trouth walls"; //

	static public void main(String[] args) throws Exception {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(new TelegramBot());
	}

	public App(Plane map, Player player) {
		this.playerOnLevel = 0;
		this.player = player;
		this.map = map;
		this.enemiesDefeated = 0;
		this.totalBattles = 0;
	}

	private void movePlayer() throws Exception {
		map.setCell(this.player, this.player.getStandsOnCell());
		this.player.move();
		if (this.map.getCell(this.player).getType() == Cell.CellType.WALL) {
			this.player.returnToPreviousPoint();
		}

		this.player.setStandsOnCell(map.getCell(this.player));
		if (map.getCell(this.player) == Cell.FLOOR) {
			map.setCell(this.player, this.player.getCell(CellType.NOTHING));
		} else if (map.getCell(this.player) == Cell.EXIT) {
			map.setCell(this.player, this.player.getCell(CellType.EXIT));
			this.playerOnLevel++;
			playerChangedLevel = true;
		} else if (map.getCell(this.player).getType() == Cell.CellType.ENEMY) {
			this.player.setStandsOnCell(Cell.FLOOR);
			this.gameState = GameState.BATTLE;
			this.battleWith = map.getEnemy(this.player);
			if (this.battleWith == null) {
				throw new Exception("no enemy for battle");
			}
			this.battlefield = new Battlefield(this.player, this.battleWith);
		} else {
			map.setCell(this.player, Cell.EMPTY);
		}
	}

	public String movePlayer(String direction) throws Exception {
		if (this.player.setDirection(direction)) {
			movePlayer();
			return String.format("Level: %d", playerOnLevel);
		}
		return "[ERROR] incorrect input\n\n" + App.helpMessage;
	}

	public void moveEnemies() {
		List<Enemy> enemies = this.map.getEnemies();
		for (Enemy enemy : enemies) {
			this.map.setCell(enemy.getPoint(), enemy.getStandsOnCell());
			enemy.walkRandom();
			enemy.changeCell();
			Cell c = this.map.getCell(enemy.getPoint());
			if (enemy.getPoint().equals(this.player)) {
				this.gameState = GameState.BATTLE;
				this.battleWith = enemy;
				this.battlefield = new Battlefield(this.player, this.battleWith);
				return;
			}
			if (c.getType() == CellType.WALL || c.getType() == CellType.EXIT
					|| c.getType() == CellType.ENEMY) {
				enemy.returnToPreviousPoint();
			}
			this.map.setCell(enemy.getPoint(), enemy.getCell());
		}
		/*
		 * if (this.gameState != GameState.BATTLE) { this.battleWith = null; this.gameState =
		 * GameState.MAP; }
		 */
	}

	public void destroyEnemy(Enemy enemy, boolean winner) {
		this.map.destroyEnemy(enemy);
		this.map.setCell(this.player, this.player.getCell(CellType.NOTHING));
		this.totalBattles++;
		if (winner) {
			this.enemiesDefeated++;
		}
	}

	public int getCurrentLevel() {
		return this.playerOnLevel;
	}

	public boolean levelIsChanged() {
		return playerChangedLevel;
	}

	public boolean changeLevel() {
		/*
		if (isGameOver()) {
			return false;
		}
		*/
		if (this.playerChangedLevel) {
			this.playerChangedLevel = false;
			this.player.goTo(map.getStart());
			this.player.setStandsOnCell(Cell.FLOOR);
			map.setCell(this.player,
					(this.player.getOrientation() == Direction.EAST) ? Cell.PLAYERR_FLOOR
							: Cell.PLAYERL_FLOOR);
		}
		return true;
	}

	public boolean loadNewLevel(Plane level) {
		/*
		if (isGameOver()) {
			return false;
		}
		*/
		this.map = level;
		return true;
	}

	public String getHTML() {
		return this.map.showHTML();
	}

	public String getHelp() {
		return App.helpMessage;
	}

	public String getMapStr() {
		return this.map.showStr();
	}

	public ByteArrayOutputStream getOutputStreamForImage(int level) {
		return this.map.writeImage();
	}

	/*
	public boolean isGameOver() {
		return this.playerOnLevel == App.numberOfLevels;
	}
	*/

	public GameState getGameState() {
		return gameState;
	}

	public Battlefield getBattlefield() {
		return battlefield;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public int getEnemiesDefeated() {
		return this.enemiesDefeated;
	}

	public int getTotalBattles() {
		return totalBattles;
	}
}
