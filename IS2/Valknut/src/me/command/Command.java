package me.command;

/**
 * Command interface used to encapsulate a combat action.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @return true if the action was completed successfully, false otherwise
     */
    boolean execute(StringBuilder sb);

    /**
     * Tells the controller whether this command should advance the turn.
     *
     * @return true if the turn should advance, false otherwise
     */
    boolean advancesTurn();

    default boolean undo() {
        return false;
    }

    default boolean canUndo() {
        return false;
    }
}