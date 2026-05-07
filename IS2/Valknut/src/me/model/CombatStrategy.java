/**
 * 
 * @author Helio Vega Fernández
 * 
 */
package me.model;

import java.util.List;

public interface CombatStrategy {
    public int execute(Element element, List<Enemy> characters, int tarjet);
}
