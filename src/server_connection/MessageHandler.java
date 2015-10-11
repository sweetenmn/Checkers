package server_connection;

import checkersGUI.Board;

public class MessageHandler {
	
	Board board;
	private static final int MIN_LENGTH = 3;
	
	public MessageHandler(Board board){
		this.board = board;
	}
	
	public void handleMessage(String msg){
		if (msg.length() > MIN_LENGTH){
			String[] values = msg.split(":");
			String messageType = values[0];
			int xOrigin = Integer.valueOf(values[1]);
			int yOrigin = Integer.valueOf(values[2]);
			int xDestin = Integer.valueOf(values[3]);
			int yDestin = Integer.valueOf(values[4]);
			board.setMovement(xOrigin, yOrigin, xDestin, yDestin);
			board.movePiece();
		} 		
	}

}