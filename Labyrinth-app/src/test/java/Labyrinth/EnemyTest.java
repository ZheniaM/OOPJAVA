package Labyrinth;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import Labyrinth.Cell.CellType;

public class EnemyTest {
	@Test
	public void testBasicBehavior() {
		Enemy enemy = new Enemy(new Point(0, 0), 1, Cell.BAT1);
		Assert.assertTrue(new Point(0, 0).equals(enemy));
		Assert.assertEquals(Cell.BAT1, enemy.getCell());
		enemy.changeCell();
		Assert.assertEquals(Cell.BAT2, enemy.getCell());
		enemy.changeCell();
		Assert.assertEquals(Cell.BAT3, enemy.getCell());
		enemy.changeCell();
		Assert.assertEquals(Cell.BAT1, enemy.getCell());
	}

	@Test
	public void testCollisionIsWorking() throws Exception {
		ClassLoader cl = EnemyTest.class.getClassLoader();
		//URL url = cl.getResource("");
		Plane plane = new Plane(cl.getResource("tests/testmap.json").getFile());
		Player player = new Player(plane.getStart());
		App app = new App(plane, player);
		for (int i = 0; i < 100; i++) {
			app.moveEnemies();
			List<Enemy> enemies = plane.getEnemies();
			for (Enemy enemy : enemies) {
				Cell c = plane.getCell(enemy);
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
				}
				Assert.assertFalse(c == Cell.EXIT);
				Assert.assertFalse(c.getType() == CellType.WALL);
			}
		}
	}
}
