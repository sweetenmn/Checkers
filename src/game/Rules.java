package game;

import helpers.CellState;
import helpers.Coordinate;
import helpers.PlayerID;
import helpers.TurnCounter;
import checkersGUI.Board;

public class Rules {
	private TurnCounter counter;
	private JumpRules jumpRules;
	static final int NORM_RANGE = 1;

	public Rules(Board board){
		this.counter = board.getCounter();
		jumpRules = new JumpRules(board, this);
	}
	
	public boolean isLegal(Cell origin, Cell destination, Player player){
		/////TESTING BLOCK
		if (isBlackChip(origin) || isBlackKing(origin)){
			player = new Player(PlayerID.BLACK);
			} else if (isRedChip(origin) || isRedKing(origin)){
				player = new Player(PlayerID.RED);
			}
		///END TESTING BLOCK. ADD && player.isPlayerChip(origin.getState()) TO BELOW IF-STATEMENT FOR FINAL
		if (playerTurn(origin) ){
			if (jumpRules.playerCanJump(player)){
				if (isJump(origin, destination) &&
						!isEmpty(jumpRules.getMiddleChip(origin, destination))){
					return true;
					} else {
						return false;
					}
				} else if (isRedKing(origin) || isBlackKing(origin)){
					return isColumnsAway(destination, origin, NORM_RANGE) && 
							isRowsAway(destination, origin, NORM_RANGE);
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
		return isColumnsAway(origin, destination, NORM_RANGE)
		&& isRowsOneWay(origin, destination, NORM_RANGE);
	case RED:
		return isColumnsAway(origin, destination, NORM_RANGE)
		&& isRowsOneWay(destination, origin, NORM_RANGE);
	case RED_KING: case BLACK_KING: case EMPTY:
		break;
	}
	return false;
	}
	
	public boolean playerTurn(Cell movingPiece){
	switch(movingPiece.getState()){
		case BLACK: case BLACK_KING:
			return isBlackTurn();
		case RED: case RED_KING:
			return !isBlackTurn();
		case EMPTY:
			break;
	}
	return false;
	}
	
	public boolean isBlackTurn(){return counter.getCount() % 2 == 0;}
 
 	public boolean isBlackChip(Cell cell){return cell.getState() == CellState.BLACK;}
 	public boolean isRedChip(Cell cell){return cell.getState() == CellState.RED;}
 	public boolean isRedKing(Cell cell){return cell.getState() == CellState.RED_KING;}
 	public boolean isBlackKing(Cell cell){return cell.getState() == CellState.BLACK_KING;}
 	public boolean isEmpty(Cell cell){return cell.getState() == CellState.EMPTY;}
 	
	public boolean isColumnsAway(Cell cell, Cell other, int desired){
		return Math.abs(cell.getColumn() - other.getColumn()) == desired;
	}
	public boolean isRowsOneWay(Cell cell, Cell other, int desired){
		return cell.getRow() - other.getRow() == desired;
	}
	public boolean isRowsAway(Cell cell, Cell other, int desired){
		return Math.abs(cell.getRow() - other.getRow()) == desired;
	}
	public Coordinate upLeftFrom(Cell origin){
		return new Coordinate(origin.getColumn() - NORM_RANGE,
				origin.getRow() - NORM_RANGE);
	}
	public Coordinate upRightFrom(Cell origin){
		return new Coordinate(origin.getColumn() + NORM_RANGE,
				origin.getRow() - NORM_RANGE);
	}
	public Coordinate downLeftFrom(Cell origin){
		return new Coordinate(origin.getColumn() - NORM_RANGE, 
				origin.getRow() + NORM_RANGE);
	}
	public Coordinate downRightFrom(Cell origin){
		return new Coordinate(origin.getColumn() + NORM_RANGE, 
				origin.getRow() + NORM_RANGE);
	}
}