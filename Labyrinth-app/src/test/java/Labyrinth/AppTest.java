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
	public void testPlane() {
		String expected = 
			"<a style='font-family: monospace'>█░░</a>\n" +
			"<a style='font-family: monospace'>░░░</a>\n" +
			"<a style='font-family: monospace'>░█░</a>\n" +
			"<a style='font-family: monospace'>░░P</a>\n";
		Plane plane = new Plane(4, 3);
		plane.setCell(new Point(0, 0), Plane.Type.WALL);
		plane.setCell(new Point(1, 2), Plane.Type.WALL);
		plane.setCell(new Point(2, 3), Plane.Type.ENTITY);
		Assert.assertEquals(expected, plane.showHTML());

	}
}
