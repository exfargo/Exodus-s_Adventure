package other;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Level implements Serializable {
    final HashMap<Integer, String> LevelMap = new HashMap<>(); // under id contains certain biome
    final HashMap<Integer, String> EntityMap = new HashMap<>(); // under id contains certain element in biome
    final HashMap<Integer, Boolean> DefeatMap = new HashMap<>();// under id contains if enemy has been defeated
    final HashMap<Integer, Integer> DifficultyMap = new HashMap<>(); //under id contains level difficulty
    final Random r = new Random();

    final String[] lands = {"Valley", "Desert", "Mountain", "Canyon", "Town", "Jungle", "Ocean", "Dungeon"};
    final String[] valley = {"Wood", "Flower", "Pebble"};
    final String[] desert = {"Cactus", "Oil", "Sand"};
    final String[] mountain = {"Gem", "Iron", "Coal", "Gold"};
    final String[] ocean = {"Seaweed", "Coral", "Fish"};
    final String[] jungle = {"Rope", "Parrot", "Palm"};
    final String[] canyon = {"Pebble", "Iron", "Sand"};
    final String[] dungeon = {"Key", "Relic"};

    private int position;
    private int id;
    private int d = 1;
    private int keys = 0;

    public Level() {
        this.position = 0;
        this.id = 0;
        createTile();
        this.DefeatMap.replace(0, false, true);
    }

    @NotNull
    public static String cropEnd(@NotNull String text) {
        return text.substring(text.indexOf("=") + 1);
    }

    @NotNull
    public static String cropString(@NotNull String text) {
        return text.substring(0, text.indexOf("="));
    }

    public void move(@NotNull String dir) {
        if ("back".contains(dir)) {
            this.position -= 1;
        } else {
            this.position += 1;
            this.controlTile();
        }
    }

    public int getPosition() {
        return this.position;
    }

    //<editor-fold desc="Tile">
    public void createTile() {
        int dungeonExtra = 0;
        if (id > 0) {
            if (this.LevelMap.get(position - 1).contains("Dung"))
                dungeonExtra = r.nextInt(6);
        }
        if (dungeonExtra > 0) {
            this.LevelMap.put(id, "Dungeon");
        } else {
            if (this.d <= 2)
                this.LevelMap.put(id, lands[r.nextInt(3)]);
            else if (this.d <= 3)
                this.LevelMap.put(id, lands[r.nextInt(5)]);
            else if (this.d <= 5)
                this.LevelMap.put(id, lands[r.nextInt(7)]);
            else {
                this.LevelMap.put(id, lands[r.nextInt(lands.length)]);
            }
        }
        createEntity();
        this.DifficultyMap.put(id, d);
        this.DefeatMap.put(id, false);
        id++;
        if (id % 4 == 0 && d < 6) {
            d++;
        }

    }

    public void controlTile() {
        if (!this.LevelMap.containsKey(position)) {
            this.createTile();
        }
    }
    //</editor-fold>

    public String getTile() {
        return this.LevelMap.get(position);
    }

    public void lootTile() {
        this.EntityMap.replace(position, getEntity(), "nothing");
    }

    public void createEntity() {
        String entity;
        String tile = getTile();
        if ("Valley".equals(tile)) {
            entity = valley[r.nextInt(valley.length)];
        } else if ("Desert".equals(tile)) {
            entity = desert[r.nextInt(desert.length)];
        } else if ("Mountain".equals(tile)) {
            entity = mountain[r.nextInt(mountain.length)];
        } else if ("Ocean".equals(tile)) {
            entity = ocean[r.nextInt(ocean.length)];
        } else if ("Jungle".equals(tile)) {
            entity = jungle[r.nextInt(jungle.length)];
        } else if ("Canyon".equals(tile)) {
            entity = canyon[r.nextInt(canyon.length)];
        } else if ("Dungeon".equals(tile)) {
            if (keys >= 1) {
                if (r.nextInt(5) < 2) {
                    entity = "Door";
                    keys--;
                } else {
                    entity = dungeon[r.nextInt(dungeon.length)];
                }
            } else {
                entity = dungeon[r.nextInt(dungeon.length)];
            }
        } else {
            entity = "Pebble";
        }
        this.EntityMap.put(id, entity);
        if (this.getEntity().equals("Key")) {
            keys++;
        }
    }

    public String getEntity() {
        return this.EntityMap.get(position);
    }

    public boolean defeatAt() {
        return this.DefeatMap.get(this.position);
    }

    public boolean defeatAt(int q) {
        return this.DefeatMap.get(q);
    }

    public void enemyDefeat() {
        this.DefeatMap.replace(position, false, true);
    }

    public int getDifficultyMap() {
        if (this.DifficultyMap.get(position) >= 5) {
            return 5;
        }
        return this.DifficultyMap.get(position);
    }
    //</editor-fold>

    //<editor-fold desc="Print Map">
    public void printAll() {
        Iterator<Map.Entry<Integer, String>> mapEntries = LevelMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, String>> entityEntries = EntityMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, Boolean>> defeatEntries = DefeatMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> difficultyEntries = DifficultyMap.entrySet().iterator();

        while (mapEntries.hasNext()) {
            String mapEntry = mapEntries.next().toString();
            System.out.print("#" + cropString(mapEntry) + " = ");
            System.out.print(cropEnd(mapEntry) + " | ");
            System.out.print(cropEnd(entityEntries.next().toString()) + " | ");
            if (defeatEntries.next().toString().contains("tr")) {
                System.out.print("cleaned | ");
            } else {
                System.out.print("infected | ");
            }
            System.out.println("difficulty : " + cropEnd(difficultyEntries.next().toString()));
        }
    }

    public void printNear() {
        Iterator<Map.Entry<Integer, String>> mapEntries = LevelMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, String>> entityEntries = EntityMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, Boolean>> defeatEntries = DefeatMap.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> difficultyEntries = DifficultyMap.entrySet().iterator();

        while (mapEntries.hasNext()) {
            String mapEntry = mapEntries.next().toString();
            String entityEntry = entityEntries.next().toString();
            String defeatEntry = defeatEntries.next().toString();
            String difficultyEntry = difficultyEntries.next().toString();
            if (Math.abs(Integer.parseInt(cropString(mapEntry)) - this.getPosition()) < 3 || Math.abs(Integer.parseInt(cropString(mapEntry)) - this.getPosition()) > 3 && Math.abs(Integer.parseInt(cropString(mapEntry)) - this.getPosition()) < 5) {
                System.out.print("#" + cropString(mapEntry) + " = ");
                System.out.print(cropEnd(mapEntry) + " | ");
                System.out.print(cropEnd(entityEntry) + " | ");
                if (defeatEntry.contains("tr")) {
                    System.out.print("cleaned | ");
                } else {
                    System.out.print("infected | ");
                }
                System.out.println("difficulty : " + cropEnd(difficultyEntry));
            }
        }
    }

    public void nullEntity() {
        this.EntityMap.replace(position, "Door", "nothing");
    }

    public boolean warpTo(int tile) {
        if (tile >= 0 && tile < this.LevelMap.values().toArray().length) {
            if (this.defeatAt(tile)) {
                this.position = tile;
                return true;
            } else {
                System.out.println("The location must be cleared first");
                return false;
            }
        } else {
            System.out.println("Invalid tile id !");
            return false;
        }
    }
}
