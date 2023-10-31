package Labyrinth;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {
	@Test
	public void testTrue() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPoint() {
		Point point1 = new Point(-1, 0);
		Point point2 = new Point(0, 0);
		Assert.assertTrue(point1.getX() == point2.getX() && point1.getY() == point2.getY());
	}

	@Test
	public void testPlaneHTML() {
		String expected = "<a style='font-family: monospace'>█░░</a>\n"
				+ "<a style='font-family: monospace'>░░░</a>\n"
				+ "<a style='font-family: monospace'>░█░</a>\n"
				+ "<a style='font-family: monospace'>░░P</a>\n";
		Plane plane = new Plane(4, 3, new Point(2, 3));
		plane.setCell(new Point(0, 0), Cell.WALL);
		plane.setCell(new Point(1, 2), Cell.WALL);
		plane.setCell(new Point(2, 3), Cell.PLAYERR_FLOOR);
		Assert.assertEquals(expected, plane.showHTML());
	}

	@Test
	public void testCollision() {
		String expectedMap = "█████\n█P░░█\n█░█X█\n█████\n";
		Plane map = new Plane(
				new int[][] {{7, 7, 7, 7, 7}, {7, 0, 0, 0, 7}, {7, 0, 7, 1, 7}, {7, 7, 7, 7, 7}},
				new Point(1, 1));
		Player player = new Player(1, 1, 0);
		App app = new App(map, player);
		app.movePlayer("/s");
		app.movePlayer("/e");
		app.movePlayer("/e");
		app.movePlayer("/n");
		Assert.assertTrue(player.equals(new Point(1, 1)));
		Assert.assertEquals(expectedMap, app.getMapStr());
	}

	@Test
	public void levelChangeTest() {
		int[][] map1Data = { //
				{4, 4, 4, 4, 4}, //
				{4, 0, 0, 1, 4}, //
				{4, 4, 4, 4, 4}, //
		};
		Plane map1 = new Plane(map1Data, new Point(1, 1));
		int[][] map2Data = { //
				{4, 4, 4}, //
				{4, 0, 4}, //
				{4, 0, 4}, //
				{4, 1, 4}, //
				{4, 4, 4}, //
		};
		Plane map2 = new Plane(map2Data, new Point(1, 1));
		Plane[] levels = {map1, map2};
		Player player = new Player(map1.getStart(), 0);
		App app = new App(levels, player);
		app.movePlayer("/e");
		app.movePlayer("/e");
		Assert.assertEquals(1, app.getCurrentLevel());
		app.changeLevel();
		app.movePlayer("/s");
		app.movePlayer("/s");
		Assert.assertEquals(2, app.getCurrentLevel());
		Assert.assertFalse(app.changeLevel());
	}
}
