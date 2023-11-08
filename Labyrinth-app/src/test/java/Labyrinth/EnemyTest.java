package Labyrinth;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import Labyrinth.enemy.Bat;
import Labyrinth.enemy.Enemy;
import Labyrinth.Cell.CellType;

public class EnemyTest {
	@Test
	public void testAnimation() {
		Enemy enemy = new Bat(new Point(0, 0), 1);
		Assert.assertTrue(new Point(0, 0).equals(enemy.getPoint()));
		Cell c = enemy.getCell();
		Assert.assertEquals(c, enemy.getCell());
		enemy.changeCell();
		Assert.assertNotEquals(enemy.getCell(), c);
	}

	@Test
	public void testCollisionIsWorking() throws Exception {
		Plane plane = new Plane("tests/testmap.json");
		Player player = new Player(plane.getStart());
		App app = new App(plane, player);
		for (int i = 0; i < 100; i++) {
			app.moveEnemies();
			List<Enemy> enemies = plane.getEnemies();
			for (Enemy enemy : enemies) {
				Cell c = plane.getCell(enemy.getPoint());
				switch (c) {
					case BAT1:
						Assert.assertFalse(c == Cell.BAT2);
						Assert.assertFalse(c == Cell.BAT3);
						break;
					case BAT2:
						Assert.assertFalse(c == Cell.BAT1);
						Assert.assertFalse(c == Cell.BAT3);
						break;
					case BAT3:
						Assert.assertFalse(c == Cell.BAT1);
						Assert.assertFalse(c == Cell.BAT2);
						break;
					default:
						break;
				}
				Assert.assertFalse(c == Cell.EXIT);
				Assert.assertFalse(c.getType() == CellType.WALL);
			}
		}
	}
}
