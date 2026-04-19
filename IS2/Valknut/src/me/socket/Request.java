package me.socket;

import me.model.CombatOption;

public class Request {

    public enum RequestType{
        COMBAT, STORY;
    }

    private CombatOption co;
    private final RequestType rt;
    Object[] parameters;

    public Request(RequestType rt){
        this.rt = rt;
    }

    public void setCombatOption(){

    }

    public void addParameter(){

    }

    public RequestType getRT(){
        return rt;
    }

    public CombatOption getCO(){
        return co;
    }

    public Object[] getParameters(){
        return parameters;
    }

}
