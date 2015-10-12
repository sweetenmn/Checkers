package game;

import helpers.CellState;
import checkersGUI.Board;

public class Rules {
	private static int count = 0;
	private Board board;

	//isKing should be a method corresponding to a chip, not a boolean. I think.
	//Maybe just change Rules() to isKing? and isKing to kinged.
	public Rules(Board board){
		this.board = board;
	}
	
	public boolean isLegal(Cell origin, Cell destination, Player player){
		if (playerTurn(origin) && player.isPlayerChip(origin.getState())){
			if(origin.getState() == CellState.RED_KING || origin.getState() == 
					CellState.BLACK_KING){
				boolean result = Math.abs(destination.getColumn() - origin.getColumn()) == 1 
						&& Math.abs(destination.getRow() - origin.getRow()) == 1;
				if (result){
					count++;
				}
				return result;
			} else {
				return LegalMoves(origin, destination);
			}
		} else {
			return false;
		}
	}
	
	public boolean LegalMoves(Cell origin, Cell destination){
		if(origin.getState() == CellState.BLACK){
			boolean result = Math.abs(destination.getColumn() - origin.getColumn()) == 1
					&& destination.getRow() - origin.getRow() == -1;
			if (result){
				count++;
			}
			
			return result;
		} else if (origin.getState() == CellState.RED){
			boolean result = Math.abs(destination.getColumn() - origin.getColumn()) == 1 
					&& destination.getRow() - origin.getRow() == 1;
			if (result){
				count++;
			}
			return result;
		}
		return false;
	}
	/*Rules:
	 * 
	 * Black moves first, then players alternate turns.
	 * keeps count of the moves, if even number, black moves. if odd, red moves.
	 * counter changes when the players are switched.
	 */
	public boolean playerTurn(Cell movingPiece){
		switch(movingPiece.getState()){
			case BLACK: case BLACK_KING:
				return count % 2 == 0;
			case RED: case RED_KING:
				return count % 2 != 0;
			case EMPTY:
				break;
		}
		return false;
	}
	
	//captured pieces are removed.
	 public void isCaptured(Cell jumped){
		 board.removeCell(jumped);
	 }
	
	 public void Jump(Cell origin, Cell enemy, Cell destination){
		 if(isAvailable(origin, enemy, destination)){
			 //origin chip moves to destination. Not sure how to make this 
			 //the only available movement.
			 isCaptured(enemy);
			 //doesn't change players to allow multiple jumps & checks for another
			 //available jump.
		 }else{
			 count++;
		 }
	 }
	 //if enemy's chip is in movement square, and there is not a piece on the 
		 //other side of it, return true.
	 public boolean isAvailable(Cell origin, Cell enemy, Cell destination){		 
		 if(origin.getState()==CellState.BLACK && (enemy.getState()==CellState.RED ||
				 enemy.getState()==CellState.RED_KING)){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2
					 && destination.getRow() - origin.getRow() == -2;
		 }else if(origin.getState()==CellState.RED &&(enemy.getState()
				 ==CellState.BLACK || enemy.getState()==CellState.BLACK_KING)){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2 
					 && destination.getRow() - origin.getRow() == 2;
		 }else if(origin.getState()==CellState.BLACK_KING && (enemy.getState()==
				 CellState.RED || enemy.getState()==CellState.RED_KING)){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2 && 
					 Math.abs(destination.getRow() - origin.getRow()) == 2;
		 }else if(origin.getState()==CellState.RED_KING && (enemy.getState()==
				 CellState.BLACK || enemy.getState()==CellState.BLACK_KING)){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2 && 
					 Math.abs(destination.getRow() - origin.getRow()) == 2;
		 }
		 return false;
	 }
		 
	 /* When a piece reaches the other side, it gets made into a king.
	 * A player wins when the other cannot make a move.
	 */
}