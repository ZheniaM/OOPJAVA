package Labyrinth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
<<<<<<< HEAD
  public void testInput() {
    String input = "f";
    String expectedOutput = "You went forward";
    String actualOutput = getOutput(input);
    assertEquals(expectedOutput, actualOutput);
  }
  
  private String getOutput(String input) {
    switch(input) {
      case("f"):
      case("front"):
        return "You went forward";
      case("r"):
      case("right"):
        return "You went right";
      case("l"):
      case("left"):
        return "You went left";
      case("--help"):
      case("-h"):
        return "Write 'l' if you want to go left, 'r' if you want to go right or 'f' if you want to go straight.";
      default:
        return "There is no such direction";
    }
  }
=======
	public void testTrue() {
		Assert.assertTrue(true);
	}
>>>>>>> 28e49e31ba7e8bfff849459924c11935aafe2631
}

