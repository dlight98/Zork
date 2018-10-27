
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NoItemException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GameState() throws NoItemException { 

  public static class IllegalSaveFormatException extends Exception {
    public IllegalSaveFormatException(String e) {
      super(e);
    }
  }

  static String DEFAULT_SAVE_FILE = "zork_save";
  static String SAVE_FILE_EXTENSION = ".sav";
  static String SAVE_FILE_VERSION = "Zork II save data";

  static String CURRENT_ROOM_LEADER = "Current room: ";

<<<<<<< HEAD
    private static GameState theInstance;
    private Dungeon dungeon;
    private Room adventurersCurrentRoom;
    public ArrayList<Item> item;
    public GameState inventory;
=======
  private static GameState theInstance;
  private Dungeon dungeon;
  private Room adventurersCurrentRoom;
>>>>>>> efd8ab7a87849bebfec2428ed56ed96e3979ce4e

  static synchronized GameState instance() {
    if (theInstance == null) {
      theInstance = new GameState();
    }
    return theInstance;
  }

  private GameState() {
  }

  void restore(String filename) throws FileNotFoundException,
    IllegalSaveFormatException, Dungeon.IllegalDungeonFormatException {

    Scanner s = new Scanner(new FileReader(filename));

    if (!s.nextLine().equals(SAVE_FILE_VERSION)) {
      throw new IllegalSaveFormatException("Save file not compatible.");
    }

    String dungeonFileLine = s.nextLine();

    if (!dungeonFileLine.startsWith(Dungeon.FILENAME_LEADER)) {
      throw new IllegalSaveFormatException("No '" +
          Dungeon.FILENAME_LEADER + 
          "' after version indicator.");
    }

<<<<<<< HEAD
    public static class NoItemException(item) {
	   if(item == null){
		  System.out.println("Item not found.");
	   }
    } 

    void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
        adventurersCurrentRoom = dungeon.getEntry();
    }
=======
    dungeon = new Dungeon(dungeonFileLine.substring(
          Dungeon.FILENAME_LEADER.length()));
    dungeon.restoreState(s);
>>>>>>> efd8ab7a87849bebfec2428ed56ed96e3979ce4e

    String currentRoomLine = s.nextLine();
    adventurersCurrentRoom = dungeon.getRoom(
        currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));
    }

<<<<<<< HEAD
    void setAdventurersCurrentRoom(Room room) {
        adventurersCurrentRoom = room;
    }

    Dungeon getDungeon() {
        return dungeon;
    }
    
    String getInventoryNames(){ 
       inventory = ArrayList<Item> item; 
       return inventory;
    } 

    String addToInventory(Item item){ 
       inventory.add(item); 
    } 

    String removeFromInventory(Item item){ 
       inventory.remove(item); 
    } 
    
    String getItemInVicinityNamed(String name){ 
       return name; 
    } 
    
    String getItemFromInventoryNamed(String name){ 
       return name; 
    }
 
=======
  void store() throws IOException {
    store(DEFAULT_SAVE_FILE);
  }

  void store(String saveName) throws IOException {
    String filename = saveName + SAVE_FILE_EXTENSION;
    PrintWriter w = new PrintWriter(new FileWriter(filename));
    w.println(SAVE_FILE_VERSION);
    dungeon.storeState(w);
    w.println(CURRENT_ROOM_LEADER + 
        getAdventurersCurrentRoom().getTitle());
    w.close();
  }

  void initialize(Dungeon dungeon) {
    this.dungeon = dungeon;
    adventurersCurrentRoom = dungeon.getEntry();
  }

  Room getAdventurersCurrentRoom() {
    return adventurersCurrentRoom;
  }

  void setAdventurersCurrentRoom(Room room) {
    adventurersCurrentRoom = room;
  }

  Dungeon getDungeon() {
    return dungeon;
  }
>>>>>>> efd8ab7a87849bebfec2428ed56ed96e3979ce4e
}
