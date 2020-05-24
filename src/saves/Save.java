package saves;

import character.Bottle;
import character.Player;
import other.Level;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Save {
    public static void saveCh(Player Harry, String name) {
        try {
            String path = "Saves/Files/" + name + "Character.ser";
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(Harry);

            o.close();
            f.close();
            System.out.println("Character Saved");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    public static void saveMap(Level map, String name) {
        try {
            String path = "Saves/Files/" + name + "Level.ser";
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(map);

            o.close();
            f.close();
            System.out.println("Level Saved");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }

    public static void saveBottle(Bottle coll, String name) {
        try {
            String path = "Saves/Files/" + name + "Collection.ser";
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(coll);

            o.close();
            f.close();
            System.out.println("Collection Saved");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }
}
