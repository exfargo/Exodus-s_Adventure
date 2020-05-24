package other;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Craft {
    final LinkedHashMap<String[], String[]> list = new LinkedHashMap<>();


    public Craft() {
        //<editor-fold desc="Initialize">
        list.put(new String[]{"Health_Potion", "5"}, new String[]{"Seaweed", "3", "Palm", "2", "Oil", "5"});
        list.put(new String[]{"Strong_Health_Potion", "5"}, new String[]{"Health_Potion", "5", "Seaweed", "3", "Flower", "6"});
        list.put(new String[]{"Vitality_Potion", "2"}, new String[]{"Oil", "2", "Cactus", "3", "Sand", "2"});
        list.put(new String[]{"Strength_Potion", "2"}, new String[]{"Pebble", "2", "Iron", "4", "Coral", "3"});
        list.put(new String[]{"Translocation_Potion", "2"}, new String[]{"Rope", "4", "Parrot", "3", "Health_Potion", "2", "Oil", "2"});
        list.put(new String[]{"Sword_Fish_Damage", "40"}, new String[]{"Fish", "13", "Coral", "10", "Iron", "11"});
        list.put(new String[]{"Wooden_Boots_Armor", "10"}, new String[]{"Wood", "12", "Pebble", "15", "Gold", "8"});
        list.put(new String[]{"Animal_Crucifix_Damage", "35"}, new String[]{"Fish", "14", "Parrot", "13", "Flower", "9"});
        list.put(new String[]{"Living_Iron_Arm%", "6"}, new String[]{"Cactus", "17", "Sand", "14", "Coal", "9", "Rope", "7"});
        list.put(new String[]{"Fiery_Oil_Dmg&", "7"}, new String[]{"Oil", "15", "Coal", "9", "Gem", "13"});
        list.put(new String[]{"Armor_Of_The_Exodus_Armor", "65"}, new String[]{"Vitality_Potion", "15", "Gold", "10", "Gem", "13", "Coral", "7", "Flower", "3", "Palm", "9", "Parrot", "11"});
        list.put(new String[]{"Sword_Of_The_Exodus_Damage", "130"}, new String[]{"Strength_Potion", "15", "Iron", "10", "Pebble", "13", "Seaweed", "7", "Cactus", "3", "Wood", "9", "Fish", "11"});
        //</editor-fold>
    }

    public void list(Map<String, Integer> inv) {
        Iterator<String[]> iterate = list.values().iterator();

        for (String[] strings : list.keySet()) {
            var t = strings[0];
            var q = iterate.next();
            boolean print = false;
            for (int i = 0; i < q.length; i += 2) {
                print = inv.containsKey(q[i]) && inv.get(q[i]) >= Integer.parseInt(q[i + 1]);
            }
            if (print) {
                System.out.println(t.replaceAll("_", " "));
            }
        }
    }

    public String[] getIngredients(String name) {
        Iterator<String[]> listk = list.keySet().iterator();
        Iterator<String[]> listv = list.values().iterator();
        while (listk.hasNext()) {
            String t = listk.next()[0];
            var y = listv.next();
            if (t.equals(name)) {
                return y;
            }
        }
        return null;
    }

    public String[] getResult(String name) {
        for (String[] t : list.keySet()) {
            if (t[0].equals(name)) {
                return t;
            }
        }
        return null;
    }
}
