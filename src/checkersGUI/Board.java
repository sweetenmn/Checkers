package checkersGUI;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {
	GridPane checkerboard;
	ArrayList<Piece> pieces;
	ArrayList<Square> squares;
	
	public Board(GridPane grid){
		checkerboard = grid;
		pieces = new ArrayList<Piece>();
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
	public void move(){
		boolean stopping = false;
		for (int i = pieces.size() - 1; i >= 0 && !stopping; i--){
			Piece oldPiece = pieces.get(i);
			if (oldPiece.isClicked()){
				for (Square s: squares){
					if (oldPiece.isLegal(s) && s.isClicked()){
						oldPiece.clear();
						s.clear();
						Pane pane = new Pane();
						Piece piece = new Piece(oldPiece.getPlayer(), pane, s.getColumn(), s.getRow());
						piece.addToBoard();
						checkerboard.add(pane, s.getColumn(), s.getRow());
						pieces.remove(i);
						pieces.add(piece);
						stopping = true;
					}
				}
				
			}
		}
		
		
	}
	
	public void movePiece(int xOrigin, int yOrigin, int xDestin, int yDestin){
		boolean stopping = false;
		for (int i = pieces.size() - 1; i >= 0 && !stopping; i--){
			Piece oldPiece = pieces.get(i);
			if (oldPiece.getColumn() == xOrigin && oldPiece.getRow() == yOrigin){
				for (Square s: squares){
					if (oldPiece.isLegal(s) && s.getColumn() == xDestin && s.getRow() == yDestin){
						oldPiece.clear();
						s.clear();
						Pane pane = new Pane();
						Piece piece = new Piece(oldPiece.getPlayer(), pane, xDestin, yDestin);
						piece.addToBoard();
						checkerboard.add(pane, xDestin, yDestin);
						pieces.remove(i);
						pieces.add(piece);
						stopping = true;
					}
				}
				
			}
		}
		
	}
	
	public String getMove(){
		String oldX = "";
		String oldY = "";
		String newX = "";
		String newY = "";
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
		return oldX + ":" + oldY + ":" + newX + ":" + newY;
	}
	
	private String getString(int x){
		return Integer.toString(x);
	}
	public void setMove(String msg){
		String[] values = msg.split(":");
		int xOrg = Integer.getInteger(values[0]);
		int yOrg = Integer.getInteger(values[1]);
		int xDest = Integer.getInteger(values[2]);
		int yDest = Integer.getInteger(values[3]);
		movePiece(xOrg, yOrg, xDest, yDest);
		
	}
	
			
	
	
	
	
}
