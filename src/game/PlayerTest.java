package game;

import static org.junit.Assert.*;
import helpers.CellState;
import helpers.PlayerID;

import org.junit.Test;

public class PlayerTest {
	PlayerID id = PlayerID.BLACK;
	Player player = new Player(id);
	
	@Test
	public void test() {
		assertTrue(player.getID()== id);
		assertTrue(player.isPlayerChip(CellState.BLACK));
		assertFalse(player.isPlayerChip(CellState.RED));
	}

}
