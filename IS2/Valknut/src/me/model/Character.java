package me.model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import me.view.Messages;

public abstract class Character {
    protected final String name;
    private boolean alive;
    private Map<Element, Integer> elements; // Representing the five elements stats with a Map
	private Map<Attribute, Integer> attributes; // Representing the five attributes stats with a Map
    private int life;
    protected int max_life;
    private boolean escaped;
    private boolean defend;

    public Character(String name, int life, int max_life) {
        this.name = name;
        alive = true;
        this.elements = new EnumMap<>(Element.class) ;
        for(Element e: Element.values())
            elements.put(e,1);
        this.attributes = new EnumMap<>(Attribute.class);
        for(Attribute a: Attribute.values())
            attributes.put(a,1);
        this.life = life;
        this.max_life = max_life;
        this.escaped = false;
        this.defend = false;
    }

    public int getStrength(){
        return attributes.get(Attribute.STRENGTH);
    }

    public int getResistance(){
        return attributes.get(Attribute.RESISTANCE);
    }

    public int getAgility(){
        return attributes.get(Attribute.AGILITY);
    }

    public int getSpeed(){
        return attributes.get(Attribute.SPEED);
    }

    public int getCleverness(){
        return attributes.get(Attribute.CLEVERNESS);
    }

    public void increaseMaxLife(int amount) {
        if (amount <= 0) return;
        max_life += amount;
        if (life > max_life) life = max_life;
    }

    public Element getMainElement(){
        return Collections.max(elements.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    
    public String name(){
        return name;
    }

    public void setEscaped(boolean b){
        escaped = b;
    }

    public boolean escaped(){
        return escaped;
    }

    public void setElementStats(List <Integer> stats){
        int i = 0;
        for(Element e: Element.values()){
            if(stats.get(i) > 0 && stats.get(i) < 11)
                elements.put(e, stats.get(i));
            i++;
        }
    }

    public void setAttributeStats(List <Integer> stats){
        int i = 0;
        for(Attribute a: Attribute.values()){
            if(stats.get(i) > 0 && stats.get(i) < 11)
                attributes.put(a, stats.get(i));
            i++;
        }
    }

    public String getStringElements(){
        StringBuilder sb = new StringBuilder();

        for(Element e: Element.values())
            sb.append(e.toString()).append(": ").append(elements.get(e)).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public String getStringAttributes(){
        StringBuilder sb = new StringBuilder();

        for(Attribute a: Attribute.values())
            sb.append(a.toString()).append(": ").append(attributes.get(a)).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public int getLife(){
        return life;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isWeak(Element e){
        return getMainElement().isWeak(e);
    }

    public boolean isResistant(Element e){
        return getMainElement().isResistant(e);
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

    public String receiveDamage(int damage, Element element) {
        StringBuilder sb = new StringBuilder();
        if (isWeak(element)) {
        	damage *= 1.5;
            sb.append(name.toUpperCase()).append(Messages.HERO_WEAK).append(element.toString()).append(Messages.NEW_LINE);
        }
        else if (isResistant(element)) {
        	damage -= 10;
            sb.append(name.toUpperCase()).append(Messages.HERO_RESISTANT).append(element.toString()).append(Messages.NEW_LINE);
        }
        
        if(damage < 0)
            damage = 0;

    	changeLife(-damage);
        
        sb.append(name.toUpperCase()).append(" has received ").append(damage).append( " point of damage.").append(Messages.NEW_LINE);
        sb.append("Health points: ").append(life);

        return sb.toString();
    }
    public int computeDamage(Character e, int damage){

        int result = (int) (damage + damage * 0.5 * getStrength());
        result -= (int) (damage * 0.5 * e.getResistance());

        if(result < 0)
            result = 0;

        return result;
    }

    public String attack(Character e, Element element, int damage) {
        StringBuilder sb = new StringBuilder();
        int var = computeDamage(e, damage);
        sb.append(e.receiveDamage(var, element)).append(Messages.NEW_LINE);

        return sb.toString();
    }

    public void changeLife(int value){
        life += value;
        if(life > max_life){
            life = max_life;
        }
        else if(life <= 0){
            life = 0;
            alive = false;
        }
    }

    public void changeShield(int value){
        int shield = attributes.get(Attribute.RESISTANCE);
        shield += value;
        if(shield > 9){
            shield = 9;
        }
        else if(shield < -5){
            shield = -5;
        }
        attributes.put(Attribute.RESISTANCE, shield);
    }

    public void changeElement(Element e, int mod){
        int i = elements.get(e);
        i += mod;
        if(i > 10){
            i = 10;
        }
        if(i < 1){
            i = 1;
        }
        elements.put(e, i);
    }

    public void changeAgility(int mod){
        int ag = attributes.get(Attribute.AGILITY);
        ag += mod;
        if(ag > 10){
            ag = 10;
        }
        if(ag < 0){
            ag = 0;
        }
        attributes.put(Attribute.AGILITY, ag);
    }

    public void changeStrength(int mod){
        int damage = attributes.get(Attribute.STRENGTH);
        damage += mod;
        if(damage > 10){
            damage = 10;
        }
        else if(damage < 1){
            damage = 1;
        }
        attributes.put(Attribute.STRENGTH, damage);
    }
}
