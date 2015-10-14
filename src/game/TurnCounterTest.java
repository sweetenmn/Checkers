package game;

import static org.junit.Assert.*;

import org.junit.Test;

public class TurnCounterTest {
	TurnCounter turn = new TurnCounter();
	
	
	@Test
	public void test() {
		assertTrue(turn.getCount() == 0);
		turn.increment();
		turn.increment();
		turn.increment();
		assertEquals(turn.getCount(), 3);
	}

}
