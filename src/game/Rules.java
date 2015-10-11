package game;

public class Rules {
	private static boolean isKing;
	private static int count = 0;
	public Rules(boolean kinged){
		isKing = kinged;		
	}
	
	public boolean isLegal(Cell origin, Cell destination){
		if(isKing == true){
			return Math.abs(destination.getColumn() - origin.getColumn()) == 1 
					&& Math.abs(destination.getRow() - origin.getRow()) == 1;
		}else{
			return LegalMoves(origin, destination);
		}
	}
	
	public boolean LegalMoves(Cell origin, Cell destination){
		if(origin.state == Cell.BLACK){
			return Math.abs(destination.getColumn() - origin.getColumn()) == 1
					&& destination.getRow() - origin.getRow() == -1;
		}else{
			return Math.abs(destination.getColumn() - origin.getColumn()) == 1 
					&& destination.getRow() - origin.getRow() == 1;
		}
	}
	/*Rules:
	 * 
	 * Black moves first, then players alternate turns.
	 * keeps count of the moves, if even number, black moves. if odd, red moves.
	 * counter changes when the players are switched.
	 */
	public void movesFirst(){
		if(count % 2 == 0){
			//black moves
		}else{
			//red moves
		}
	}
	/*
	 
	 * Pieces not jumping can only move forward one square at a time (I think this
	 * may have already been covered by the prev. piece movement).
	 * 
	 * Only one opponent piece can be captured per jump, but multiple jumps are 
	 * allowed.
	 * 
	 * Pieces that are captured need to be removed from the board.
	 * 
	 * If a jump is available, it must be made. If more than one jump is available,
	 * the player can choose either.
	 * 
	 * When a piece reaches the other side, it gets made into a king.
	 * 
	 * Kings can move backwards and forwards (Maybe have a king interface for this?)
	 * 
	 * Kings can do multiple jumps backwards and forwards, or a combination of those.
	 * 
	 * A player wins when the other cannot make a move.
	 * 
	 */
	
	
	
}
