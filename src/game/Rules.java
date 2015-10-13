package game;

import java.util.ArrayList;

import helpers.CellState;
import helpers.Coordinate;
import checkersGUI.Board;

public class Rules {
	private Board board;
	private TurnCounter counter;

	public Rules(Board board){
		this.counter = board.getCounter();
		this.board = board;
	}
	
	public boolean hasJump(Cell origin){
		ArrayList<Cell> possibleEnemies = getPossibleEnemies(origin);
		if (!possibleEnemies.isEmpty()){
			
		for(Cell enemy : possibleEnemies){
			System.out.println("in possible dest loop");
			if (isPossibleDest(origin, enemy)){
				System.out.println("in possible if");
				return true;
			}
		}
		}
		return false;
	}
	private ArrayList<Cell> getPossibleEnemies(Cell origin){
		ArrayList<Cell> enemies = new ArrayList<Cell>();
		Coordinate upLeft = new Coordinate(origin.getColumn() - 1, origin.getRow() - 1);
		Coordinate upRight = new Coordinate(origin.getColumn() + 1, origin.getRow() - 1);
		Coordinate downLeft = new Coordinate(origin.getColumn() - 1, origin.getRow() + 1);
		Coordinate downRight = new Coordinate(origin.getColumn() + 1, origin.getRow() + 1);
		
		switch(origin.getState()){
		case BLACK:
			if (enemyInRange(upLeft)){ 
				if (isEnemy(origin, board.getCellAt(upLeft))){
					enemies.add(board.getCellAt(upLeft)); 
					}
				}
			if (enemyInRange(upRight)){ 
				if (isEnemy(origin, board.getCellAt(upRight))){
					enemies.add(board.getCellAt(upRight)); 
					}
				}
			break;
		case RED:
			if (enemyInRange(downLeft)){
				if (isEnemy(origin, board.getCellAt(downLeft))){
					enemies.add(board.getCellAt(downLeft)); 
					}
				}
			if (enemyInRange(downRight)){
				if (isEnemy(origin, board.getCellAt(downRight))){
					enemies.add(board.getCellAt(downRight)); 
					}
				}
			break;
		case BLACK_KING: case RED_KING:
			if (enemyInRange(upLeft)){
				if (isEnemy(origin, board.getCellAt(upLeft))){
					enemies.add(board.getCellAt(upLeft)); 
					}
				}
			if (enemyInRange(upRight)){
				if (isEnemy(origin, board.getCellAt(upRight))){
					enemies.add(board.getCellAt(upRight)); 
					}
				}
			if (enemyInRange(downLeft)){ 
				if (isEnemy(origin, board.getCellAt(downLeft))){
					enemies.add(board.getCellAt(downLeft)); 
					}
				}
			if (enemyInRange(downRight)){ 
				if (isEnemy(origin, board.getCellAt(downRight))){
					enemies.add(board.getCellAt(downRight)); 
					}
				}
			break;
		case EMPTY:
			break;
		}
		return enemies;
	}
	private boolean isPossibleDest(Cell origin, Cell enemy){
		Coordinate upLeft = new Coordinate(enemy.getColumn() - 1, enemy.getRow() - 1);
		Coordinate upRight = new Coordinate(enemy.getColumn() + 1, enemy.getRow() - 1);
		Coordinate downLeft = new Coordinate(enemy.getColumn() - 1, enemy.getRow() + 1);
		Coordinate downRight = new Coordinate(enemy.getColumn() + 1, enemy.getRow() + 1);
		String location = origin.compareCoords(enemy.getCoords());
		
		if (location.equals("UL")){
			if (enemyInRange(upLeft)){
				if (board.getCellAt(upLeft).getState() == CellState.EMPTY){
					return true; 
				}
			}
		} else if (location.equals("UR")){
			if (enemyInRange(upRight)){
				if (board.getCellAt(upRight).getState() == CellState.EMPTY){
					return true;
				}
			}
		} else if (location.equals("DL")){
			if (enemyInRange(downLeft)){
				if (board.getCellAt(downLeft).getState() == CellState.EMPTY){
					return true;
				}
			}
		} else if (location.equals("DR")){
			if (enemyInRange(downRight)){
				if (board.getCellAt(downRight).getState() == CellState.EMPTY){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean enemyInRange(Coordinate coord){
		return enemyRangeCheck(coord.column()) && enemyRangeCheck(coord.row());
	}
	
	private boolean enemyRangeCheck(int index){
		return index >= 0 && index <= 7;
	}
	public boolean isJump(Cell origin, Cell destination){
		switch(origin.getState()){
		case RED:
			return Math.abs(origin.getColumn() - destination.getColumn()) == 2 &&
					origin.getRow() - destination.getRow() == -2;
		case BLACK:
			return Math.abs(origin.getColumn() - destination.getColumn()) == 2 &&
			origin.getRow() - destination.getRow() == 2;
		case RED_KING: case BLACK_KING:
			return Math.abs(origin.getColumn() - destination.getColumn()) == 2 &&
					Math.abs(origin.getRow() - destination.getRow()) == 2;
		case EMPTY:
			break;
		}
		return false;
	}
	
	public Cell getMiddleChip(Cell origin, Cell dest){
		if (dest.getColumn() > origin.getColumn()){
			if (dest.getRow() > origin.getRow()){
				return board.getCellAt(new Coordinate(origin.getColumn() + 1, origin.getRow() + 1));
			} else {
				return board.getCellAt(new Coordinate(origin.getColumn() + 1, origin.getRow() - 1));
			}
		} else {
			if (dest.getRow() > origin.getRow()){
				return board.getCellAt(new Coordinate(origin.getColumn() - 1, origin.getRow() + 1));
			} else {
				return board.getCellAt(new Coordinate(origin.getColumn() - 1, origin.getRow() - 1));
			}
		}
	}
	
	public boolean isLegal(Cell origin, Cell destination, Player player){
		System.out.println(String.valueOf(counter.getCount()));
		if (hasJump(origin)){
			if (isJump(origin, destination) && 
					getMiddleChip(origin, destination).getState() != CellState.EMPTY){
				return true;
			} else {
				return false;
			}
			//&& player.isPlayerChip(origin.getState())
		} else if (playerTurn(origin)){
			if(origin.getState() == CellState.RED_KING || origin.getState() == 
					CellState.BLACK_KING){
				boolean result = Math.abs(destination.getColumn()-origin.getColumn()) == 1 
						&& Math.abs(destination.getRow() - origin.getRow()) == 1;
				//if (result){
			//		counter.increment();
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
				/*counter.increment();*/
				System.out.println(String.valueOf(counter.getCount()));
			}
			return result;
		} else if (origin.getState() == CellState.RED){
			boolean result = Math.abs(destination.getColumn() - origin.getColumn()) == 1 
					&& destination.getRow() - origin.getRow() == 1;
			if (result){
			//	counter.increment();
				System.out.println(String.valueOf(counter.getCount()));
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
				return counter.getCount() % 2 == 0;
			case RED: case RED_KING:
				return counter.getCount() % 2 != 0;
			case EMPTY:
				break;
		}
		return false;
	}
	
	//captured pieces are removed.
	 public void isCaptured(Cell jumped){
		 board.removeCell(jumped);
	 }
	
	/* public void jump(Cell origin, Cell enemy, Cell destination){
		 if(isAvailable(origin, enemy, destination)){
			 //origin chip moves to destination. Not sure how to make this 
			 //the only available movement.
			 isCaptured(enemy);
			 //doesn't change players to allow multiple jumps & checks for another
			 //available jump.
		 }
	 }*/
	 //will accept an empty square for the false statement.
	 public boolean isEnemy(Cell origin, Cell enemy){
		 switch(origin.getState()){
		 case BLACK: case BLACK_KING:
			 return (enemy.getState()==CellState.RED || 
			 enemy.getState()==CellState.RED_KING);
		 case RED: case RED_KING:
			 return (enemy.getState()==CellState.BLACK || 
			 enemy.getState()==CellState.BLACK_KING);
		case EMPTY:
			break;
		default:
			return false;
			 
		 }
		 return false;
	 }
	 //if enemy's chip is in movement square, and there is not a piece on the 
		 //other side of it, return true.
	 public boolean isAvailable(Cell origin, Cell destination){	
		 
		 /*
		 if(origin.getState()==CellState.BLACK && Reds(enemy) == true){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2
					 && destination.getRow() - origin.getRow() == -2;
		 }else if(origin.getState()==CellState.RED && Reds(enemy)== false){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2 
					 && destination.getRow() - origin.getRow() == 2;
		 }else if(origin.getState()==CellState.BLACK_KING && Reds(enemy)==true){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2 && 
					 Math.abs(destination.getRow() - origin.getRow()) == 2;
		 }else if(origin.getState()==CellState.RED_KING && Reds(enemy)==false){
			 return Math.abs(destination.getColumn() - origin.getColumn()) == 2 && 
					 Math.abs(destination.getRow() - origin.getRow()) == 2;
					 
		 }*/
		 return false;
	 }
}