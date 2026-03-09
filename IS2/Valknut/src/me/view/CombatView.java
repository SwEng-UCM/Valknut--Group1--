package me.view;

import me.model.Character;
import me.model.CombatOption;

public class CombatView extends ConsoleIO{

    private static CombatView cv;

    private CombatView(){
        super();
    }

    public static CombatView getInstance(){

        if(cv == null){
            cv = new CombatView();
        }

        return cv;
    }

    public CombatOption selectAction(Character c){
        CombatOption co;
        print(turnToString(c));
        String aux = readPrompt();
        printLine("");
        co = CombatOption.parseCommand(aux);
        while(co == null || co == CombatOption.WAIT){
            printLine(Messages.ENTER_VV + Messages.NEW_LINE);
            print(turnToString(c));
            aux = readPrompt();
            co = CombatOption.parseCommand(aux);
            printLine("");
        }

        return co;
    }

    public String turnToString(Character c){
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.NEW_LINE);
        sb.append(c.name().toUpperCase()).append("'s TURN...").append(Messages.NEW_LINE).append(Messages.NEW_LINE);
        sb.append(CombatOption.display()).append(Messages.NEW_LINE).append("Option: ");

        return sb.toString();
    }

    public int selectTarject(String tarjets, int maxRange){

        print(tarjets);
        int i = parseIntInRange(1, maxRange);

        return i;
    }

    public void selectItem(String s){ 
        printLine(s);
    }
}
