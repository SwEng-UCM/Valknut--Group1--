/**
 * 
 * @author Helio Vega Fernández  AI assisted: No
 * 
 */
package me;

import me.control.Controller;

public class Main {

	private final static Controller controller = Controller.getInstance();
	public static void main(String[] args) {
		controller.run();
	}
	
	
	
}
