package game;

import static org.junit.Assert.*;
import helpers.CellState;
import helpers.PlayerID;
import helpers.TurnCounter;

import org.junit.Test;

public class PlayerTest {
	PlayerID id = PlayerID.BLACK;
	TurnCounter count = new TurnCounter();
	Player player = new Player(id, count);
	
	@Test
	public void test() {
		assertEquals(player.getID(), id);
		assertTrue(player.isPlayerChip(CellState.BLACK));
		assertFalse(player.isPlayerChip(CellState.RED));
		assertTrue(player.isPlayerChip(CellState.BLACK_KING));
		assertFalse(player.isPlayerChip(CellState.RED_KING));
		assertFalse(player.isPlayerChip(CellState.EMPTY));
		
	}
	
	@Test
	public void playerTurn(){
		assertTrue(player.isPlayerMove(CellState.BLACK));
		assertTrue(player.isPlayerMove(CellState.BLACK_KING));
		assertFalse(player.isPlayerMove(CellState.RED));
		assertFalse(player.isPlayerMove(CellState.RED_KING));
		assertTrue(player.isPlayerTurn(CellState.BLACK));
		assertTrue(player.isPlayerTurn(CellState.BLACK_KING));
		assertFalse(player.isPlayerTurn(CellState.RED));
		assertFalse(player.isPlayerTurn(CellState.RED_KING));
		assertTrue(player.isBlackTurn());
		assertFalse(player.isRedTurn());
		count.increment();
		assertFalse(player.isBlackTurn());
		assertTrue(player.isRedTurn());
		count.increment();
		assertTrue(player.isBlackTurn());
		assertFalse(player.isRedTurn());
	}

}
