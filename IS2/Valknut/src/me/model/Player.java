/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * 
 */
package me.model;

import me.socket.Request;

//to force heroes to apply this method. Used in AH and multiplayer
public interface Player {
    public String does(Request rq);
}
