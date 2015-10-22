package game;

import static org.junit.Assert.*;
import helpers.CellState;
import helpers.PlayerID;
import helpers.TurnCounter;

import org.junit.Test;

public class PlayerTest {
	PlayerID id = PlayerID.BLACK;
	Player player = new Player(id, new TurnCounter());
	
	@Test
	public void test() {
		assertTrue(player.getID()== id);
		assertTrue(player.isPlayerChip(CellState.BLACK));
		assertFalse(player.isPlayerChip(CellState.RED));
	}

}
