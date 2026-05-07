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

            // startBroadcasting(); // Starts saying to the network, "Hey, I'm here!"
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

    // public void startBroadcasting() {
    //     new Thread(() -> { // A Thread since it is done constantly
    //         // I'm not sure if this Thread fall asleep at some point, I could do it different so
    //         //when a client is connected, this Thread ends
    //         try {
    //             socket = new DatagramSocket(); // This is the endpoit for sending and receiving 
    //             // packages in the network UDP style 
    //             socket.setBroadcast(true); // To start broadcasting "Hey, I'm here!"
    //             String mensaje = "SERVER_ALIVE"; // This is the "Hey, I'm here!", actually
    //             byte[] buffer = mensaje.getBytes(); // UDP communication works via Bytes, so byte[] is needed
    //             DatagramPacket packet = new DatagramPacket(
    //                 buffer, buffer.length, // The message, the length, the mask (or directly the IP) and
    //                 // the port where to send the message
    //                 InetAddress.getByName(getWiFiIP()), 8888
    //             );
    //             // DatagramPacket packet = new DatagramPacket(
    //             //     buffer, buffer.length, 
    //             //     InetAddress.getByName(getWiFiIP()), 8888
    //             // ); This is another option still in progress

    //             while (true) { // Constant sending
    //                 socket.send(packet);
    //                 Thread.sleep(2000); // To not saturate the machine
    //             }
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }finally{
    //             socket.close(); // finally, when the Thread is close, so it's the DatagramSocket
    //         }
    //     }).start();
    // }

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
