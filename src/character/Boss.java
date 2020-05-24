package character;

import java.util.Random;

public class Boss {
    private static boolean isSummoned;
    private static int position = -1;
    final Random r = new Random();
    private final int armor;
    private final int ability;
    private final int damage;
    private String name;
    private int health;
    private int skill;

    public Boss(int bossDifficulty, int summonedLocation) {
        this.health = (550 * bossDifficulty) + r.nextInt(35);
        this.armor = (17 * bossDifficulty) + r.nextInt(13);
        this.damage = (44 * bossDifficulty) + r.nextInt(24);
        this.ability = (77 * bossDifficulty) + r.nextInt(23);
        this.skill = 2;

        isSummoned = true;
        position = summonedLocation;

        if (bossDifficulty == 1) this.name = "Zedek";
        if (bossDifficulty == 2) this.name = "Karel";
        if (bossDifficulty >= 3) this.name = "Chingischan v." + (bossDifficulty - 2);
    }

    //<editor-fold desc="Location">
    public static int getPosition() {
        if (position < 0) return -1;
        else return position;
    }

    public static void setPosition(int value) {
        position = value;
    }

    public static boolean getIsSummoned() {
        return isSummoned;
    }

    public static void setIsSummoned(boolean value) {
        isSummoned = value;
    }
    //</editor-fold>

    //<editor-fold desc="Health">
    public int getHealth() {
        return this.health;
    }

    public void obtainDamage(int value) {
        value -= this.armor;
        this.health -= value;
    }
    //</editor-fold>

    //<editor-fold desc="Armor_Damage">
    public int getDamage() {
        return this.damage;
    }

    public int getArmor() {
        return this.armor;
    }
    //</editor-fold>

    //<editor-fold desc="Ability">
    public int getSkillCooldown() {
        return this.skill;
    }

    public void skillCooldown() {
        this.skill -= 1;
    }

    public void resetSkillCooldown() {
        this.skill = 2;
    }

    public int getAbilityDamage() {
        return this.ability;
    }
    //</editor-fold>

    public String getName() {
        return this.name;
    }


}
