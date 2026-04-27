package me.socket;

public class Dispatcher {
    
    public void dispatch(Request rq, MultiplayerManager test){
        Request.RequestType rt = rq.getRT();
        switch(rt){
            case MESSAGE ->{dispatchMessage(rq, test);}
            case CLOSING ->{dispatchClose(rq, test);}
            default ->{}
        }
    }

    public void dispatchMessage(Request rq, MultiplayerManager test){
        String message = (String) rq.getParameters()[0];
        if(test.getUser().getId() == rq.getId())
            message = "You: " + message;
        else if(rq.getId() == 1)
            message = "Server: " + message;
        else
            message = "Client: " + message;
        test.write(message);
    }

    public void dispatchClose(Request rq, MultiplayerManager test){
        if(test.getUser().getId() == rq.getId()){}
        else
            test.killUser();
    }
}
