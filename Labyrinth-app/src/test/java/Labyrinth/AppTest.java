package Labyrinth;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
<<<<<<< HEAD
	public void testDestination() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		App.destination("f");
		assertEquals("You went forward\r\n", outContent.toString());
		outContent.reset();
		App.destination("r");
		assertEquals("You went right\r\n", outContent.toString());
		outContent.reset();
		App.destination("l");
		assertEquals("You went left\r\n", outContent.toString());
		outContent.reset();
		App.destination("-h");
		assertEquals(
				"Write 'l' if you want to go left, 'r' if you want to go right or 'f' if you want to go straight.\r\n",
				outContent.toString());
		outContent.reset();
=======
	public void testTrue() {
		Assert.assertTrue(true);
	}

	@Test
	public void testPoint() {
		Point point1 = new Point(-1, 0);
		Point point2 = new Point(0, 0);
		Assert.assertTrue(point1.getX() == point2.getX() && point1.getY() == point2.getY());
>>>>>>> f36678b (terminal demo v0.0.0)
	}

	@Test
	public void testPlane() {
		Plane plane = new Plane(4, 3);
		plane.setCell(new Point(0, 0), Plane.Type.WALL);
		plane.setCell(new Point(1, 2), Plane.Type.WALL);
		plane.setCell(new Point(2, 3), Plane.Type.ENTITY);
		Assert.assertEquals("#  \n   \n # \n  E\n", plane.show());

	}
}
