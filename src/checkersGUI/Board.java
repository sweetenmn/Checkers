package checkersGUI;

import java.util.ArrayList;
import game.Cell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {
	GridPane checkerboard;
	ArrayList<Piece> pieces;
	ArrayList<Square> squares;
	Cell lastPieceClicked;
	Cell lastSquareClicked;
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
		addChips(0, 0, 8);
		addChips(1, 0, 3);
		addChips(2, 5, 8);
	}
	
	public void addChips(int state, int rowStart, int rowEnd){
		for (int column = 0; column < 8; column++){
			for (int row = rowStart; row < rowEnd; row++){
				Pane pane = new Pane();
				Piece piece = new Piece(state, pane, column, row);
				Cell cell = new Cell(state, pane, column, row);
				cell.addToBoard(this);
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
	
	public void setLastPieceClicked(Cell cell){
		lastPieceClicked = cell;
	}
	
	public void setLastSquareClicked(Cell cell){
		lastSquareClicked= cell;
	}
/*
	@FXML
	public void movePiece(){
		boolean stopping = false;
		boolean done = false;
		for (int i = pieces.size() - 1; i >= 0 && !stopping; i--){
			Piece oldPiece = pieces.get(i);
			if (oldPiece.getColumn() == oldX && oldPiece.getRow() == oldY){
				for (int j = squares.size() - 1; j >= 0 && !done; j--){
					Square s = squares.get(j);
					if (s.getColumn() == newX && s.getRow() == newY){
						oldPiece.clear();
						s.clear();
						Pane pane = new Pane();
						Piece piece = new Piece(oldPiece.getState(), pane, newX, newY);
						piece.addToBoard();
						Platform.runLater(() -> checkerboard.add(pane, newX, newY));
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
	}*/
	
	public void movePiece(){
		lastPieceClicked.eraseFrom(checkerboard);
		lastSquareClicked.eraseFrom(checkerboard);
		
	}
	
	//Once we get interface going, have a method that will swap locations
	//of the two images.
	//might have a helper for actually doing it?
	
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
			String[] values = msg.split(":");
			oldX = Integer.valueOf(values[0]);
			oldY = Integer.valueOf(values[1]);
			newX = Integer.valueOf(values[2]);
			newY = Integer.valueOf(values[3]);
		//	Platform.runLater(() -> movePiece());
		} 		
	}
}
