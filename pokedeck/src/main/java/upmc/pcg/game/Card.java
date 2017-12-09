package upmc.pcg.game;

import java.util.ArrayList;

public class Card {

    private String name;
    private int health_points;
    private String energy_type;
    private ArrayList<Attack> attacks;
    private ArrayList<Object> weakness;
    private ArrayList<Object> resistance;
    private String type;
    private String description;
    public static String[] types = new String[]{"Grass", "Fire", "Water", "Lightning", "Psychic", "Fighting", "Darkness", "Metal", "Fairy", "Dragon", "Colorless"};

    public String toString(){
        return "    Card Type: "+this.type+
                "\n Name: "+this.name+
                "\n Energy: "+this.energy_type+
                "\n HP: "+this.health_points+
                "\n Weakness: "+this.weakness.get(0)+" x "+this.weakness.get(1)+
                "\n Resistance: "+this.resistance.get(0)+" - "+this.resistance.get(1)+
                "\n Attacks: "+
                "\n "+this.attacks.get(0).getName()+"(Power: "+this.attacks.get(0).getPower()+")"+
                "\n "+this.attacks.get(1).getName()+"(Power: "+this.attacks.get(1).getPower()+")";
    }

    public Card(){
        this.type= "MONSTER";
        this.attacks = new ArrayList<Attack>(2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth_points() {
        return health_points;
    }

    public void setHealth_points(int health_points) {
        this.health_points = health_points;
    }

    public String getEnergy_type() {
        return energy_type;
    }

    public void setEnergy_type(String energy_type) {
        this.energy_type = energy_type;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }

    public ArrayList<Object> getWeakness() {
        return weakness;
    }

    public void setWeakness(ArrayList<Object> weakness) {
        this.weakness = weakness;
    }

    public ArrayList<Object> getResistance() {
        return resistance;
    }

    public void setResistance(ArrayList<Object> resistance) {
        this.resistance = resistance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String[] getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
