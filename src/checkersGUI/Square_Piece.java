package checkersGUI;

import javafx.fxml.FXML;

public interface Square_Piece {

	public int getColumn();
	
	public int getRow();
	
	@FXML
	public void handleClick();
	
	public boolean isClicked();
	
	public void clear();
}
