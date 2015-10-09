package checkersGUI;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {
	GridPane checkerboard;
	ArrayList<Piece> pieces;
	ArrayList<Square> squares;
	int oldX;
	int oldY;
	int newX;
	int newY;
	
	public Board(GridPane grid){
		checkerboard = grid;
		pieces = new ArrayList<Piece>();
		//get legal squares
		squares = new ArrayList<Square>();
	}
	
	public void setUp(){
		addSquares();
		addChips(1, 0);
		addChips(2, 5);
	}
	
	public void addChips(int player, int rowIndex){
		for (int column = 0; column < 8; column++){
			for (int row = rowIndex; row < rowIndex + 3; row++){
				Pane pane = new Pane();
				Piece piece = new Piece(player, pane, column, row);
				piece.addToBoard();
				if (row % 2 != 0){
					if (column % 2 != 0){
						checkerboard.add(pane, column, row);
						pieces.add(piece);
					}
				} else {
					if (column % 2 == 0){
						checkerboard.add(pane, column, row);
						pieces.add(piece);
					}
				}
			}
			
		}
		
	}
	
	@FXML
	public void addSquares(){
		for (int column = 0; column < 8; column++){
			for (int row = 0; row < 8; row++){
				Pane pane = new Pane();
				Square square = new Square(pane, column, row);
				pane.setOnMouseClicked(k -> square.click());
				if (row % 2 != 0){
					if (column % 2 != 0){
						checkerboard.add(pane, column, row);
						squares.add(square);
					}
				} else {
					if (column % 2 == 0){
						checkerboard.add(pane, column, row);
						squares.add(square);
						
					}
				}
			}
			
		}
	}

	@FXML
	public void movePiece(){
		boolean stopping = false;
		boolean done = false;
		//change to pieces of whoever's turn it is and change to looking
		//at most recent clicks-- intermediary class that goes through and
		//unclicks all the other pieces every third click or something
		for (int i = pieces.size() - 1; i >= 0 && !stopping; i--){
			Piece oldPiece = pieces.get(i);
			if (oldPiece.getColumn() == oldX && oldPiece.getRow() == oldY){
				//change to array of legal squares
				for (int j = squares.size() - 1; j >= 0 && !done; j--){
					Square s = squares.get(j);
					if (s.getColumn() == newX && s.getRow() == newY){
						oldPiece.clear();
						s.clear();
						Pane pane = new Pane();
						Piece piece = new Piece(oldPiece.getPlayer(), pane, newX, newY);
						piece.addToBoard();
						checkerboard.add(pane, newX, newY);
						pieces.remove(i);
						pieces.add(piece);
						done=true;
						stopping=true;
					} else {
						s.clear();
					}
					
				}
				
			} else {
				oldPiece.unclick();
			}
		}
		
	}
	
	//Once we get interface going, have a method that will swap locations
	//of the two images.
	//might have a helper for actually doing it?
	
	public void swap(GridPane grid, Pane x, Pane y){
		for (Node p: grid.getChildren()){
			int originX = GridPane.getColumnIndex(x);
		}
	 
		
	}
	
	public String getMove(){
		String oldX = "";
		String oldY = "";
		String newX = "";
		String newY = "";
		//no
		//this is why it moves the wrong things, need better way to talk about
		//when we will be moving a piece
		for (int i = pieces.size() - 1; i >= 0; i--){
			Piece oldPiece = pieces.get(i);
			if (oldPiece.isClicked()){
				for (Square s: squares){
					if (oldPiece.isLegal(s) && s.isClicked()){
						oldX = getString(oldPiece.getColumn());
						oldY = getString(oldPiece.getRow());
						newX = getString(s.getColumn());
						newY = getString(s.getRow());
					}
				}
				
			}
		}
		return (oldX + ":" + oldY + ":" + newX + ":" + newY).trim();
	}
	
	private String getString(int x){
		return Integer.toString(x);
	
	}
	
	public void setMove(String msg){
		if (msg.length() > 3){
			System.out.println("old x: " + oldX);
			String[] values = msg.split(":");
			oldX = Integer.valueOf(values[0]);
			oldY = Integer.valueOf(values[1]);
			newX = Integer.valueOf(values[2]);
			newY = Integer.valueOf(values[3]);
			System.out.println("new oldX: " + oldX);
			Platform.runLater(() -> movePiece());
		} 
		
	}
	
			
	
	
	
	
}
