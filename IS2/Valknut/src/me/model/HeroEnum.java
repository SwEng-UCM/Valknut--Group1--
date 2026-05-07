/**
 * 
 * @author Helio Vega Fernández AI assisted: No
 * @author Pablo Cabello Canales AI assisted: No
 * 
 */

package me.model;

public enum HeroEnum {
	//five possible heroes
	GERSEMI, VALI, JORUNN, VIGGO, MAGNI;

	public static HeroEnum randomEnum(){
		double x = Math.random();
		if(x < 0.20)
			return GERSEMI;
		else if(x < 0.40)
			return VALI;
		else if(x < 0.60)
			return JORUNN;
		else if(x < 0.80)
			return VIGGO;
		else
			return MAGNI;
	}

}
