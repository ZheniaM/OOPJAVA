package Labyrinth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import Labyrinth.enemy.Bat;
import Labyrinth.enemy.Enemy;

public class BattleTest {


    @Test
    public void testIsOver() {
        Player player = new Player(1,1);
        Enemy enemy = new Bat(new Point(0, 0), 1, 2);
        assertEquals(player.getXp(), 0);
        assertEquals(player.getHp(), 14);
        assertEquals(player.getLVL(), 1);
        Battlefield battlefield = new Battlefield(player, enemy);
        AbilityP ability = AbilityP.PUNCH;
        battlefield.setAbility(ability);
        assertEquals(ability, battlefield.abilityP);
        assertNotNull(battlefield.abilityE);
        battlefield.abilityE = AbilityE.SKIP_TURN;
        String message = battlefield.battle();
        assertEquals("You deal 3 damage, Enemy does not respond", message);
        assertTrue(battlefield.isOver());
        assertTrue(battlefield.winner());
    }
    @Test
    public void testIsLoser(){
        Player player = new Player(1,1);
        Enemy enemy = new Bat(new Point(0, 0), 1, 2);
        Battlefield battlefield = new Battlefield(player, enemy);
        player.reduseHp(14);
        assertTrue(battlefield.isOver());
        assertFalse(battlefield.winner());
    }
}
