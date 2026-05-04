package me.command;

import me.model.Combat;
import me.view.CombatView;

public class UndoCommand implements Command{
	
	private final Combat combat;
    private final CombatView combatView;
	private final Command previousCommand;
	
	public UndoCommand(Combat combat, CombatView combatView, Command lastCommand) {
		this.combat = combat;
		this.combatView = combatView;
		this.previousCommand = lastCommand;
	}

	@Override
	public boolean execute(StringBuilder sb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean advancesTurn() {
		// TODO Auto-generated method stub
		return false;
	}
}
