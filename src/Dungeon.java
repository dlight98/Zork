import java.util.Hashtable;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class Dungeon {

  public static class IllegalDungeonFormatException extends Exception {
    public IllegalDungeonFormatException(String e) {
      super(e);
    }
  }

  // Variables relating to both dungeon file and game state storage.
  public static String TOP_LEVEL_DELIM = "===";
  public static String SECOND_LEVEL_DELIM = "---";

  // Variables relating to dungeon file (.zork) storage.
  public static String ITEMS_MARKER = "Items:";
  public static String ROOMS_MARKER = "Rooms:";
  public static String EXITS_MARKER = "Exits:";

  // Variables relating to game state (.sav) storage.
  static String FILENAME_LEADER = "Dungeon file: ";
  static String ROOM_STATES_MARKER = "Room states:";

  private String name;
  private Room entry;
  private Hashtable<String,Room> rooms;
  private Hashtable<String,Item> items; //primary name and item. used for hydration
  private String filename;

  public Dungeon(String filename, boolean initState){
    //TODO not sure how to use this yet
    if(initState == true){  //no save file
      //this = new Dungeon(filename); 
      //should to original dungeon file
    }
    //TODO make if there is a save file
  }

  Dungeon(String name, Room entry) throws FileNotFoundException, IllegalDungeonFormatException {
    init();
    this.filename = null;    // null indicates not hydrated from file.
    this.name = name;
    this.entry = entry;
    rooms = new Hashtable<String,Room>();
  }

  /**
  * Read from the .zork filename passed, and instantiate a Dungeon object
  * based on it.
  */
  public Dungeon(String filename) throws FileNotFoundException,
  IllegalDungeonFormatException {

    init();
    this.filename = filename;

    Scanner s = new Scanner(new FileReader(filename));
    name = s.nextLine();

    s.nextLine();   // Throw away version indicator.

    // Throw away delimiter.
    if (!s.nextLine().equals(TOP_LEVEL_DELIM)) {
      throw new IllegalDungeonFormatException("No '" +
      TOP_LEVEL_DELIM + "' after version indicator.");
    }

    // Throw away Rooms starter.
    if (!s.nextLine().equals(ROOMS_MARKER)) {
      throw new IllegalDungeonFormatException("No '" +
      ROOMS_MARKER + "' line where expected.");
    }


    try {
      // Instantiate and add first room (the entry).
      entry = new Room(s);
      add(entry);

      // Instantiate and add other rooms.
      while (true) {
        add(new Room(s));
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
        // (Note that the Exit constructor takes care of adding itself
        // to its source room.)
        Exit exit = new Exit(s, this);
      }
    } catch (Exit.NoExitException e) {  /* end of exits */ }

    s.close();
  }

  // Common object initialization tasks, regardless of which constructor
  // is used.
  private void init() {
    rooms = new Hashtable<String,Room>();
  }

  /*
  * Store the current (changeable) state of this dungeon to the writer
  * passed.
  */
  void storeState(PrintWriter w) throws IOException {
    w.println(FILENAME_LEADER + getFilename());
    w.println(ROOM_STATES_MARKER);
    for (Room room : rooms.values()) {
      room.storeState(w);
    }
    w.println(TOP_LEVEL_DELIM);
  }

  /*
  * Restore the (changeable) state of this dungeon to that reflected in the
  * reader passed.
  */
  void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

    // Note: the filename has already been read at this point.

    if (!s.nextLine().equals(ROOM_STATES_MARKER)) {
      throw new GameState.IllegalSaveFormatException("No '" +
      ROOM_STATES_MARKER + "' after dungeon filename in save file.");
    }

    String roomName = s.nextLine();
    while (!roomName.equals(TOP_LEVEL_DELIM)) {
      getRoom(roomName.substring(0,roomName.length()-1)).restoreState(s);
      roomName = s.nextLine();
    }
  }

  public Item getItem(String primaryName) throws NoItemException {
    //TODO working on this
    return null;
  }

  public void add(Item item) throws NoItemException {
    //TODO working on this too
  }

  public Room getEntry() { return entry; }
  public String getName() { return name; }
  public String getFilename() { return filename; }
  public void add(Room room) { rooms.put(room.getTitle(),room); }

  public Room getRoom(String roomTitle) {
    return rooms.get(roomTitle);
  }
} 
