package server_connection;

import helpers.PlayerID;
import checkersGUI.Board;

public class MessageHandler {
	
	//will replace the appropriate methods in Board
	//need to handle non-move messages like updating turn information (not if jumping)
	//and players' names
	//talks to Movement class
	
	Board board;
	private static final int MIN_LENGTH = 8;
	
	public MessageHandler(Board board){
		this.board = board;
	}
	public void handleMessage(String msg){
		System.out.println("handling... " + msg);
		if (msg.length() > MIN_LENGTH){
			String[] values = msg.split(":");
			String messageType = values[0];
			if (messageType.equals("PLAY")){
				setUpPlayer(values[1], values[2]);
			} else if (messageType.equals("MOVE") && msg.length() > 8){
				board.setMovement(toInt(values[1]), toInt(values[2]),
						toInt(values[3]), toInt(values[4]));
				board.movePiece();
			}
		} 		
	}
	
	public void incCounter(){
		board.getCounter().increment();
	}
	
	public String getMovementMessage(){
		return ("MOVE:" + board.getMove());
	}
	public void setUpPlayer(String player, String otherPlayer){
		if (player.equals(board.getName())){
			System.out.println("YOU ARE BLACK");
			board.createPlayer(PlayerID.BLACK);
		} else if (otherPlayer.equals(board.getName())) {
			System.out.println("YOU ARE RED");
			board.createPlayer(PlayerID.RED);
		}
	}
	
	public String generateSetUpMessage(String player, String otherPlayer){
		if(player.compareTo(otherPlayer) < 0){
			System.out.print("generating... " + player + " " + otherPlayer);
			return "PLAY:" + player + ":" + otherPlayer;
		} else {
			System.out.print("generating... " + otherPlayer + " " + player);
			return ("PLAY:" + otherPlayer + ":" + player);
		}
	}
	
	public Integer toInt(String s){
		return Integer.valueOf(s);
	}
	

}
