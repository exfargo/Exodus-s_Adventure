package other;

import character.Player;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Shop {
    final LinkedHashMap<String, int[]> list = new LinkedHashMap<>();
    final Random r = new Random();

    public Shop(String shop) {
        if (shop.equals("Shop")) {
            this.list.put("Health_Potion", new int[]{5, Player.getValueBuy("Health_Potion", 5) * 3});
            this.list.put("Galactic_Scythe_Damage", new int[]{30, Player.getValueBuy("_Damage", 30)});
            this.list.put("Heroic_Crown_Armor", new int[]{20, Player.getValueBuy("_Armor", 20)});
            this.list.put("Sword_Of_Null_Pointer_Damage", new int[]{54, Player.getValueBuy("_Damage", 54)});
        }
        else if (shop.equals("Store")) {
            this.list.put("Health_Potion", new int[]{5, Player.getValueBuy("Health_Potion", 5) * 2});
            this.list.put("Sharpening_Tool_Dmg%", new int[]{5, Player.getValueBuy("_Dmg%", 5)});
            this.list.put("Slicing_Edge_Dmg%", new int[]{8, Player.getValueBuy("_Dmg%", 8)});
            if (r.nextInt(3) <= 1) {
                this.list.put("Blessed_Metal_Arm%", new int[]{10, Player.getValueBuy("_Arm%", 10)});
            }
            if (r.nextInt(10) <= 3) {
                this.list.put("Eye_Of_Extinction_Dmg%", new int[]{7, Player.getValueBuy("_Dmg%", 7)});
            }
            if (r.nextInt(5) <= 1) {
                this.list.put("Sword_Of_Gryffindor_Damage", new int[]{65, Player.getValueBuy("_Damage", 65)});
            }
            if (r.nextInt(10) <= 1) {
                this.list.put("Glamdring_Damage", new int[]{87, Player.getValueBuy("_Damage", 87)});
            }
            if (r.nextInt(30) <= 1) {
                this.list.put("Sword_Of_Truth_Damage", new int[]{101, Player.getValueBuy("_Damage", 101)});
            }
        }
        else if (shop.equals("Merchant")) {
            this.list.put("Health_Potion", new int[]{5, Player.getValueBuy("Health_Potion", 5)});
            this.list.put("Sharpening_Tool_Dmg%", new int[]{2, Player.getValueBuy("_Dmg%", 2)});
            this.list.put("Slicing_Edge_Dmg%", new int[]{4, Player.getValueBuy("_Dmg%", 4)});
            this.list.put("Obsidian_Plate_Arm%", new int[]{7, Player.getValueBuy("_Arm%", 7)});
            this.list.put("Mighty_Sharpening_Tool_Dmg%", new int[]{8, Player.getValueBuy("_Dmg%", 8)});
            this.list.put("Calamari_Wizard_Staff_Damage", new int[]{56, Player.getValueBuy("_Damage", 56)});
            this.list.put("Doom_Coin", new int[]{1, 666});
        }
    }

    public static String cropString(String text) {
        return text.substring(0, text.lastIndexOf("_"));
    }

    public static String cropEnd(String text) {
        return text.substring(text.lastIndexOf("_") + 1);
    }

    public void printList() {
        for (Map.Entry<String, int[]> stringEntry : this.list.entrySet()) {
            String cur = stringEntry.toString();
            cur = cur.substring(0, cur.indexOf("="));
            if (cur.contains("Damage") || cur.contains("Armor") || cur.contains("Dmg%") || cur.contains("Arm%")) {
                System.out.print(cropString(cur).replaceAll("_", " ") + "\n" + cropEnd(cur).replaceAll("_", " "));
                System.out.print(" : " + this.list.get(cur)[0]);
            } else {
                System.out.println(cur.replaceAll("_", " "));
                System.out.print(this.list.get(cur)[0]);
            }
            System.out.println("  |  Price : " + this.list.get(cur)[1]);
        }
    }

    public int quantity(String item) {
        return this.list.get(item.replaceAll(" ", "_"))[0];
    }

    public int price(String item) {
        return this.list.get(item.replaceAll(" ", "_"))[1];
    }
}





