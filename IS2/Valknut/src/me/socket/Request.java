package me.socket;

import java.io.Serializable;

public class Request implements Serializable{

    public enum RequestType{
        MESSAGE, CLOSING;
    }

    private RequestType rt;
    private int id;
    Object[] parameter;

    public Request(RequestType rt, int id){
        this.id = id;
        this.rt = rt;
        parameter = new Object[10];
    }

    public void setRequestType(RequestType rt){
        this.rt = rt;
    }

    public void addParameter(Object obj){
        parameter[0] = obj;
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
