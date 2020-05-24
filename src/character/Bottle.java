package character;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Bottle implements Serializable {
    final Map<String, Boolean> jews = new HashMap<>();
    final String[] slaveNames;
    final int longestString;

    public Bottle(String[] slaves) {
        this.slaveNames = slaves;
        for (String q : slaves) {
            this.jews.put(q, false);
        }
        longestString = getLongestString(slaves);
    }

    public static int getLongestString(String[] array) {
        int maxLength = 0;
        for (String s : array) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }
        return maxLength;
    }

    public void printCollection() {
        for (var stringEntry : this.jews.entrySet()) {
            String current = stringEntry.toString();
            String status;
            if (current.substring(current.indexOf("=") + 1).contains("true")) {
                status = "Imprisoned";
            } else
                status = "Free";
            StringBuilder spaces = new StringBuilder();
            for (int i = 0; i + current.substring(0, current.indexOf("=")).length() < longestString; i++)
                spaces.append(" ");
            System.out.println(current.substring(0, current.indexOf("=")) + spaces + "      " + status);
        }
    }

    public void addToCollection(String name, Player harry) {
        if (!this.jews.get(name)) {
            this.jews.replace(name, false, true);
            ControlCollector(harry);
        }
    }

    private void ControlCollector(Player harry) {
        boolean completed = true;
        for (var stringEntry : this.jews.values()) {
            if (stringEntry.toString().contains("false")) {
                completed = false;
                break;
            }
        }
        if (completed) {
            System.out.println("\nYour bottle is full now. You drink all your friend's souls.");
            harry.getCollectorBonus();
            for (var stringEntry : this.jews.keySet()) {
                this.jews.replace(stringEntry, true, false);
            }
        }
    }
}
