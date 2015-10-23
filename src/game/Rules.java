package game;

import checkersGUI.Board;

public class Rules {
	private JumpRules jumpRules;
	public static final int NORM_RANGE = 1;

	public Rules(Board board){
		//remove counter for final
		jumpRules = new JumpRules(board, this);
	}
	
	public boolean isLegal(Cell origin, Cell destination, Player player){
		if (player.isPlayerMove(origin.getState())) {
			if (jumpRules.playerCanJump(player)){
				return (isJump(origin, destination) &&
						!jumpRules.getMiddleChip(origin, destination).isEmpty());
					
			} else if (origin.isRedKing() || origin.isBlackKing()){
				return destination.isColumnsAway(origin, NORM_RANGE) && 
						destination.isRowsAway(origin, NORM_RANGE);
			} else {
				return isNormalLegalMove(origin, destination);
			}			
		} else { 
			return false;
		}
	}
	
	public boolean isJump(Cell origin, Cell destination){
		return jumpRules.isJump(origin, destination);
	}
	
	public boolean hasJump(Cell cell){
		return jumpRules.hasJump(cell);
	}
		
	public Cell getMiddleChip(Cell origin, Cell dest){
		return jumpRules.getMiddleChip(origin, dest);
	}
		
	public boolean isNormalLegalMove(Cell origin, Cell destination){
		switch(origin.getState()){
			case BLACK:
				return origin.isColumnsAway(destination, NORM_RANGE)
				&& origin.isRowsOneWay(destination, NORM_RANGE);
			case RED:
				return origin.isColumnsAway(destination, NORM_RANGE)
				&& destination.isRowsOneWay(origin, NORM_RANGE);
			case RED_KING: case BLACK_KING: case EMPTY:
				break;
		}
		return false;
	}
	
 	
}