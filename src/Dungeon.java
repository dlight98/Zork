import java.util.Hashtable;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

/**
* A <tt>Dungeon</tt> holds all of the <tt>{@link Room}s</tt>, <tt>{@link Item}s</tt>,
* and <tt>{@link Exit}s</tt> that the player interacts with. There must be
* at least one <tt>Room</tt> in each dungeon.
* There should only need to be one dugenon instantiated.
*
* @author Nicholas Turner
*/
public class Dungeon {

  /**
  * <tt>IllegalDungeonFormatException</tt> is thrown when the format of the
  * dungeon file is formed incorrectly.
  */
  public static class IllegalDungeonFormatException extends Exception {
    public IllegalDungeonFormatException(String e) {
      super(e);
    }
  }

  // Variables relating to both dungeon file and game state storage.
  public static String TOP_LEVEL_DELIM = "===";
  public static String SECOND_LEVEL_DELIM = "---";

  // Variables relating to dungeon file (.zork) storage.
  public static String ROOMS_MARKER = "Rooms:";
  public static String EXITS_MARKER = "Exits:";
  public static String ITEMS_MARKER = "Items:";

  // Variables relating to game state (.sav) storage.
  static String FILENAME_LEADER = "Dungeon file: ";
  static String ROOM_STATES_MARKER = "Room states:";

  private String name;
  private Room entry;
  private Hashtable<String,Room> rooms;
  private Hashtable<String,Item> items;
  private String filename;

  /**
  * A <tt>Dungeon</tt> holds all of the rooms, items, and exits that the
  * player interacts with. This constructor makes the Dungeon not hydrated
  * from a file.
  * @param name  the name of the dungeon.
  * @param entry the first room the adventurer is placed in.
  */
  Dungeon(String name, Room entry) {
    init();
    this.filename = null;    // null indicates not hydrated from file.
    this.name = name;
    this.entry = entry;
    rooms = new Hashtable<String,Room>();
  }

  /**
  * This is used if the <tt>Dungeon</tt> is made by itself, without a save file.
  * Read from the .zork filename passed, and instantiate a Dungeon object
  * based on it. This is passes to the other Dungeon constructor if it is a
  * .zork file. This is done so a dungeon can be built without needing a
  * boolean to specify if it needs to initialized.
  * @param  filename                      the name of the file being read from.
  * @throws FileNotFoundException         if the file specified in the commandline is not found this will be thrown.
  * @throws IllegalDungeonFormatException if the format of the file is done incorrectly this will be thrown.
  */
  public Dungeon(String filename) throws FileNotFoundException,
  IllegalDungeonFormatException {
    this(filename, true);
  }

  /**
  * Read from the .zork filename passed, and instantiate a Dungeon object
  * based on it, including (possibly) the items in their original locations.
  * @param  filename                      the name of the file being read from.
  * @param  initState                     determines if the dungeon needs the item layout
  * @throws FileNotFoundException         if the file specified in the commandline is not found this will be thrown.
  * @throws IllegalDungeonFormatException if the format of the file is done incorrectly this will be thrown.
  */
  public Dungeon(String filename, boolean initState)
  throws FileNotFoundException, IllegalDungeonFormatException {

    init();
    this.filename = filename;

    Scanner s = new Scanner(new FileReader(filename));
    name = s.nextLine();
    
    s.nextLine();   // Throw away version indicator.

    Clock.instance().init(s); //Initializes the Clock info

    // Throw away delimiter.
    if (!s.nextLine().equals(TOP_LEVEL_DELIM)) {
      throw new IllegalDungeonFormatException("No '" +
      TOP_LEVEL_DELIM + "' after version indicator.");
    }

    // Throw away Items starter.
    if (!s.nextLine().equals(ITEMS_MARKER)) {
      throw new IllegalDungeonFormatException("No '" +
      ITEMS_MARKER + "' line where expected.");
    }

    try {
      // Instantiate items.
      while (true) {
        add(new Item(s));
      }
    } catch (Item.NoItemException e) {  /* end of items */ }

    // Throw away Rooms starter.
    if (!s.nextLine().equals(ROOMS_MARKER)) {
      throw new IllegalDungeonFormatException("No '" +
      ROOMS_MARKER + "' line where expected.");
    }

    try {
      // Instantiate and add first room (the entry).
      entry = new Room(s, this, initState);
      add(entry);

      // Instantiate and add other rooms.
      while (true) {
        add(new Room(s, this, initState));
      }
    } catch (Room.NoRoomException e) {  /* end of rooms */ }

    // Throw away Exits starter.
    if (!s.nextLine().equals(EXITS_MARKER)) {
      throw new IllegalDungeonFormatException("No '" +
      EXITS_MARKER + "' line where expected.");
    }

    try {
      // Instantiate exits.
      while (true) {
        Exit exit = new Exit(s, this);
      }
    } catch (Exit.NoExitException e) {  /* end of exits */ }

    s.close();
  }

  /**
  * Common object initialization tasks, regardless of which constructor
  * is used.
  */
  private void init() {
    rooms = new Hashtable<String,Room>();
    items = new Hashtable<String,Item>();
  }

  /**
  * Store the current (changeable) state of this dungeon to the writer passed.
  *
  * @param  w           the writer meant to write to the file.
  * @throws IOException if the I/O writing has a problem
  */
  void storeState(PrintWriter w) throws IOException {
    w.println(FILENAME_LEADER + getFilename());
    w.println(ROOM_STATES_MARKER);
    for (Room room : rooms.values()) {
      room.storeState(w);
    }
    w.println(TOP_LEVEL_DELIM);
  }

  /**
  * Loads the dungeon's layout from the save file.
  * Restore the (changeable) state of this dungeon to that reflected in the
  * reader passed.
  * @param  s                                    the scanner used to read through the file.
  * @throws GameState.IllegalSaveFormatException if at any point the file does not conform to the save file format.
  */
  void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

    // Note: the filename has already been read at this point.

    if (!s.nextLine().equals(ROOM_STATES_MARKER)) {
      throw new GameState.IllegalSaveFormatException("No '" +
      ROOM_STATES_MARKER + "' after dungeon filename in save file.");
    }

    String roomName = s.nextLine();
    while (!roomName.equals(TOP_LEVEL_DELIM)) {
      getRoom(roomName.substring(0,roomName.length()-1)).
      restoreState(s, this);
      roomName = s.nextLine();
    }
  }

  /**
  * Getter for the room the adventurer starts his or here
  * adventure in.
  * @return the room the adventurer starts in.
  */
  public Room getEntry() { return entry; }
  /**
  * Getter for the name of a {@link Room}.
  * Meant to be used any time the name of a room is needed.
  * @return the name of the room.
  */
  public String getName() { return name; }
  /**
  * Getter for the name of either the save or dungeon file.
  * @return the name of the dungeon file.
  */
  public String getFilename() { return filename; }
  /**
  * Adds a room to the Dungeon.
  * Normally meant to be used in conjunction with
  * the {@link Dungeon#Dungeon} creator.
  * @param room the room to be added to the dungeon.
  */
  public void add(Room room) { rooms.put(room.getTitle(),room); }
  /**
  * Adds an item to the Dungeon's list of items.
  * This does NOT add this item to any room or the
  * inventory, just the list of items.
  * Normally meant to be used in conjunction with
  * the {@link Dungeon#Dungeon} creator.
  * @param item the item to be added.
  */
  public void add(Item item) { items.put(item.getPrimaryName(),item); }
  /**
  * Getter for the Room specified in the parameter.
  * @param  roomTitle the name of the room meant to be returned.
  * @return           the room with the name given in the parameter.
  */
  public Room getRoom(String roomTitle) {
    return rooms.get(roomTitle);
  }

  /**
  * Gets the Item object whose primary name is passed.
  * The name passed should be the primary name and not an alias.
  * This has nothing to do with where the Adventurer might be,
  * or what's in his/her inventory, etc.
  * @param  primaryItemName      the primary name of the item.
  * @return                      the item being searched for.
  * @throws Item.NoItemException if this is thrown then there is no item with primaryItemName.
  */
  public Item getItem(String primaryItemName) throws Item.NoItemException {

    if (items.get(primaryItemName) == null) {
      throw new Item.NoItemException();
    }
    return items.get(primaryItemName);
  }
      public void VerboseExecute(boolean value){
            if(value == true){
                    for(String room: rooms.keySet()){
                            rooms.get(room).setBeenHere(false);
                            rooms.get(room).setVerbose(true);
                    }
        }
                else{
                        for(String room: rooms.keySet()){
                            rooms.get(room).setBeenHere(true);
                            rooms.get(room).setVerbose(false);
                }
    }
    }
        public Room Look(String find){
            System.out.println("test");
            return rooms.get(find);
    }

}
