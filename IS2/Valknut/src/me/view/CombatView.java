/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.view;

import me.model.Character;
import me.model.CombatOption;
import me.model.Hero;
import me.model.items.Item;

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
        while(co == null){
            printLine(Messages.ENTER_VV + Messages.NEW_LINE);
            print(turnToString(c));
            aux = readPrompt();
            co = CombatOption.parseCommand(aux);
            printLine("");
        }

        return co;
    }

    public void turnHeader(Character c){
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.NEW_LINE).append(c.name().toUpperCase()).append("'s TURN...").append(Messages.NEW_LINE);
        print(sb.toString());
    }

    public String turnToString(Character c){
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.NEW_LINE);
        sb.append(CombatOption.display()).append(Messages.NEW_LINE).append("Option (ex: item): ");

        return sb.toString();
    }

    public int selectTarject(String tarjets, int maxRange){

        print(tarjets);
        int i = parseIntInRange(1, maxRange);

        return i;
    }

    public Item selectItem(String s, Hero h){ 
        printLine(s);
        if(!h.getInventory().isEmpty()){
            boolean valid = false;
            Item i = null;
            while(!valid){
                print("Select (item full name) (e to exit): ");
                String item = sc.nextLine();
                //i = h.getInventory().contains(item);
                valid = (i != null) || (item.equals("e"));
                if(!valid){
                    printLine(Messages.INVALID_ITEM + item);
                    printLine("");
                    printLine(s);
                }
            }
            return i;
        }
        return null;
    }
}
