package me.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import me.model.Hero;

public abstract class UserObject extends Hero implements User {

    protected MultiplayerManager test;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    Listener listener;

    public enum UserType{
        SERVER, CLIENT;
    }

    public UserObject(String name, int life, int max_life, String surname, int id, MultiplayerManager test){
        super(name, life, max_life, surname, id);
        this.test = test;
    }

    @Override
    public String does(Request rq){
        send(rq);
        return null;
    }

    @Override
    public int getId(){
        return id;
    }

    public class Listener implements Runnable{

        @Override
        public void run() {
            try {
                Object obj;
                while((obj = in.readObject()) != null){
                    convertInfo(obj);
                    Thread.sleep(1000); // causes a pause of 1 second between interactions to not exceed demand limits
                }
                
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }finally{
                
            }
        }
    }

    private void convertInfo(Object obj){
        Request rq = (Request) obj;
        if(id == 1){ // if I am the server I validate the request and then send it again to the client to treate it and treate it my self
            send(rq);
            treatRequest(rq);
        }
        else{ //if I am the client I manage the Server update
            treatRequest(rq);
        }
    }

    private void treatRequest(Request rq){
        test.treatRequest(rq);
    }

    protected void startListener(){
        listener = new Listener();
        Thread thread = new Thread(listener);
        thread.start();
    }

    public abstract  void set();

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

    public String getWiFiIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                String name = iface.getDisplayName().toLowerCase();
                
                if (iface.isLoopback() || !iface.isUp() || !name.contains("wi-fi")) continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    
                    if (addr instanceof java.net.Inet4Address) {
                        System.out.println("[UserObject:113] Interfaz: " + iface.getDisplayName() + " IP: " + addr.getHostAddress());
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void disconnect();

    protected void closeStream(){
        if(out != null && in != null){
            try {
                out.close();
                in.close();
                System.out.println("[UserObject:131] Communication Closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
