package helpers;

public class Coordinate {
	private int column;
	private int row;
	
	public Coordinate(int x, int y){
		column = x;
		row = y;
	}
	
	public int column(){return column;}
	public int row(){return row;}

}