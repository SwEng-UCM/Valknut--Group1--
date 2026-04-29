package me.socket;

public class Validator {
    
    public void validate(Request rq){
        Request.RequestType rt = rq.getRT();
        switch (rt) {
            case CHARACTERSELECT-> {
            }
            default -> throw new AssertionError();
        }
    }

}
