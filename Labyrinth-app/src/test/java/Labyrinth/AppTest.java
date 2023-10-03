package Labyrinth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void testTrue() {
		Assert.assertTrue(true);
	}
	@Test
	public void testPoint() {
		Point point1 = new Point(-1, 0);
		Point point2 = new Point(0,0);
		Assert.assertTrue(point1.getX() == point2.getX() && point1.getY() == point2.getY());
	}
}
