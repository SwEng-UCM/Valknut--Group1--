package me.socket;

import me.model.CombatOption;

public class Request {

    public enum RequestType{
        COMBAT, STORY, ERROR;
    }

    private CombatOption co;
    private RequestType rt;
    Object[] parameter;

    public Request(RequestType rt){
        this.rt = rt;
        parameter = new Object[2];
    }

    public void setRequestType(RequestType rt){
        this.rt = rt;
    }

    public void setCombatOption(CombatOption co){
        this.co = co;
    }

    public void addParameter(Object obj){
        parameter[0] = obj;
    }

    public RequestType getRT(){
        return rt;
    }

    public CombatOption getCO(){
        return co;
    }

    public Object[] getParameters(){
        return parameter;
    }

}
