package combat;

public class Chaos {
	private int life;
	
	public Chaos() {
		life = 100;
	}
	
	public boolean receiveAttack(Element attack) {

		switch(attack) {
		case LIGHT:
			life -= 20;
			break;
			
		case NATURE:
			life -= 5;
			break;
		
		case CHAOS:
			life -= 0;
			break;
			
		case FALSEHOOD:
			life -= 10;
			break;
			
		case BLOOD:
			life -= 10;
			break;
		};
		
		System.out.println("My remaining life is:");
		System.out.println(life);
		
		return life > 0;
	}

}
