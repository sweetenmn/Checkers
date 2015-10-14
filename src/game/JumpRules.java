package game;

import helpers.Coordinate;

import java.util.ArrayList;

import checkersGUI.Board;

public class JumpRules {
	Board board;
	Rules rules;
	
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
	
	public boolean hasJump(Cell origin){
		ArrayList<Cell> possibleEnemies = getPossibleEnemies(origin);
		if (!possibleEnemies.isEmpty()){
			for(Cell enemy : possibleEnemies){
				if (hasPossibleDestination(origin, enemy)){
					return true;
				}
			}
		}
		return false;
	}
	private ArrayList<Cell> getPossibleEnemies(Cell origin){
		ArrayList<Cell> enemies = new ArrayList<Cell>();
		switch(origin.getState()){
		case BLACK:
			addLegalEnemy(origin, rules.upLeftFrom(origin), enemies);
			addLegalEnemy(origin, rules.upRightFrom(origin), enemies);
			break;
		case RED:
			addLegalEnemy(origin, rules.downLeftFrom(origin), enemies);
			addLegalEnemy(origin, rules.downRightFrom(origin), enemies);
			break;
		case BLACK_KING: case RED_KING:
			addLegalEnemy(origin, rules.upLeftFrom(origin), enemies);
			addLegalEnemy(origin, rules.upRightFrom(origin), enemies);
			addLegalEnemy(origin, rules.downLeftFrom(origin), enemies);
			addLegalEnemy(origin, rules.downRightFrom(origin), enemies);
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
			if (isEnemy(origin, board.getCellAt(coord))){
				return true;
			}
		}
		return false;
	}
	
	private boolean isEnemy(Cell origin, Cell enemy){
		 switch(origin.getState()){
		 case BLACK: case BLACK_KING:
			 return rules.isRedChip(enemy) || rules.isRedKing(enemy);
		 case RED: case RED_KING:
			 return rules.isBlackChip(enemy) || rules.isBlackKing(enemy);
		 case EMPTY:
			 break;
		 }
		 return false;
	 }
	
	private boolean cellInRange(Coordinate coord){
		return rangeCheck(coord.column()) && rangeCheck(coord.row());
	}
	
	private boolean rangeCheck(int index){return index >= 0 && index <= 7;}
	private boolean hasPossibleDestination(Cell origin, Cell enemy){
		String location = origin.compareCoords(enemy.getCoords());
		if (location.equals("UL")){
			return validDestination(rules.upLeftFrom(enemy));
		} else if (location.equals("UR")){
			return validDestination(rules.upRightFrom(enemy));
		} else if (location.equals("DL")){
			return validDestination(rules.downLeftFrom(enemy));
		} else if (location.equals("DR")){
			return validDestination(rules.downRightFrom(enemy));
		}
		return false;
	}
	
	private boolean validDestination(Coordinate coord){
		if (cellInRange(coord)){
			if (rules.isEmpty(board.getCellAt(coord))){
				return true;
			}
		}
		return false;
	}
	
	public boolean isJump(Cell origin, Cell destination){
		switch(origin.getState()){
		case RED:
			return rules.isColumnsAway(origin, destination, 2) &&
					rules.isRowsOneWay(destination, origin, 2);
		case BLACK:
			return rules.isColumnsAway(origin, destination, 2) && 
					rules.isRowsOneWay(origin, destination, 2);
		case RED_KING: case BLACK_KING:
			return rules.isColumnsAway(origin, destination, 2) && 
					rules.isRowsAway(destination, origin, 2);
		case EMPTY:
			break;
		}
		return false;
	}
	
	public Cell getMiddleChip(Cell origin, Cell dest){
		if (dest.getColumn() > origin.getColumn()){
			if (dest.getRow() > origin.getRow()){
				return board.getCellAt(rules.downRightFrom(origin));
			} else {
				return board.getCellAt(rules.upRightFrom(origin));
			}
		} else {
			if (dest.getRow() > origin.getRow()){
				return board.getCellAt(rules.downLeftFrom(origin));
			} else {
				return board.getCellAt(rules.upLeftFrom(origin));
			}
		}
	}

	

}
