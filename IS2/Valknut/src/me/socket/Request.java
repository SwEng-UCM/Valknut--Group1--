package me.socket;

import java.io.Serializable;

public class Request implements Serializable{

    public enum RequestType{
        MESSAGE, CLOSING, CHARACTERSELECT, COMBATOPTION;
    }

    private RequestType rt;
    private int id;
    private Object[] parameter;
    private int size = 0;

    public Request(RequestType rt, int id){
        this.id = id;
        this.rt = rt;
        parameter = new Object[10];
    }

    public void setRequestType(RequestType rt){
        this.rt = rt;
    }

    public void addParameter(Object obj){
        if(size < 10)
            parameter[size++] = obj;
        else
            System.err.println("FULL CAPACITY OF THE PARAMETERS LIST");
    }

    public RequestType getRT(){
        return rt;
    }

    public int getId(){
        return id;
    }

    public Object[] getParameters(){
        return parameter;
    }

}
