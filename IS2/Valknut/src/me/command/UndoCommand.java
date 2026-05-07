/**
 * 
 * @author Hana Perocevic
 * 
 * ai-assisted: yes
 * 
 */


package me.command;

import me.model.Combat;

public class UndoCommand implements Command {

    private final Combat combat;
    private final Command previousCommand;

    public UndoCommand(Combat combat,Command lastCommand) {
        this.combat = combat;
        this.previousCommand = lastCommand;
    }

    @Override
    
    public boolean execute(StringBuilder sb) {
        if (previousCommand == null || !previousCommand.canUndo()) {
            sb.append("There is no action to undo.\n");
            return false;
        }

        return previousCommand.undo();
    }

    @Override
    public boolean undo() {
        // Undo command itself cannot be undone
        return false;
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean advancesTurn() {
        // Undo should NOT advance turn
        return false;
        
    }
}