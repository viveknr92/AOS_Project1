import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer{

	ServerSocket listener = null;
	// This node listens as a Server for the clients requests 
	Socket socket = null;
	int serverPort;
	private MapProtocol mapObject;
	
	public TCPServer(MapProtocol mapObject) {
		
		this.mapObject = mapObject; //Global mapObject
		// Get the port number on which this node should listen 
		serverPort = mapObject.nodes.get(mapObject.id).port;
		try {
			listener = new ServerSocket(serverPort);
		} 
		catch(BindException e) {
			e.printStackTrace();
			System.out.println("Port not available");
			System.exit(1);
		}
		catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Connection Broken");
			System.exit(1);
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void listenforinput(){
		// Start server on this node's assigned port
		try {
			while (true) {
				try {
					socket = listener.accept();
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Connection Broken");
					System.exit(1);
				}
				// For every client request start a new thread 
				new ReceiveThread(socket,mapObject).start();
			}
		}
		finally {
			try {
				listener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}