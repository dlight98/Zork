
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

/**TODO
 *
 * @author Nicholas Turner
 */
public class Room {

  /**
   * <tt>NoRoomException</tt> extends <tt>{@link Exception}</tt>
   * and is meant to be used for saying when the <tt>{@link Room}</tt>
   * specified does not exist.
   *
   * @author Nicholas Turner
   */
    class NoRoomException extends Exception {}

    static String CONTENTS_STARTER = "Contents: ";

    private String title;
    private String desc;
    private boolean beenHere;
    private ArrayList<Item> contents;
    private ArrayList<Exit> exits;

    /**TODO
     * [Room description]
     * @param title the title of the room.
     */
    Room(String title) {
        init();
        this.title = title;
    }

    /**TODO
     * [Room description]
     * @param  s                                     [description]
     * @param  d                                     [description]
     * @throws NoRoomException                       [description]
     * @throws Dungeon.IllegalDungeonFormatException [description]
     */
    Room(Scanner s, Dungeon d) throws NoRoomException,
        Dungeon.IllegalDungeonFormatException {

        this(s, d, true);
    }

    /** Given a Scanner object positioned at the beginning of a "room" file
        entry, read and return a Room object representing it.
        @param d The containing {@link Dungeon} object, necessary to
        retrieve {@link Item} objects.
        @param initState should items listed for this room be added to it?
        @throws NoRoomException The reader object is not positioned at the
        start of a room entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
     */
    Room(Scanner s, Dungeon d, boolean initState) throws NoRoomException,
        Dungeon.IllegalDungeonFormatException {

        init();
        title = s.nextLine();
        desc = "";
        if (title.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoRoomException();
        }

        String lineOfDesc = s.nextLine();
        while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
               !lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {

            if (lineOfDesc.startsWith(CONTENTS_STARTER)) {
                String itemsList = lineOfDesc.substring(CONTENTS_STARTER.length());
                String[] itemNames = itemsList.split(",");
                for (String itemName : itemNames) {
                    try {
                        if (initState) {
                            add(d.getItem(itemName));
                        }
                    } catch (Item.NoItemException e) {
                        throw new Dungeon.IllegalDungeonFormatException(
                            "No such item '" + itemName + "'");
                    }
                }
            } else {
                desc += lineOfDesc + "\n";
            }
            lineOfDesc = s.nextLine();
        }

        // throw away delimiter
        if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            throw new Dungeon.IllegalDungeonFormatException("No '" +
                Dungeon.SECOND_LEVEL_DELIM + "' after room.");
        }
    }

    /** Common object initialization tasks. */
    private void init() {
        contents = new ArrayList<Item>();
        exits = new ArrayList<Exit>();
        beenHere = false;
    }

    /**TODO
     * [getTitle description]
     * @return [description]
     */
    String getTitle() { return title; }

    /**TODO
     * [setDesc description]
     * @param desc [description]
     */
    void setDesc(String desc) { this.desc = desc; }

    /**TODO
     * Store the current (changeable) state of this room to the writer
     * passed.
     * @param  w           [description]
     * @throws IOException [description]
     */
    void storeState(PrintWriter w) throws IOException {
        w.println(title + ":");
        w.println("beenHere=" + beenHere);
        if (contents.size() > 0) {
            w.print(CONTENTS_STARTER);
            for (int i=0; i<contents.size()-1; i++) {
                w.print(contents.get(i).getPrimaryName() + ",");
            }
            w.println(contents.get(contents.size()-1).getPrimaryName());
        }
        w.println(Dungeon.SECOND_LEVEL_DELIM);
    }

    /**TODO
     * [restoreState description]
     * @param  s                                    [description]
     * @param  d                                    [description]
     * @throws GameState.IllegalSaveFormatException [description]
     */
    void restoreState(Scanner s, Dungeon d) throws
        GameState.IllegalSaveFormatException {

        String line = s.nextLine();
        if (!line.startsWith("beenHere")) {
            throw new GameState.IllegalSaveFormatException("No beenHere.");
        }
        beenHere = Boolean.valueOf(line.substring(line.indexOf("=")+1));

        line = s.nextLine();
        if (line.startsWith(CONTENTS_STARTER)) {
            String itemsList = line.substring(CONTENTS_STARTER.length());
            String[] itemNames = itemsList.split(",");
            for (String itemName : itemNames) {
                try {
                    add(d.getItem(itemName));
                } catch (Item.NoItemException e) {
                    throw new GameState.IllegalSaveFormatException(
                        "No such item '" + itemName + "'");
                }
            }
            s.nextLine();  // Consume "---".
        }
    }

    /**TODO
     * [describe description]
     * @return [description]
     */
    public String describe() {
        return describe(false);
    }

    /**TODO
     * [describe description]
     * @param  fullDesc [description]
     * @return          [description]
     */
    public String describe(boolean fullDesc) {
        String description;
        if (beenHere && !fullDesc) {
            description = title;
        } else {
            description = title + "\n" + desc;
        }
        for (Item item : contents) {
            description += "\nThere is a " + item.getPrimaryName() + " here.";
        }
        if (contents.size() > 0) { description += "\n"; }
        if (!beenHere || fullDesc) {
            for (Exit exit : exits) {
                description += "\n" + exit.describe();
            }
        }
        beenHere = true;
        return description;
    }

    /**TODO
     * [leaveBy description]
     * @param  dir [description]
     * @return     [description]
     */
    public Room leaveBy(String dir) {
        for (Exit exit : exits) {
            if (exit.getDir().equals(dir)) {
                return exit.getDest();
            }
        }
        return null;
    }

    /**TODO
     * [addExit description]
     * @param exit [description]
     */
    void addExit(Exit exit) {
        exits.add(exit);
    }

    /**TODO
     * [add description]
     * @param item [description]
     */
    void add(Item item) {
        contents.add(item);
    }

    /**TODO
     * [remove description]
     * @param item [description]
     */
    void remove(Item item) {
        contents.remove(item);
    }

    /**TODO
     * [getItemNamed description]
     * @param  name                 [description]
     * @return                      [description]
     * @throws Item.NoItemException [description]
     */
    Item getItemNamed(String name) throws Item.NoItemException {
        for (Item item : contents) {
            if (item.goesBy(name)) {
                return item;
            }
        }
        throw new Item.NoItemException();
    }

    /**TODO
     * [getContents description]
     * @return [description]
     */
    ArrayList<Item> getContents() {
        return contents;
    }
}
