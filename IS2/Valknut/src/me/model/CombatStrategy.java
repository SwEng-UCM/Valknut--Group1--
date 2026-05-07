/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model;

import java.util.List;

//Interface for the AH (autonomous hero) to attack implementing strategy pattern
public interface CombatStrategy {
    public int execute(Element element, List<Enemy> characters, int tarjet);
}
