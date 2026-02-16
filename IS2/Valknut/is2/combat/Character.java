package combat;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Character {
    protected String name;
    private boolean alive;
    private Map<Element, Integer> elements; // Representing the five elements stats with a Map
	private Map<Attribute, Integer> attributes; // Representing the five attributes stats with a Map
    private int life;
    private int shield;
    private boolean escaped;
    private boolean defend;

    public Character(String name, int life) {
        this.name = name;
        alive = true;
        this.elements = new EnumMap<>(Element.class) ;
        for(Element e: Element.values())
            elements.put(e,1);
        this.attributes = new EnumMap<>(Attribute.class);
        for(Attribute a: Attribute.values())
            attributes.put(a,1);
        this.life = life;
        this.shield = 0;
        this.escaped = false;
        this.defend = false;
    }

    public Element getMainElement(){
        return Collections.max(elements.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void setEscaped(boolean b){
        escaped = b;
    }

    public boolean escaped(){
        return escaped;
    }

    public void setElementStats(List <Integer> stats){
        int i = 0;
        for(Element e: Element.values())
            if(stats.get(i) > 0 && stats.get(i) < 11)
                elements.put(e, stats.get(i++));
    }

    public void setAttributeStats(){

    }

    public void printElements(){
        for(Element e: Element.values())
            System.out.println(e.toString() + ": " + elements.get(e));
    }

    public int getLife(){
        return life;
    }

    public boolean isAlive(){
        return alive;
    }

    public void defend(){
        defend = true;
    }

    public boolean isDefending(){
        boolean yes = defend;
        if(defend)
            defend = !defend;
        return yes;
    }

    public void receiveDamage(int damage, Element element) {
        int mod = 1;
        life -= damage * mod;
        System.out.println(name.toUpperCase() + " has received " + damage + " point of damage.");
        System.out.println(name.toUpperCase() + "'s health points: " + life);
        if(life <= 0){
            System.out.println(name.toUpperCase() + " DIE.");
            life = 0;
            alive = false;
        }
    }

    public void attack(Character e, Element element, Object item, int damage) {
        e.receiveDamage(damage, element);
    }

    public String name(){
        return name;
    }
}




