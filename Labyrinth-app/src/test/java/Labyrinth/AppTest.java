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
		Plane plane = new Plane(4, 3);
		plane.setCell(new Point(0, 0), Cell.WALL);
		plane.setCell(new Point(1, 2), Cell.WALL);
		plane.setCell(new Point(2, 3), Cell.PLAYER);
		Assert.assertEquals(expected, plane.showHTML());
	}

	@Test
	public void testCollision() {
		String expectedMap = "█████\n█P░░█\n█░█░█\n█████\n";
		Plane map = new Plane(
				new int[][] {{7, 7, 7, 7, 7}, {7, 0, 0, 0, 7}, {7, 0, 7, 0, 7}, {7, 7, 7, 7, 7}});
		Player player = new Player(1, 1, 0);
		App app = new App(map, player);
		app.movePlayerForTestCollision("s");
		app.movePlayerForTestCollision("e");
		app.movePlayerForTestCollision("e");
		app.movePlayerForTestCollision("n");
		Assert.assertTrue(player.equals(new Point(1, 1)));
		Assert.assertEquals(expectedMap, app.getMapStr());
	}
}
