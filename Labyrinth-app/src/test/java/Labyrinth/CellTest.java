package Labyrinth;

import org.junit.Assert;
import org.junit.Test;

public class CellTest {
	@Test
	public void testSymbols() {
		Assert.assertEquals("P", Cell.PLAYERR_FLOOR.symbol);
		Assert.assertEquals("X", Cell.EXIT.symbol);
		Assert.assertEquals("░", Cell.FLOOR.symbol);
		Assert.assertEquals("█", Cell.WALL.symbol);
		Assert.assertEquals("█", Cell.WALL_LEFT.symbol);
		Assert.assertEquals("█", Cell.WALL_RIGHT.symbol);
		Assert.assertEquals("█", Cell.WALL_TB.symbol);
		Assert.assertEquals("█", Cell.WALL_BRI.symbol);
		Assert.assertEquals("█", Cell.WALL_BLI.symbol);
		Assert.assertEquals("█", Cell.WALL_UR.symbol);
		Assert.assertEquals("█", Cell.WALL_UL.symbol);
	}
}
