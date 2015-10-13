package server_connection;

import helpers.PlayerID;
import checkersGUI.Board;

public class MessageHandler {
	
	//will replace the appropriate methods in Board
	//need to handle non-move messages like updating turn information (not if jumping)
	//and players' names
	//talks to Movement class
	
	Board board;
	String setUpMessage;
	String playerOneName;
	String playerTwoName;
	private static final int MIN_LENGTH = 3;
	
	public MessageHandler(Board board){
		this.board = board;
		setUpMessage = "";
	}
	public void handleMessage(String msg){
		System.out.println("handling... " + msg);
		if (msg.length() > MIN_LENGTH){
			String[] values = msg.split(":");
			String messageType = values[0];
			if (messageType.equals("PLAY")){
				setUpMessage = generateSetUpMessage(values[1], values[2]);
			} else if (messageType.equals("MOVE") && msg.length() > 8){
				board.setMovement(toInt(values[1]), toInt(values[2]),
						toInt(values[3]), toInt(values[4]));
				board.movePiece();
			}
		} 		
	}
	
	public String getMovementMessage(){
		return ("MOVE:" + board.getMove());
	}
	
	public String generateSetUpMessage(String player, String otherPlayer){
		System.out.print("generating... " + player + " " + otherPlayer);
		if (player.compareTo(otherPlayer) > 0){
			board.createPlayer(PlayerID.RED);
			playerOneName = player;
			playerTwoName = otherPlayer;
			return ("PLAY:" + player + ":" + otherPlayer);
		} else {
			board.createPlayer(PlayerID.BLACK);
			playerOneName = otherPlayer;
			playerTwoName = player;
			return ("PLAY:" + otherPlayer + ":" + otherPlayer);
		}
	}
	
	public String getSetUp(){
		return setUpMessage;
	}
	
	public Integer toInt(String s){
		return Integer.valueOf(s);
	}
	
	public String getPlayerOneName(){
		return playerOneName;
	}
	public String getPlayerTwoName(){
		return playerTwoName;
	}

}
