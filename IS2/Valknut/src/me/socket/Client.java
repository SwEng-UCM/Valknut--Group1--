package me.socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import me.view.ViewUtils;

public class Client extends UserObject{
    private Socket me; // An instance of the client itself to do some operations like closing
    private static final int PORT = 5000;
    private String ip;

    public Client(String ip, MultiplayerManager test){
        super(2, test);
        this.ip = ip;
    }

    private void initStreams() throws IOException{
        System.out.println("Trying to stablish communication");
        out = new ObjectOutputStream(me.getOutputStream());
        System.out.println("Half way");
        in = new ObjectInputStream(me.getInputStream());
        System.out.println("Communication Stablished");
    }

    @Override
    public void set() {
        try {
            me = new Socket();
            System.out.println("New socket");

            me.setReuseAddress(true);
            System.out.println("Trying to connect to " + ip + ":" + PORT);

            me.connect(new InetSocketAddress(ip, PORT), 2000);
            System.out.println("Succesfully connected");

            initStreams();
            startListener();
            System.out.println("Listener started");

            ViewUtils.showErrorMsg("Connected!!");
            test.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect(){
        try {
            closeStream();
            me.close();
            if(me.isClosed())
                System.out.println("Client Closed");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

}
