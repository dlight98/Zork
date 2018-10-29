import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

class GameState {

  public static class IllegalSaveFormatException extends Exception {
    public IllegalSaveFormatException(String e) {
      super(e);
    }
  }

  class NoItemException extends Exception {
    public NoItemException(String e){
      if(item == null){
        System.out.println("Item not found.");
      }
    }
  }

  static String DEFAULT_SAVE_FILE = "zork_save";
  static String SAVE_FILE_EXTENSION = ".sav";
  static String SAVE_FILE_VERSION = "Zork II save data";

  static String CURRENT_ROOM_LEADER = "Current room: ";

  private static GameState theInstance;
  private Dungeon dungeon;
  private Room adventurersCurrentRoom;
  public ArrayList<Item> item;
  public ArrayList<Item> inventory;

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

    s.close();
  }

  void initialize(Dungeon dungeon, Scanner s) {
    this.dungeon = dungeon;
    adventurersCurrentRoom = dungeon.getEntry();

    dungeon = new Dungeon(dungeonFileLine.substring(
    Dungeon.FILENAME_LEADER.length()));
    this.dungeon.restoreState(s);

    String currentRoomLine = s.nextLine();
    adventurersCurrentRoom = dungeon.getRoom(
    currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));
  }


  void setAdventurersCurrentRoom(Room room) {
    this.adventurersCurrentRoom = room;
  }

  Dungeon getDungeon() {
    return dungeon;
  }

  ArrayList<Item> getInventory(){
    inventory = this.inventory;
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

  

  Room getAdventurersCurrentRoom() {
    return adventurersCurrentRoom;
  }

  
}
