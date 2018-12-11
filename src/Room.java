
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

/**
* A <tt>Room</tt> is the area between <tt>{@link Exit}s</tt> that
* holds <tt>{@link Item}s</tt> and the adventurer can be in. There is
* always at least one per {@link Dungeon}. A room does not require an
* {@link Item} or an {@link Exit}. It needs a description and a title.
* The adventurer is able to travel between these using exits, and may drop
* and take items as he or she chooses.
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
    private boolean Verbose = false;
    private boolean isDark;

    /**
    * Creates a room with the String given.The String becomes
    * the title of the room, which will be called
    * when referencing the room.
    * @param title the title of the room.
    */
    Room(String title) {
      init();
      this.title = title;
    }

    /**
    * This constructor is passed to the other constructor
    * while having the initState param set as true.
    * @param  s the Scanner object reading from file.
    * @param  d the containing {@link Dungeon} object, necessary to retrieve {@link Item} objects.
    * @throws NoRoomException the reader object is not positioned at the
    * start of a room entry. A side effect of this is the reader's cursor
    * is now positioned one line past where it was.
    * @throws Dungeon.IllegalDungeonFormatException a structural problem with the
    * dungeon file itself, detected when trying to read this room.
    */
    Room(Scanner s, Dungeon d) throws NoRoomException,
    Dungeon.IllegalDungeonFormatException {

      this(s, d, true);
    }

    /** Given a Scanner object positioned at the beginning of a "room" file
    entry, read and return a Room object representing it.
    @param s the Scanner object reading from file.
    @param d the containing {@link Dungeon} object, necessary to
    retrieve {@link Item} objects.
    @param initState should items listed for this room be added to it?
    @throws NoRoomException the reader object is not positioned at the
    start of a room entry. A side effect of this is the reader's cursor
    is now positioned one line past where it was.
    @throws IllegalDungeonFormatException a structural problem with the
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

    /**
    * Gets the title of the room and returns it as a string.
    * @return the title of the room.
    */
    String getTitle() { return title; }

    /**
    * Sets the current string as the description
    * of the room.
    * @param desc the description to be set.
    */
    void setDesc(String desc) { this.desc = desc; }

    /**
    * Store the current (changeable) state of this room to the writer
    * passed.
    * @param  w           what is writing to the file.
    * @throws IOException if there is an error writing to the file.
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

    /**
    * This restores the location of the items in the rooms
    * from the save file.
    * @param  s                                    the Scanner reading from file.
    * @param  d                                    the dungeon the rooms are being placed in.
    * @throws GameState.IllegalSaveFormatException if the format of the save file is corrup or incompatible.
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

    /**
    * This calls the other <tt>describe</tt> method and
    * passes the boolean as true. This makes the description
    * of the room be printed.
    * @return the description of the room.
    */
    public String describe() {
      return describe(false);
    }

    /**
    * This will print the title, every {@link Item} in the room,
    * and every {@link Exit} leading out of the room when fullDesc is true.
    * Any exits leading towards the room will not be printed.
    * If fullDesc is not true then only the title will be printed.
    * @param  fullDesc if the full description is meant to be printed.
    * @return          any items and exits pertaining to the room.
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
      if(this.Verbose == false){
        beenHere = true;
      }
      return description;
    }

    /**
    * This attempts to use an <tt>{@link Exit}</tt> given by the user. If the
    * exit does not exist the method returns null.
    * @param  dir the direction of the exit the player is attempting to enter.
    * @return     the room the adventurer is entering, or null if there is no exit.
    */
    public Room leaveBy(String dir) {
      for (Exit exit : exits) {
        if (exit.getDir().equals(dir)) {
          return exit.getDest();
        }
      }
      return null;
    }
    public boolean ExitState(String dir){
      for (Exit exit : exits) {
        if (exit.getDir().equals(dir)) {
          if(exit.getisClosed() == true){
            return true;
          }
        }
      }
      return false;
    }

    /**
    * Adds an {@link Exit} the the room. This exit
    * is added to the source room, not the destination.
    * @param exit the exit to be added to the room.
    */
    void addExit(Exit exit) {
      exits.add(exit);
    }
    public boolean getisDark(){
      return this.isDark;
    }

    /**
    * Adds an {@link Item} to the room. This item
    * can be picked up, used, and dropped by the user
    * @param item the item to be added to the room.
    */
    void add(Item item) {
      contents.add(item);
    }

    /**
    * Removes an {@link Item} from the room.
    * This is normally used to add and item to the
    * inventory of the adventurer.
    * @param item the item to be removed.
    */
    void remove(Item item) {
      contents.remove(item);
    }

    /**
    * Looks in the room to see if the specified {@link Item} is in it.
    * Normally used with {@link TakeItem}.
    * @param  name                 the name of the {@link Item} being searched for.
    * @return                      the item named in the parameter.
    * @throws Item.NoItemException if the {@link Item} specified is not in the room.
    */
    Item getItemNamed(String name) throws Item.NoItemException {
      for (Item item : contents) {
        if (item.goesBy(name)) {
          return item;
        }
      }
      throw new Item.NoItemException();
    }

    /**
    * This returns the <tt>{@link Item}s</tt> currently in the room.
    * These items do not include the ones in the adventurers inventory.
    * @return the items currently in the room.
    */
    ArrayList<Item> getContents() {
      return contents;
    }
    void setBeenHere(boolean value){
      this.beenHere = value;
    }
    void setVerbose(boolean value){
      this.Verbose = value;
    }
  }
