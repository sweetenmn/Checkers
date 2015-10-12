package game;

import checkersGUI.Board;

public class Rules {
	private static int count = 0;

	//isKing should be a method corresponding to a chip, not a boolean. I think.
	//Maybe just change Rules() to isKing? and isKing to kinged.

	
	public boolean isLegal(Cell origin, Cell destination){
		if(origin.getState() == CellState.RED_KING || origin.getState() == 
				CellState.BLACK_KING){
			return Math.abs(destination.getColumn() - origin.getColumn()) == 1 && 
					Math.abs(destination.getRow() - origin.getRow()) == 1;
		} else {

			return LegalMoves(origin, destination);
		}
	}
	
	public boolean LegalMoves(Cell origin, Cell destination){
		if(origin.getState() == CellState.BLACK){
			return Math.abs(destination.getColumn() - origin.getColumn()) == 1
					&& destination.getRow() - origin.getRow() == -1;
		} else if (origin.getState() == CellState.RED){
			return Math.abs(destination.getColumn() - origin.getColumn()) == 1 
					&& destination.getRow() - origin.getRow() == 1;
		}
		return false;
	}
	/*Rules:
	 * 
	 * Black moves first, then players alternate turns.
	 * keeps count of the moves, if even number, black moves. if odd, red moves.
	 * counter changes when the players are switched.
	 */
	public void playerTurn(){
		if(count % 2 == 0){
			//black moves
		}else{
			//red moves
		}
	}
	
	//captured pieces are removed.
	 public void isCaptured(Cell jumped){
		 //Imported Board, but this command wants to be called statically.
		 //Board.removeCell(jumped);
	 }
	
	 public void Jump(Cell origin, Cell enemy, Cell destination){
		 if(isAvailable(origin, enemy, destination) == true){
			 //origin chip moves to destination. Not sure how to make this 
			 //the only available movement.
			 isCaptured(enemy);
			 //doesn't change players to allow multiple jumps & checks for another
			 //available jump.
		 }else{
			 count ++;
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