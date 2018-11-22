
import java.util.Scanner;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;

public class Item {

    static class NoItemException extends Exception {}

    private String primaryName;
    private int weight;
    private Hashtable<String,String> messages;
    private Set<String> aliases;

    /**
     * [Item description]
     * @param  s                                     [description]
     * @throws NoItemException                       [description]
     * @throws Dungeon.IllegalDungeonFormatException [description]
     */
    Item(Scanner s) throws NoItemException,
        Dungeon.IllegalDungeonFormatException {

        messages = new Hashtable<String,String>();
        aliases = new HashSet<String>();

        // Read item name.
        String names[] = s.nextLine().split(",");
        if (names[0].equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoItemException();
        }
        primaryName = names[0];
        for (int i=1; i<names.length; i++) {
            aliases.add(names[i]);
        }

        // Read item weight.
        weight = Integer.valueOf(s.nextLine());

        // Read and parse verbs lines, as long as there are more.
        String verbLine = s.nextLine();
        while (!verbLine.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            if (verbLine.equals(Dungeon.TOP_LEVEL_DELIM)) {
                throw new Dungeon.IllegalDungeonFormatException("No '" +
                    Dungeon.SECOND_LEVEL_DELIM + "' after item.");
            }
            String[] verbParts = verbLine.split(":");
            messages.put(verbParts[0],verbParts[1]);

            verbLine = s.nextLine();
        }
    }

    /**
     * [getWeight description]
     * @return [description]
     */
    int getWeight() {
        return weight;
    }

    /**
     * [goesBy description]
     * @param  name [description]
     * @return      [description]
     */
    boolean goesBy(String name) {
        if (this.primaryName.equals(name)) {
            return true;
        }
        for (String alias : this.aliases) {
            if (alias.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * [getPrimaryName description]
     * @return [description]
     */
    String getPrimaryName() { return primaryName; }

    /**
     * [getMessageForVerb description]
     * @param  verb [description]
     * @return      [description]
     */
    public String getMessageForVerb(String verb) {
        return messages.get(verb);
    }

    /**
     * [toString description]
     * @return [description]
     */
    public String toString() {
        return primaryName;
    }
}
