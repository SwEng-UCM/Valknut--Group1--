package combat;

public class Player {
	private int life;
	
	
	public Player() {
		life = 100;
	}
	
	public boolean receiveAttack(int attack) {
		life -= attack;
		
		return life > 0;
	}
}
