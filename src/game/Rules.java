package game;

import helpers.CellState;
import helpers.Coordinate;
import helpers.PlayerID;
import helpers.TurnCounter;
import checkersGUI.Board;

public class Rules {
	private TurnCounter counter;
	private JumpRules jumpRules;

	public Rules(Board board){
		this.counter = board.getCounter();
		jumpRules = new JumpRules(board, this);
	}
	
	public boolean isLegal(Cell origin, Cell destination, Player player){
		if (playerTurn(origin) ){
			//&& player.isPlayerChip(origin.getState())
			//FOR TESTING*********************
			if (isBlackChip(origin) || isBlackKing(origin)){
				player = new Player(PlayerID.BLACK);
			} else if(isRedChip(origin) || isRedKing(origin)){
				player = new Player(PlayerID.RED);
			} 
			//END TESTING**********************/
			if (jumpRules.playerCanJump(player)){
				if (isJump(origin, destination) &&
						!isEmpty(jumpRules.getMiddleChip(origin, destination))){
					return true;
					} else {
						return false;
					}
				} else if (isRedKing(origin) || isBlackKing(origin)){
					return Math.abs(destination.getColumn()-origin.getColumn()) == 1 
						&& Math.abs(destination.getRow() - origin.getRow()) == 1;
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
		return isColumnsAway(origin, destination, 1)
		&& isRowsOneWay(origin, destination, 1);
	case RED:
		return isColumnsAway(origin, destination, 1)
		&& isRowsOneWay(destination, origin, 1);
	case RED_KING: case BLACK_KING: case EMPTY:
		break;
	}
	return false;
	}
	
	public boolean playerTurn(Cell movingPiece){
	switch(movingPiece.getState()){
		case BLACK: case BLACK_KING:
			return counter.getCount() % 2 == 0;
		case RED: case RED_KING:
			return counter.getCount() % 2 != 0;
		case EMPTY:
			break;
	}
	return false;
	}
 
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
		return new Coordinate(origin.getColumn() - 1, origin.getRow() - 1);
	}
	public Coordinate upRightFrom(Cell origin){
		return new Coordinate(origin.getColumn() + 1, origin.getRow() - 1);
	}
	public Coordinate downLeftFrom(Cell origin){
		return new Coordinate(origin.getColumn() - 1, origin.getRow() + 1);
	}
	public Coordinate downRightFrom(Cell origin){
		return new Coordinate(origin.getColumn() + 1, origin.getRow() + 1);
	}
}