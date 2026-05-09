/**
 * 
 * @author Helio Vega Fernández
 * AI assisted
 * 
 */
package me.socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import me.view.ViewUtils;

public class Server extends UserObject{
    
    private Socket client; // The client we are going to stablish the DataInput/OutputStreams
    private ServerSocket me; // A reference to me to execute some methods like closing socket
    private DatagramSocket socket; // Not really needed as a attribute, but for udp communication
    private static final int PORT = 5000; // If we want a fixed port, like 5000 in this case

    public Server(String name, int life, int max_life, String surname, MultiplayerManager test){
        super(name, life,  max_life, surname, 1, test); // We could think on an interface to limit the user access to the controller
    }

    @Override
    public void set(){ // Steps for initializing the server
        try {
            me = new ServerSocket(PORT); // Creates the class that will host the server features
            System.out.println("New ServerSocket created");

            System.out.println("Server Arranged"); // Status control line

            client = me.accept(); // Waiting for a Socket connection. This methods return a new Socket() indeed 
            System.out.println("Server Accepted the connection with: " + client.getInetAddress());

            initStreams(); // Simple method to initialize the DataInput/OutputStreams
            startListener(); // A thread for listening the other side deliveries
            System.out.println("Listener started");

            ViewUtils.showErrorMsg("Connected!!");
            test.start();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void initStreams() throws IOException{ // The server needs to do this here because it has 
    // the attribute Client and the superclass does not
        System.out.println("Trying to stablish communication");
        out = new ObjectOutputStream(client.getOutputStream()); // The Socket Class is the source
        System.out.println("Half way");
        in = new ObjectInputStream(client.getInputStream());
        System.out.println("Communication Stablished");
    }

    @Override
    public void disconnect(){ // very important
        try {
            if(client != null){
                Request rq = new Request(Request.RequestType.CLOSING, 1); // Closing request
                send(rq); // say the client to disconnect since there will be no server
                System.out.println("Request for closing sent");
                client.close();
                if(client.isClosed())
                    System.out.println("Client closed");
                client = null;
            }
            me.close(); // close the ServerSocket
            System.out.println("Server Closed"); // Status control line
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}   
