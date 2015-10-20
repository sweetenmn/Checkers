package game;

import helpers.CellState;
import helpers.PlayerID;
import helpers.TurnCounter;

public class Player {
	PlayerID player;
	TurnCounter counter;
	
	public Player(PlayerID playID, TurnCounter counter){
		player = playID;
		this.counter = counter;
	}
	
	public PlayerID getID(){return player;}
	
	public boolean isPlayerChip(CellState state){
		switch(state){
		case BLACK: case BLACK_KING:
			return player == PlayerID.BLACK;
		case EMPTY:
			return false;
		case RED: case RED_KING:
			return player == PlayerID.RED;
		}
		return false;
	}
	
	public boolean isPlayerMove(CellState attempted){
		return isPlayerChip(attempted) && isPlayerTurn(attempted);
	}
	public boolean isPlayerTurn(CellState state){
		switch(state){
			case BLACK: case BLACK_KING:
				return isBlackTurn();
			case RED: case RED_KING:
				return isRedTurn();
			case EMPTY:
				break;
		}
		return false;
		}
	
	public boolean isBlackTurn(){return counter.getCount() % 2 == 0;}
	public boolean isRedTurn(){return counter.getCount() % 2 != 0;}

}
