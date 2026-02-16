package combat;

import java.util.List;

public class Enemy extends Character {
    public Enemy(String name, int life) {
        super(name, life);
    }

    public Hero selectTarjet(List<Hero> e){
        Hero h;
        int max = 0, i = 0, j = 0;
        for(Hero hero: e){
            if(max < hero.getLife()){
                max = hero.getLife();
                i = j;
            }
            j++;
        }

        return e.get(i);
    }
}
