/**
 * 
 * @author Helio Vega Fernández
 * @author Pablo Cabello Canales
 * 
 */

package me.model;

public enum HeroEnum {
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
