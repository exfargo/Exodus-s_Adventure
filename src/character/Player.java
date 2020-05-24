package character;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    static final HashMap<String, Integer> values = new HashMap<>();
    private static final Random r = new Random();
    final Map<String, Integer> inv = new HashMap<>();
    final String[] bonuses = {"Health", "Damage", "Armor"};
    private final String[] EnemyDrop = {"Golden_Sword_Damage", "Diamond_Sword_Damage", "Hercules_Bow_Damage", "Gem", "Relic", "Medusa_Head_Damage", "Rock_Damage"};
    private final String[] BossDrop = {"Golden_Sword_Damage", "Diamond_Sword_Damage", "Rod_Of_Calamari_Damage", "Gem", "Relic", "Medusa_head_Damage", "Palm", "Seaweed", "Gold", "Pope's_Antivirus_Armor"};
    private final String[] ChestDrop = {"Relic", "Gold", "Gem", "Heroic_Crown_Armor", "Cruci_Fix_Damage"};
    private int health;
    private int armor;
    private int damage;
    private int multiplierD = 1;
    private int multiplierH = 1;
    private float bonusArmor = 1;
    private float bonusHealth = 1;
    private float bonusDamage = 1;

    public Player() {
        this.health = 100;
        this.inv.put("Qiqra", 25);
        this.inv.put("Health_Potion", 15);
        this.inv.put("Electric_Sword_Damage", 30);
        this.inv.put("Leather_Plate_Armor", 10);
        this.inv.put("Vitality_Potion", 1);
        this.inv.put("Strength_Potion", 1);
        this.inv.put("Translocation_Potion", 2);

        //<editor-fold desc="value initializer...">
        values.put("Qiqra", 1);
        values.put("Health_Potion", 2);
        values.put("Strong_Health_Potion", 6);
        values.put("Vitality_Potion", 9);
        values.put("Strength_Potion", 9);
        values.put("_Damage", 5);
        values.put("_Armor", 6);
        values.put("_Arm%", 17);
        values.put("_Dmg%", 21);
        values.put("Wood", 4);
        values.put("Flower", 5);
        values.put("Pebble", 2);
        values.put("Cactus", 4);
        values.put("Oil", 5);
        values.put("Sand", 3);
        values.put("Gem", 6);
        values.put("Iron", 5);
        values.put("Coal", 4);
        values.put("Gold", 6);
        values.put("Seaweed", 5);
        values.put("Coral", 3);
        values.put("Fish", 5);
        values.put("Rope", 2);
        values.put("Parrot", 6);
        values.put("Palm", 5);
        values.put("Exodus's Soul", 1429);
        //</editor-fold>
    }

    public static int getValueBuy(String s, int amount) {
        try {
            double f = (0.1 * (-values.get(s) * amount + r.nextInt((values.get(s) * amount * 2))));
            return (int) ((int) (values.get(s) * 1.5 * amount) + Math.ceil(f));
        } catch (Exception e) {
            if (s.contains("_Damage")) return getValueBuy("_Damage", amount);
            else if (s.contains("_Armor")) return getValueBuy("_Armor", amount);
            else if (s.contains("_Dmg%")) return getValueBuy("_Dmg%", amount);
            else if (s.contains("_Arm%")) return getValueBuy("_Arm%", amount);
        }
        return 25;
    }

    public static int getValueSell(String s) {
        try {
            return values.get(s);
        } catch (Exception e) {
            if (s.contains("_Damage")) return getValueSell("_Damage");
            else if (s.contains("_Armor")) return getValueSell("_Armor");
            else if (s.contains("_Dmg%")) return getValueSell("_Dmg%");
            else if (s.contains("_Arm%")) return getValueSell("_Arm%");
        }
        return 1;
    }

    public static String cropString(String text) {
        return text.substring(0, text.length() - (text.length() - text.lastIndexOf("=")));
    }

    public static String suffix(String text) {
        return text.replaceAll("_Damage", "").replaceAll("_Armor", "").replaceAll("_Dmg%", "").replaceAll("_Arm%", "");
    }

    public static String cropStart(String text) {
        return text.substring(0, text.lastIndexOf("_"));
    }

    public static String cropEnd(String text) {
        return text.substring(text.lastIndexOf("_") + 1);
    }
    //</editor-fold>

    //<editor-fold desc="Health">
    public void changeHealth(int value) {
        this.health += value;
        if (this.health > 100) this.health = 100;
    }

    public int getHealth() {
        return (int) (this.health * multiplierH * bonusHealth);
    }

    public void obtainDamage(int damage) {
        int deal = damage - this.getArmor();
        if (deal < 1) {
            deal = 1;
        }
        this.health -= deal;
    }

    //<editor-fold desc="Damage">
    public int getDamage() {
        this.calcDamage();
        return (int) (this.damage * multiplierD * bonusDamage);
    }
    //</editor-fold>

    public int getDamagePure() {
        var it = this.inv.entrySet().iterator();
        this.damage = 0;
        int tempBig = 0;
        while (it.hasNext()) {
            String val = it.next().toString();
            if (val.contains("_Damage")) {
                if (this.inv.get(cropString(val)) > tempBig) {
                    tempBig = this.inv.get(cropString(val));
                }
            }
        }
        return tempBig;
    }

    public void calcDamage() {
        var it = this.inv.entrySet().iterator();
        this.damage = 0;
        int tempBig = 0;
        while (it.hasNext()) {
            String val = it.next().toString();
            if (val.contains("_Damage")) {
                if (this.inv.get(cropString(val)) > tempBig) {
                    tempBig = this.inv.get(cropString(val));
                }
            }
        }
        this.damage = tempBig;
        for (Map.Entry<String, Integer> stringIntegerEntry : this.inv.entrySet()) {
            String vat = stringIntegerEntry.toString();
            if (vat.contains("_Dmg%")) {
                this.damage *= 1 + ((this.inv.get(cropString(vat)) * 0.01));
            }
        }
    }
    //</editor-fold>

    public void changeBestDamage() {
        var it = this.inv.entrySet().iterator();
        int tempBig = 0;
        String stemp = "";
        while (it.hasNext()) {
            String val = it.next().toString();
            if (val.contains("_Damage")) {
                if (this.inv.get(cropString(val)) > tempBig) {
                    tempBig = this.inv.get(cropString(val));
                    stemp = val;
                }
            }
        }
        this.changeInv(cropString(stemp), (int) -(this.inv.get(cropString(stemp)) * 0.25));
    }

    //<editor-fold desc="Armor">
    public int getArmor() {
        this.calcArmor();
        return (int) (this.armor * bonusArmor);
    }

    public void calcArmor() {
        var it = this.inv.entrySet().iterator();
        this.armor = 0;
        while (it.hasNext()) {
            String val = it.next().toString();
            if (val.contains("_Armor")) {
                this.armor += this.inv.get(cropString(val));
            }
        }
        for (var stringIntegerEntry : this.inv.entrySet()) {
            String vat = stringIntegerEntry.toString();
            if (vat.contains("_Arm%")) {
                this.armor *= 1 + ((this.inv.get(cropString(vat)) * 0.01));
            }
        }
    }

    //<editor-fold desc="Inventory">
    public void setInv(String item, int amount) {
        this.inv.put(item, amount);
    }

    public int getItem(String item) {
        return this.inv.getOrDefault(item, 0);
    }

    public Map<String, Integer> getItem() {
        return this.inv;
    }

    public void changeInv(String item, int value) {
        if (this.inv.containsKey(item))
            this.inv.put(item, this.inv.get(item) + value);
        else
            this.inv.put(item, value);
    }

    public void showInv() {
        this.DestroyZeroInv();
        for (var stringEntry : this.inv.entrySet()) {
            String cur = stringEntry.toString();
            cur = cur.substring(0, cur.indexOf("="));
            if (cur.contains("Damage") || cur.contains("Armor") || cur.contains("Dmg%") || cur.contains("Arm%")) {
                System.out.println(cropStart(cur).replaceAll("_", " ") + "\n" + cropEnd(cur).replaceAll("_", "") + " : " + this.inv.get(cur));
            } else {
                System.out.println(cur.replaceAll("_", " ") + "\n" + this.inv.get(cur));
            }
            System.out.println();
        }
    }

    public void showPotions() {
        this.DestroyZeroInv();
        for (var stringEntry : this.inv.entrySet()) {
            String cur = stringEntry.toString();
            cur = cur.substring(0, cur.indexOf("="));
            if (cur.toLowerCase().contains("potion")) {
                System.out.println(cur.replaceAll("_", " ") + "  " + this.inv.get(cur));
            }
        }
    }

    public int getQiqra() {
        return this.getItem("Qiqra");
    }

    public void loot(String item) {
        if ("Key".equals(item)) {
            if (this.inv.containsKey("Key")) {
                this.changeInv("Key", 1);
            } else
                this.inv.put("Key", 1);
            System.out.println("You received 1 key.");
        } else {
            int z = r.nextInt(10) + 1;
            if (this.inv.containsKey(item)) this.changeInv(item, z);
            else this.setInv(item, z);
            System.out.println("You received " + z + " " + item);
        }
    }

    public void dropEnemy() {
        String item = EnemyDrop[r.nextInt(EnemyDrop.length)];
        System.out.println();
        int z = 10 + r.nextInt(17);
        this.changeInv("Qiqra", z);
        System.out.println("You received " + z + " Qiqra. Now you have " + this.getQiqra() + " Qiqra.");
        if (this.inv.containsKey(item)) this.changeInv(item, r.nextInt(4) + 3);
        else this.setInv(item, r.nextInt(5) + 4);
        System.out.println("You received some " + item + ". Now you have " + this.inv.get(item));
    }

    public void dropBoss() {
        String item = BossDrop[r.nextInt(BossDrop.length)];
        System.out.println();
        int z = 160 + r.nextInt(200);
        this.changeInv("Qiqra", z);
        System.out.println("You received " + z + " Qiqra. Now you have " + this.getQiqra() + " Qiqra.");
        if (this.inv.containsKey(item)) this.changeInv(item, r.nextInt(25) + 11);
        else this.setInv(item, r.nextInt(30) + 15);
        System.out.println("You received some " + item + ". Now you have " + this.inv.get(item));
    }

    public void dropChest() {
        String item = ChestDrop[r.nextInt(ChestDrop.length)];
        int z = 60 + r.nextInt(100);
        this.changeInv("Qiqra", z);
        System.out.println(z + " Qiqra. Now you have " + this.getQiqra() + " Qiqra.");
        if (this.inv.containsKey(item)) this.changeInv(item, r.nextInt(21) + 9);
        else this.setInv(item, r.nextInt(27) + 7);
        System.out.println(item + ". Now you have " + this.inv.get(item));
    }

    public void craftItem(String[] ingredients, String[] result) {
        boolean successful = true;
        for (int i = 0; i < ingredients.length; i += 2) {
            if (inv.containsKey(ingredients[i]) && inv.get(ingredients[i]) >= Integer.parseInt(ingredients[i + 1])) {
                this.changeInv(ingredients[i], -Integer.parseInt(ingredients[i + 1]));
            } else {
                System.out.println("Not enough resources");
                successful = false;
                break;
            }
        }
        if (successful) {
            System.out.println("You crafted " + result[1] + " of " + result[0].replaceAll("_", " "));
            if (this.inv.containsKey(result[0])) this.changeInv(result[0], Integer.parseInt(result[1]));
            else this.setInv(result[0], Integer.parseInt(result[1]));
        }

    }

    public void buyItem(String item, int value, int price) {
        item = item.replaceAll(" ", "_");
        if (this.getItem("Qiqra") >= price) {
            if (this.inv.containsKey(item)) this.changeInv(item, value);
            else this.setInv(item, value);
            System.out.println("You bought " + suffix(item).replaceAll("_", " ") + " for " + price);
            this.changeInv("Qiqra", -price);
        } else System.out.println("You are too poor");
    }

    public void sellItem(String item, int amount, int i) {
        try {
            if (amount > this.inv.get(item)) amount = this.inv.get(item);
            this.changeInv(item, (-1 * amount));
            int price = (int) Math.floor(getValueSell(item) * amount);
            this.changeInv("Qiqra", price);
            System.out.println("You sold " + amount + " " + item.replaceAll("_", " ") + " for " + price + " Qiqra.");
        } catch (Exception e) {
            try {
                i++;
                if (i == 1) {
                    sellItem(item.concat("_Damage"), amount, i);
                } else if (i == 2) {
                    sellItem(item.substring(0, item.length() - 7).concat("_Armor"), amount, i);
                } else if (i == 3) {
                    sellItem(item.substring(0, item.length() - 6).concat("_Dmg%"), amount, i);
                } else if (i == 4) {
                    sellItem(item.substring(0, item.length() - 5).concat("_Arm%"), amount, i);
                } else {
                    System.out.println("Inventory does not contain this item.");
                }
            } catch (Exception q) {
                // it's empty, because i need it just not to crash
            }
        }
    }
    //</editor-fold>

    public Map<String, Integer> getInv() {
        DestroyZeroInv();
        return this.inv;
    }

    public void sellAllIngredients() {
        DestroyZeroInv();
        Iterator<Map.Entry<String, Integer>> at = inv.entrySet().iterator();
        ArrayList<String> allis = new ArrayList<>();
        while (at.hasNext()) {
            String ter = at.next().toString();
            if (!ter.contains("_Damage") && !ter.contains("_Armor") && !ter.contains("_Dmg%") && !ter.contains("_Arm%") && !ter.contains("Qiqra") && !ter.contains("Potion") && !ter.contains("Soul"))
                allis.add(cropString(ter));
        }
        for (Object item : allis)
            sellItem(item.toString(), 999, 0);
    }

    public void sellAll() {
        DestroyZeroInv();
        Iterator<Map.Entry<String, Integer>> at = inv.entrySet().iterator();
        ArrayList<String> allis = new ArrayList<>();
        while (at.hasNext()) {
            String ter = at.next().toString();
            if (!ter.contains("_Armor") && !ter.contains("Potion") && !ter.contains("_Dmg%") && !ter.contains("_Arm%") && !ter.contains("Qiqra") && this.inv.get(cropString(ter)) != this.getDamagePure() && !ter.contains("Soul"))
                allis.add(cropString(ter));
        }
        for (Object item : allis)
            sellItem(item.toString(), 999, 0);
    }

    public void DestroyZeroInv() {
        Iterator<Map.Entry<String, Integer>> at = inv.entrySet().iterator();
        ArrayList<String> allis = new ArrayList<>();
        while (at.hasNext()) {
            String ter = at.next().toString();
            if (this.inv.get(cropString(ter)) <= 0) {
                allis.add(cropString(ter));
            }
        }
        for (Object item : allis)
            this.inv.remove(item);
    }

    public void showStats() {
        System.out.println("HP : " + this.getHealth());
        System.out.println("Armor : " + this.getArmor());
        System.out.println("Damage : " + this.getDamage());
    }

    public void doubleHealth() {
        this.multiplierH = 2;
    }

    public void exitMultiplierH() {
        this.multiplierH = 1;
    }

    public void doubleDamage() {
        this.multiplierD = 2;
    }

    public void exitMultiplierD() { this.multiplierD = 1; }

    public void sellSlaves() {
        DestroyZeroInv();
        Iterator<Map.Entry<String, Integer>> at = inv.entrySet().iterator();
        ArrayList<String> allis = new ArrayList<>();
        while (at.hasNext()) {
            String ter = at.next().toString();
            if (ter.contains("Slave"))
                allis.add(cropString(ter));
        }
        int sum = 0;
        for (Object item : allis) {
            int q = this.inv.get(item.toString()) * 35 + r.nextInt(10);
            this.changeInv("Qiqra", q);
            this.inv.remove(item, 999);
            sum += q;
        }
        System.out.println("You sold your slaves for " + sum);
    }

    public void getCollectorBonus() {
        int random = r.nextInt(bonuses.length);
        switch (random) {
            case 0:
                this.bonusHealth += 0.25;
            case 1:
                this.bonusDamage += 0.12;
            case 2:
                this.bonusArmor += 0.2;
        }
        System.out.println("You obtained " + bonuses[random] + " bonus!");
    }
}
