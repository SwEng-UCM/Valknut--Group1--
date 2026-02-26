package me.model;

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
    private int max_life;
    private double mod;
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
        this.escaped = false;
        this.defend = false;
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
        changeLife(-damage);
        System.out.println(name.toUpperCase() + " has received " + damage + " point of damage.");
        System.out.println(name.toUpperCase() + "'s health points: " + life);
    }

    public void attack(Character e, Element element, int damage) {
        int var = damage + (int)(damage*mod);
        e.receiveDamage(var, element);
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
        attributes.put(Attribute.RESISTANCE, damage);
    }
}




