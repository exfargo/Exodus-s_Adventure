package character;

import java.security.SecureRandom;

public class Npcs {

    static final String[] slaves = {"Imani", "Bahati", "Jafari", "Dálí", "Feechi", "Baraka", "Kehinde", "Jinjabu",
            "Gahigirian", "Iboidife", "Kanoe", "Ishaanmar", "Varwilin", "Ahmad", "Ajaysana", "Shylahdn", "Shashirum",
            "Mugechilwanva", "Dlodseki", "Mechechuez"};
    static int score = 1;
    final String[] enemiesValley = {"Falcon", "Ladybug", "Rabbit", "Merchant", "Lizard", "Swan", "Judah"};
    final String[] enemiesDesert = {"Worm", "Condor", "Acolyte", "Merchant", "Scorpion", "Alchemist", "Phantom"};
    final String[] enemiesMountain = {"Eagle", "Worm", "Stoner", "Merchant", "Tesla Coil", "Living Rock", "Negro"};
    final String[] enemiesCanyon = {"Hammeroid", "Apache", "Wizard", "Merchant", "Vampire", "Rocky", "Medusa"};
    final String[] enemiesJungle = {"Snake", "Chloroglob", "Trader", "Merchant", "Visage", "Spaghetto", "Giuseppe"};
    final String[] enemiesOcean = {"Fishron", "OctoMum", "Adoplhin", "Merchant", "Rivertail", "Sea Tiger", "Leviathan"};
    final String[] enemiesDungeon = {"Infernon", "Sun Walker", "Fuhrer"};
    final int[] damageDungeon = {35, 20, 45};
    final int[] hpDungeon = {105, 70, 95};
    final int[] difficulties = {1, 1, 2, 3, 3, 4, 5, 6};
    final int[] damageRegular = {15, 25, 30, 0, 40, 50, 65};
    final int[] hpRegular = {60, 70, 75, 200, 85, 95, 105};
    final String[] prefixes = {"Deadly", "Heroic", "Fast", "Energized", "Viral", "Naughty", "Ancient", "Dangerous",
            "Horny", "Furious", "Vibrating", "Soundproof", "Magmatic"};
    final SecureRandom r = new SecureRandom();
    private final String name;
    private final int damage;
    private int difficulty;
    private String prefix;
    private int health;

    public Npcs(int diff, String tile) {
        int limit = 0;
        for (int i = 0; i < difficulties.length; i++) {
            if (difficulties[i] == (diff + 1)) {
                limit = i;
                break;
            }
        }
        if (r.nextInt(15) < 1) {
            this.name = slaves[r.nextInt(slaves.length)] + " Slave";
            this.damage = r.nextInt(5) + 1;
            this.health = r.nextInt(7) + 1;
        } else {
            switch (tile) {
                case "Valley": {
                    int rand = r.nextInt(limit);
                    this.name = enemiesValley[rand];
                    this.damage = (int) (damageRegular[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpRegular[rand] * (1 + score * 0.1) - r.nextInt(9));
                    this.difficulty = difficulties[rand];
                    break;
                }
                case "Desert": {
                    int rand = r.nextInt(limit);
                    this.name = enemiesDesert[rand];
                    this.damage = (int) (damageRegular[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpRegular[rand] * (1 + score * 0.1) - r.nextInt(9));
                    this.difficulty = difficulties[rand];
                    break;
                }
                case "Mountain": {
                    int rand = r.nextInt(limit);
                    this.name = enemiesMountain[rand];
                    this.damage = (int) (damageRegular[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpRegular[rand] * (1 + score * 0.1) - r.nextInt(9));
                    this.difficulty = difficulties[rand];
                    break;
                }
                case "Canyon": {
                    int rand = r.nextInt(limit);
                    this.name = enemiesCanyon[rand];
                    this.damage = (int) (damageRegular[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpRegular[rand] * (1 + score * 0.1) - r.nextInt(9));
                    this.difficulty = difficulties[rand];
                    break;
                }
                case "Jungle": {
                    int rand = r.nextInt(limit);
                    this.name = enemiesJungle[rand];
                    this.damage = (int) (damageRegular[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpRegular[rand] * (1 + score * 0.1) - r.nextInt(9));
                    this.difficulty = difficulties[rand];
                    break;
                }
                case "Ocean": {
                    int rand = r.nextInt(limit);
                    this.name = enemiesOcean[rand];
                    this.damage = (int) (damageRegular[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpRegular[rand] * (1 + score * 0.1) - r.nextInt(9));
                    this.difficulty = difficulties[rand];
                    break;
                }
                case "Dungeon": {
                    int rand = r.nextInt(enemiesDungeon.length);
                    this.name = enemiesDungeon[rand];
                    this.damage = (int) (damageDungeon[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.health = (int) (hpDungeon[rand] * (1 + score * 0.1) - r.nextInt(5));
                    this.difficulty = 6;
                    break;
                }
                default: {
                    this.name = "Exodus's nightmare";
                    this.damage = 200;
                    this.health = 1250;
                    this.difficulty = 6;
                    break;
                }
            }
            addPrefix();
            score += 0.1;
        }
    }

    public static String[] getSlaves() {
        return slaves;
    }

    public static void getScore() {
        System.out.println("Score : " + score);
    }

    public void addPrefix() {
        this.prefix = prefixes[r.nextInt(prefixes.length)];
        if (r.nextInt(5) <= 1) {
            int q = r.nextInt(prefixes.length);
            if (!prefixes[q].equals(this.prefix)) {
                this.prefix += " " + prefixes[q];
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public String getFullName() {
        return this.prefix + " " + this.name;
    }

    public int getDamageValue() {
        return this.damage;
    }

    public void receiveDamage(int value) {
        this.health -= value;
    }

    public void getStats() {
        System.out.println(this.prefix + " " + this.name);
        System.out.println("HP : " + this.health);
        System.out.println("Dif : " + this.difficulty);

    }
}
