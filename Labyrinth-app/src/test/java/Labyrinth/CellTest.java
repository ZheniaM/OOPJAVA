package Labyrinth;

import org.junit.Assert;
import org.junit.Test;

public class CellTest {
	@Test
	public void testSymbols() {
		Assert.assertEquals("P", Cell.PLAYERR_FLOOR.getSymbol());
		Assert.assertEquals("X", Cell.EXIT.getSymbol());
		Assert.assertEquals("░", Cell.FLOOR.getSymbol());
		Assert.assertEquals("█", Cell.WALL.getSymbol());
		Assert.assertEquals("█", Cell.WALL_LEFT.getSymbol());
		Assert.assertEquals("█", Cell.WALL_RIGHT.getSymbol());
		Assert.assertEquals("█", Cell.WALL_TB.getSymbol());
		Assert.assertEquals("█", Cell.WALL_BRI.getSymbol());
		Assert.assertEquals("█", Cell.WALL_BLI.getSymbol());
		Assert.assertEquals("█", Cell.WALL_UR.getSymbol());
		Assert.assertEquals("█", Cell.WALL_UL.getSymbol());
	}
}
