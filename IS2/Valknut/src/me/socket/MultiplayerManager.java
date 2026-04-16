package me.socket;

import java.io.*;
import java.net.*;

public class MultiplayerManager {

    private final int PORT = 9999;
    private Socket client;
    private ServerSocket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int id;

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
        if(id == 1){ //if I am the server I manage the Client request

        }
        else{ //if I am the client I manage the Server update

        }
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
