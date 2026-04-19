package me.socket;

import javax.swing.text.View;
import me.control.Controller;
import me.model.CombatOption;
import me.model.items.Item;
import me.socket.Request.RequestType;
import me.view.Messages;
import me.view.ViewUtils;

public class Dispatcher {
    
    public void dispatch(Request rq, Controller ctrl){
        RequestType rt = rq.getRT();
        switch (rt) {
            case COMBAT -> {
                dispatchCombatRequest(rq, ctrl);
            }
            case STORY -> {
            }
            case ERROR ->{
                Object o = rq.parameter[0];
                ViewUtils.showErrorMsg(Messages.WRONG_REQUEST + (o == null ? "Null value" : o));
            }
            default -> {
            }
        }
    }

    private void dispatchCombatRequest(Request rq, Controller ctrl){
        CombatOption co = rq.getCO();
        Object[] parameter = rq.getParameters();
        switch (co) {
            case ATTACK-> {
                ctrl.action(co, (int) parameter[0], null);
            }
            case DEFEND-> {
                ctrl.action(co, 0, null);
            }
            case RUN-> {
                ctrl.action(co, 0, null);
            }
            case USE_ITEM-> {
                ctrl.action(co, 0, (Item) parameter[0]);
            }
            case STATS-> {
                ctrl.action(co, 0, null);
            }
            default ->{}
        }
    }
}
