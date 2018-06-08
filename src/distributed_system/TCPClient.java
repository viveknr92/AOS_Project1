package distributed_system;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements Runnable {
	private Socket socket;
	private int port;
	private String address;
	private Thread client;
	private InputStream inputstream;
	private OutputStream outputstream;
	public TCPClient(String address, int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
		client = new Thread(this, "client");
		client.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			socket = new Socket(address, port); // Creates a stream socket and connects it to the specified port number on the named host
			outputstream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputstream);
			writer.println("this is from the client");
			writer.flush();
			//System.out.println("outputstream"+ inputstream.available());
			//writer.close();
			//outputstream.close();
			//System.out.println("outputstream"+ inputstream.available());
			
			BufferedReader reader;
			//socket = new Socket(address, port);
			System.out.println(socket.isClosed());
			System.out.println(socket.isConnected());
			inputstream = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputstream));
			System.out.println("Server says:" + reader.readLine());
			
			System.out.println("outputstream"+ inputstream.available());
			reader.close();
			inputstream.close();
			System.out.println("inputstream" + socket.isClosed());
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		TCPClient tcpclient = new TCPClient("localhost", 9999);
	}
	
}
