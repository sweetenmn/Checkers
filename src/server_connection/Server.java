package server_connection;
import checkersGUI.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class Server {
	private String host;
	private MessageHandler messageHandler;

	public static void main(String[] args, MessageHandler handler, String string) throws IOException {
		Server s = new Server(Integer.parseInt(args[0]), handler, string);
		s.listen();
	}
	
	private ServerSocket accepter;

	public Server(int port, MessageHandler handler, String host) throws IOException {
		messageHandler = handler;
		this.host = host;
		accepter = new ServerSocket(port);
	}

	public void listen() throws IOException {
		for (;;) {
			Socket s = accepter.accept();
			SocketEchoThread echoer = new SocketEchoThread(s);
			echoer.start();
		}
	}
	
	private class SocketEchoThread extends Thread {
	    private Socket socket;
	    
	    public SocketEchoThread(Socket socket) {
	        this.socket = socket;
	    }

	    public void run() {
	    	try {
	    		PrintWriter writer = new PrintWriter(socket.getOutputStream());
	    		String msg = getMessage();
	    		if (!host.equals("localhost")){
		    		Platform.runLater(() -> messageHandler.handleMessage(msg));
	    		}
	    		echoAndClose(writer, msg);
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    	}
	    }
	    
	    private void echoAndClose(PrintWriter writer, String msg) throws IOException {
            writer.print(msg);
            writer.flush();
            socket.close();	    	
	    }
	    
	    private String getMessage() throws IOException {
            BufferedReader responses = 
            		new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (!responses.ready()){}
            while (responses.ready()) {
                sb.append(responses.readLine());
            }
	    	return sb.toString();
	    }
	}
}

