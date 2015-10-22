package helpers;

import game.Rules;

public class Coordinate {
	private int column;
	private int row;
	
	public Coordinate(int x, int y){
		column = x;
		row = y;
	}
	
	public int column(){return column;}
	public int row(){return row;}
	
	public Coordinate upLeftCoord(){
		return new Coordinate(column() - Rules.NORM_RANGE,
				row() - Rules.NORM_RANGE);
	}
	public Coordinate upRightCoord(){
		return new Coordinate(column() + Rules.NORM_RANGE,
				row() - Rules.NORM_RANGE);
	}
	public Coordinate downLeftCoord(){
		return new Coordinate(column() - Rules.NORM_RANGE, 
				row() + Rules.NORM_RANGE);
	}
	public Coordinate downRightCoord(){
		return new Coordinate(column() + Rules.NORM_RANGE, 
				row() + Rules.NORM_RANGE);
	} 

}
