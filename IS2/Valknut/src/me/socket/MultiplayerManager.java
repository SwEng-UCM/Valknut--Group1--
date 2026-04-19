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

    public MultiplayerManager(Controller ctrl){
        this.ctrl = ctrl;
        dispatcher = new Dispatcher();
    }

    public class Listener implements Runnable{
        @Override
        public void run() {
            try {
                while(true){
                    Object obj = in.readObject();
                    convertInfo(obj);
                }
            } catch (Exception e) {

            }
        }

    }

    private void startListener(){
        Listener listener = new Listener();
        Thread thread = new Thread(listener);
        thread.start();
    }

    private void convertInfo(Object obj){
        Request rq = (Request) obj;
        if(id == 1){ // if I am the server I validate the request and then send it again to the client to treate it and treate it my self
            if(validateRequest(rq)){
                send(rq);
                treatRequest(rq);
            }
        }
        else{ //if I am the client I manage the Server update
            treatRequest(rq);
        }
    }

    private boolean validateRequest(Request obj){
        return false;
    }

    private void treatRequest(Request rq){
        dispatcher.Dispatch(rq, ctrl);
    }

    public void connectClient(String s){
        try{
            client = new Socket(s, PORT);
            id = 2;
            initStreams(); //stablish the communication canal
            startListener(); //create the independent thread for listening other side communication events
        }
        catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }

    public void connectServer(){
        try{
            server = new ServerSocket(PORT);
            id = 1;
            client = server.accept();
            initStreams(); //stablish the communication canal
            startListener(); //create the independent thread for listening other side communication events
        }
        catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }

    public void endCommunication(){
        try {
            server.close();
            client.close();
        }
        catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }

    private void initStreams(){
        try{
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        }
        catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
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
}
