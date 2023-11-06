package Labyrinth;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {
	@Test
	public void testEquals() {
		Player player = new Player(20, 20);
		Point point = new Point(20, 20);
		Assert.assertTrue(point.equals(player));
	}
}
