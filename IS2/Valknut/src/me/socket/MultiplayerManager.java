package me.socket;

import java.io.*;
import java.net.*;
import me.control.Controller;

public class MultiplayerManager {

    private final int PORT = 9999;
    private Socket client;
    private ServerSocket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int id;
    private final Dispatcher dispatcher;
    private final Controller ctrl;
    private final Validator validator;
    private Listener listener;

    public MultiplayerManager(Controller ctrl){
        this.ctrl = ctrl;
        dispatcher = new Dispatcher();
        validator = new Validator();
    }

    public class Listener implements Runnable{
        private volatile boolean running = true;

        @Override
        public void run() {
            try {
                while(running){
                    Object obj = in.readObject();
                    convertInfo(obj);
                    Thread.sleep(1000); // causes a pause of 1 second between interactions to not exceed demand limits
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stop(){
            running = false;
        }

    }

    private void startListener(){
        listener = new Listener();
        Thread thread = new Thread(listener);
        thread.start();
    }

    private void convertInfo(Object obj){
        Request rq = (Request) obj;
        if(id == 1){ // if I am the server I validate the request and then send it again to the client to treate it and treate it my self
            if(!validateRequest(rq))
                rq.setRequestType(Request.RequestType.ERROR);
            send(rq);
            treatRequest(rq);
        }
        else{ //if I am the client I manage the Server update
            treatRequest(rq);
        }
    }

    private boolean validateRequest(Request rq){
        return validator.validate(rq, ctrl.getTurn());
    }

    private void treatRequest(Request rq){
        dispatcher.dispatch(rq, ctrl);
    }

    public void connectClient(String ip) throws IOException{
        client = new Socket(ip, PORT);
        id = 2;
        initStreams(); //stablish the communication canal
        startListener(); //create the independent thread for listening other side communication events
    }

    public void connectServer() throws IOException{
        server = new ServerSocket(PORT);
        id = 1;
        try{
            client = server.accept();
        }catch(IOException e){
            // ViewUtils.showErrorMsg("Server Aborted");
        }
        initStreams(); //stablish the communication canal
        startListener(); //create the independent thread for listening other side communication events
    }

    public void endServer() throws IOException{
            server.close();
    }

    public void endClient() throws IOException{
            client.close();
    }

    public void endCommunication(){
        try {
            listener.stop();
            endServer();
            endClient();
        }
        catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }

    private void initStreams() throws IOException{
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
    }

    public void send(Object o) {
        try {
            out.writeObject(o);
            out.flush();
            out.reset(); 
        } 
        catch (IOException e) { 
            System.err.println(e.getMessage()); 
        }
    }   

    public int id(){
        return id;
    }
}
