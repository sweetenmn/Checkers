package game;

import helpers.CellState;
import helpers.PlayerID;

public class Player {
	PlayerID player;
	
	public Player(PlayerID playID){
		player = playID;
	}
	
	public PlayerID getID(){
		return player;
		}
	
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

}
