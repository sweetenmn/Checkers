package game;

import helpers.Coordinate;

import java.util.ArrayList;

import checkersGUI.Board;

public class JumpRules {
	Board board;
	Rules rules;
	static final int JUMP_RANGE = 2;
	
	public JumpRules(Board board, Rules rules){
		this.board = board;
		this.rules = rules;
	}
	public boolean playerCanJump(Player player){
		for (Cell c: board.cells){
			if (player.isPlayerChip(c.getState())){
				if(hasJump(c)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasJumpHelper(ArrayList<Cell> possibleEnemies, Cell origin){
		for(Cell enemy : possibleEnemies){
			if (hasPossibleDestination(origin, enemy)){
				return true;
			}
		}
		return false;
	}
	public boolean hasJump(Cell origin){
		ArrayList<Cell> possibleEnemies = getPossibleEnemies(origin);
		if (!possibleEnemies.isEmpty()){
			return hasJumpHelper(possibleEnemies, origin);
		}
		return false;
	}
	
	public boolean isJump(Cell origin, Cell destination){
		switch(origin.getState()){
		case RED:
			return origin.isColumnsAway(destination, JUMP_RANGE) &&
					destination.isRowsOneWay(origin, JUMP_RANGE);
		case BLACK:
			return origin.isColumnsAway(destination, JUMP_RANGE) && 
					origin.isRowsOneWay(destination, JUMP_RANGE);
		case RED_KING: case BLACK_KING:
			return origin.isColumnsAway(destination, JUMP_RANGE) && 
					destination.isRowsAway(origin, JUMP_RANGE);
		case EMPTY:
			break;
		}
		return false;
	}
	
	public Cell getMiddleChip(Cell origin, Cell dest){
		Coordinate originCoord = origin.getCoords();
		if (dest.getColumn() > origin.getColumn()){
			if (dest.getRow() > origin.getRow()){
				return board.getCellAt(originCoord.downRightCoord());
			} else {
				return board.getCellAt(originCoord.upRightCoord());
			}
		} else {
			if (dest.getRow() > origin.getRow()){
				return board.getCellAt(originCoord.downLeftCoord());
			} else {
				return board.getCellAt(originCoord.upLeftCoord());
			}
		}
	}
	private ArrayList<Cell> getPossibleEnemies(Cell origin){
		ArrayList<Cell> enemies = new ArrayList<Cell>();
		Coordinate originCoord = origin.getCoords();
		switch(origin.getState()){
		case BLACK:
			addLegalEnemy(origin, originCoord.upLeftCoord(), enemies);
			addLegalEnemy(origin, originCoord.upRightCoord(), enemies);
			break;
		case RED:
			addLegalEnemy(origin, originCoord.downLeftCoord(), enemies);
			addLegalEnemy(origin, originCoord.downRightCoord(), enemies);
			break;
		case BLACK_KING: case RED_KING:
			addLegalEnemy(origin, originCoord.upLeftCoord(), enemies);
			addLegalEnemy(origin, originCoord.upRightCoord(), enemies);
			addLegalEnemy(origin, originCoord.downLeftCoord(), enemies);
			addLegalEnemy(origin, originCoord.downRightCoord(), enemies);
			break;
		case EMPTY:
			break;
		}
		return enemies;
	}
	private void addLegalEnemy(Cell origin, Coordinate enemyCoord, ArrayList<Cell> enemies){
		if (validEnemy(origin, enemyCoord)){
			enemies.add(board.getCellAt(enemyCoord)); 
		}
	}
	
	private boolean validEnemy(Cell origin, Coordinate coord){
		if (cellInRange(coord)){ 
			return isEnemy(origin, board.getCellAt(coord));
		}
		return false;
	}
	
	private boolean isEnemy(Cell origin, Cell enemy){
		 switch(origin.getState()){
		 case BLACK: case BLACK_KING:
			 return enemy.isRedChip() || enemy.isRedKing();
		 case RED: case RED_KING:
			 return enemy.isBlackChip() || enemy.isBlackKing();
		 case EMPTY:
			 break;
		 }
		 return false;
	 }
	
	private boolean cellInRange(Coordinate coord){
		return rangeCheck(coord.column()) && rangeCheck(coord.row());
	}
	
	private boolean rangeCheck(int index){
		return index >= Cell.TOP_ROW && index <= Cell.BOTTOM_ROW;}
	
	private boolean hasPossibleDestination(Cell origin, Cell enemy){
		Coordinate enemyCoord = enemy.getCoords();
		String location = origin.compareCoords(enemyCoord);
		
		if (location.equals("UL")){
			return validDestination(enemyCoord.upLeftCoord());
		} else if (location.equals("UR")){
			return validDestination(enemyCoord.upRightCoord());
		} else if (location.equals("DL")){
			return validDestination(enemyCoord.downLeftCoord());
		} else if (location.equals("DR")){
			return validDestination(enemyCoord.downRightCoord());
		}
		return false;
	}
	
	private boolean validDestination(Coordinate coord){
		if (cellInRange(coord)){
			return board.getCellAt(coord).isEmpty();	
		} else {
			return false;
		}
	}
	
}
