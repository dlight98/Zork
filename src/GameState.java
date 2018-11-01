import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class GameState {

  public static class IllegalSaveFormatException extends Exception {
    public IllegalSaveFormatException(String e) {
      super(e);
    }
  }

 /* public class NoItemException extends Exception {
    public NoItemException(String e) {
      if(item == null) {
        System.out.println("Item not found.");
      }
    }
  }*/

  static String DEFAULT_SAVE_FILE = "zork_save";
  static String SAVE_FILE_EXTENSION = ".sav";
  static String SAVE_FILE_VERSION = "Zork II save data";

  static String CURRENT_ROOM_LEADER = "Current room: ";

  private static GameState theInstance;
  private Dungeon dungeon;
  private Room adventurersCurrentRoom;
  private Item vicinity;
  public ArrayList<Item> item;
  public ArrayList<Item> inventory;

  static synchronized GameState instance() {
    if (theInstance == null) {
      theInstance = new GameState();
    }
    return theInstance;
  }

  private GameState() { }

  void restore(String filename) throws FileNotFoundException,
      IllegalSaveFormatException, Dungeon.IllegalDungeonFormatException {

    Scanner s = new Scanner(new FileReader(filename));

    if (!s.nextLine().equals(SAVE_FILE_VERSION)) {
      throw new IllegalSaveFormatException("Save file not compatible.");
    }

    String dungeonFileLine = s.nextLine();

    if (!dungeonFileLine.startsWith(Dungeon.FILENAME_LEADER)) {
      throw new IllegalSaveFormatException("No '"
      + Dungeon.FILENAME_LEADER
      + "' after version indicator.");
    }

    dungeon = new Dungeon(dungeonFileLine.substring(
    Dungeon.FILENAME_LEADER.length()));
    this.dungeon.restoreState(s);

    String currentRoomLine = s.nextLine();
    adventurersCurrentRoom = dungeon.getRoom(
    currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));

  }

  void initialize(Dungeon d) {
    this.dungeon = d;
    adventurersCurrentRoom = dungeon.getEntry();
  }

  void initialize(Dungeon dungeon, Scanner s) {
    this.dungeon = dungeon;
    adventurersCurrentRoom = dungeon.getEntry();

  }

  void setAdventurersCurrentRoom(Room room) {
    this.adventurersCurrentRoom = room;
  }

  Dungeon getDungeon() {
    return dungeon;
  }

  ArrayList<Item> getInventory() {
    inventory = this.inventory;
    return inventory;
  }

  void addToInventory(Item item) {
    inventory.add(item);
  }

  void removeFromInventory(Item item) {
    inventory.remove(item);
  }

  Item getItemInVicinityNamed(String name) {
    //need to get the item named from the room

    //should be like:
    //getAdventurerCurrentRoom then get items in room
    //add do something with that item
    
    

    return vicinity;
  }

  ArrayList<Item> getItemFromInventoryNamed(String name) throws NoItemException {
    //need to get the item name this from inventory


    //should be like:
    //get inventory and cycles thru items
    //do something with that item
    for(Item item:inventory) {
      
    }
    
    return null; //FIXME(Item itemOnList : GameState.instance().getAdventurersCurrentRoom().getContents) 
  }

  void store() throws IOException {
    store(DEFAULT_SAVE_FILE);
  }

  void store(String saveName) throws IOException {
    String filename = saveName + SAVE_FILE_EXTENSION;
    PrintWriter w = new PrintWriter(new FileWriter(filename));
    w.println(SAVE_FILE_VERSION);
    dungeon.storeState(w);
    w.println(CURRENT_ROOM_LEADER + getAdventurersCurrentRoom().getTitle());
    w.close();
  }

  Room getAdventurersCurrentRoom() {
    return adventurersCurrentRoom;
  }

}
