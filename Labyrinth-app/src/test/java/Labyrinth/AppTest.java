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
	}
}
