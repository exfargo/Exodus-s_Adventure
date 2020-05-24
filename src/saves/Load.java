package saves;

import character.Bottle;
import character.Player;
import other.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class Load {
    public static Player loadCh(String name) {
        try {
            String path = "Saves/Files/" + name + "Character.ser";
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(fi);

            Player pr1 = (Player) oi.readObject();

            oi.close();
            fi.close();
            System.out.println("Character loaded");
            return pr1;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
        return null;
    }

    public static Level loadMap(String name) {
        try {
            String path = "Saves/Files/" + name + "Level.ser";
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(fi);

            Level pr1 = (Level) oi.readObject();

            oi.close();
            fi.close();
            System.out.println("Level loaded");
            return pr1;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
        return null;
    }

    public static Bottle loadBottle(String name) {
        try {
            String path = "Saves/Files/" + name + "Collection.ser";
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(fi);

            Bottle col = (Bottle) oi.readObject();

            oi.close();
            fi.close();
            System.out.println("Collection loaded");
            return col;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
        return null;
    }

    public static void printFiles() {
        File[] files = new File("saves/files").listFiles();
        for (File file : files) {
            if (file.toString().contains("Character"))
                System.out.println(file.getName().substring(0, file.getName().indexOf("Character.ser")));
        }
    }
}
