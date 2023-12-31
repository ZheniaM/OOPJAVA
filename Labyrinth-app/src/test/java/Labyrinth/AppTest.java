package Labyrinth;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {
	@Test
	public void testTrue() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPointIsCorrectlySets() {
		Point point1 = new Point(-1, 0);
		Point point2 = new Point(0, 0);
		Assert.assertTrue(point1.getX() == point2.getX() && point1.getY() == point2.getY());
	}

	@Test
	public void testPlaneHTMLIsCorrectlyShows() {
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
	public void testCollisions() throws Exception {
		String expectedMap = "█████\n█P░░█\n█░█X█\n█████\n";
		Plane map = new Plane(
				new int[][] {{7, 7, 7, 7, 7}, {7, 0, 0, 0, 7}, {7, 0, 7, 1, 7}, {7, 7, 7, 7, 7}},
				new Point(1, 1));
		Player player = new Player(1, 1);
		App app = new App(map, player);
		app.movePlayer("/s");
		app.movePlayer("/e");
		app.movePlayer("/e");
		app.movePlayer("/n");
		Assert.assertTrue(player.equals(new Point(1, 1)));
		Assert.assertEquals(expectedMap, app.getMapStr());
	}

	@Test
	public void levelChangeTest() throws Exception {
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
		Player player = new Player(map1.getStart());
		App app = new App(map1, player);
		app.movePlayer("/east");
		app.movePlayer("/east");
		Assert.assertEquals(1, app.getCurrentLevel());
		app.loadNewLevel(map2);
		app.changeLevel();
		app.movePlayer("/south");
		app.movePlayer("/south");
		Assert.assertEquals(2, app.getCurrentLevel());
	}
}
