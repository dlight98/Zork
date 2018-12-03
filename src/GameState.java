import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
* The <tt>GameState</tt> class updates the state of the game whenever
* an event causes it to change in some way.
* Interacts with the ExternalClock, Room, Exit,
* Transform, Teleport, CommandFactory, and VerboseCommand classes.
* @author Benjamin Madren
*/
public class GameState {

  public static class IllegalSaveFormatException extends Exception {
    public IllegalSaveFormatException(String e) {
      super(e);
    }
  }

  static String SAVE_FILE_EXTENSION = ".sav";
  static String SAVE_FILE_VERSION = "Zork III save data";

  static String ADVENTURER_MARKER = "Adventurer:";
  static String CURRENT_ROOM_LEADER = "Current room: ";
  static String INVENTORY_LEADER = "Inventory: ";

  private static GameState theInstance;
  private Dungeon dungeon;
  private ArrayList<Item> inventory;
  private Room adventurersCurrentRoom;
  private int currentHealth=25; //FIXME this is temporary for now
  private int score=0;  //FIXME temporary for testing.
  static String[] ranks = {"Apprentice", "Knight", "Earl", "Duke", "Prince", "King", "Emperor"};


  /**This method creates a new instance of the GameState class.**/
  static synchronized GameState instance() {
    if (theInstance == null) {
      theInstance = new GameState();
    }
    return theInstance;
  }

  private GameState() {
    inventory = new ArrayList<Item>();
  }
  /**This method returns the adventurer's current weight
  * based on the items in their inventory.**/
  int getAdventurersCurrentWeight() {
    int total = 0;
    for (Item item : inventory) {
      total += item.getWeight();
    }
    return total;
  }
  /**This method restores the player's game
  * from a save file when the game restarts.
  * @param filename **/
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

    dungeon = new Dungeon(dungeonFileLine.substring(
    Dungeon.FILENAME_LEADER.length()), false);
    dungeon.restoreState(s);

    s.nextLine();  // Throw away "Adventurer:".
    String currentRoomLine = s.nextLine();
    adventurersCurrentRoom = dungeon.getRoom(
    currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));
    if (s.hasNext()) {
      String inventoryList = s.nextLine().substring(
      INVENTORY_LEADER.length());
      String[] inventoryItems = inventoryList.split(",");
      for (String itemName : inventoryItems) {
        try {
          addToInventory(dungeon.getItem(itemName));
        } catch (Item.NoItemException e) {
          throw new IllegalSaveFormatException("No such item '" +
          itemName + "'");
        }
      }

    }
    //TODO this is restoring health
    if (s.hasNext()) {
      String healthS = s.nextLine();
      String[] healthParts = healthS.split(":");
      int health = Integer.parseInt(healthParts[1]);
      GameState.instance().setHealth(health);
    }
    if(s.hasNext()){
	    String ScoreS = s.nextLine();
	    String[] ScoreParts = ScoreS.split(":");
	    int Score = Integer.parseInt(ScoreParts[1]);
	    GameState.instance().setScore(Score);

    //TODO add score here
  }
 }
  /**This method stores the player's information
  * into a save file.
  * @param saveName **/
  void store(String saveName) throws IOException {
    String filename = saveName + SAVE_FILE_EXTENSION;
    PrintWriter w = new PrintWriter(new FileWriter(filename));
    w.println(SAVE_FILE_VERSION);
    dungeon.storeState(w);
    w.println(ADVENTURER_MARKER);
    w.println(CURRENT_ROOM_LEADER + adventurersCurrentRoom.getTitle());
    if (inventory.size() > 0) {
      w.print(INVENTORY_LEADER);
      for (int i=0; i<inventory.size()-1; i++) {
        w.print(inventory.get(i).getPrimaryName() + ",");
      }
      w.println(inventory.get(inventory.size()-1).getPrimaryName());
    }

    w.println("Health:" + this.currentHealth);
    w.println("Score:" + this.score);
    //TODO add score here
    w.close();
  }
  void setScore(int Score){
	  Score = score;
  }
	  
  /**This method creates a new dungeon
  * and sets the player in the entry room.
  * @param dungeon **/
  void initialize(Dungeon dungeon) {
    this.dungeon = dungeon;
    adventurersCurrentRoom = dungeon.getEntry();
  }
  /**This method returns the names of each item
  * in the player's inventory from an array list.**/
  ArrayList<String> getInventoryNames() {
    ArrayList<String> names = new ArrayList<String>();
    for (Item item : inventory) {
      names.add(item.getPrimaryName());
    }
    return names;
  }
  /**This method adds a new item to the player's inventory.
  * @param item **/
  void addToInventory(Item item) /* throws TooHeavyException */ {
    inventory.add(item);
  }
  /**This method removes the selected item from the player's inventory.**/
  void removeFromInventory(Item item) {
    inventory.remove(item);
  }
  /**This method returns the names of the items
  * in the player's inventory
  * and the room the player is currently in,
  * throwing a no item exception as necessary.
  * @param name **/
  Item getItemInVicinityNamed(String name) throws Item.NoItemException {

    // First, check inventory.
    for (Item item : inventory) {
      if (item.goesBy(name)) {
        return item;
      }
    }

    // Next, check room contents.
    for (Item item : adventurersCurrentRoom.getContents()) {
      if (item.goesBy(name)) {
        return item;
      }
    }

    throw new Item.NoItemException();
  }
  /**This method checks specific items in the player's inventory.**/
  Item getItemFromInventoryNamed(String name) throws Item.NoItemException {

    for (Item item : inventory) {
      if (item.goesBy(name)) {
        return item;
      }
    }
    throw new Item.NoItemException();
  }
  /**This method checks what room the player is currently in.**/
  Room getAdventurersCurrentRoom() {
    return adventurersCurrentRoom;
  }
  /**This method updates the player's current room.**/
  void setAdventurersCurrentRoom(Room room) {
    adventurersCurrentRoom = room;
  }
  /**This method checks what dungeon the player is currently in.**/
  Dungeon getDungeon() {
    return dungeon;
  }
  /** This method sets the current time of day for the given dungeon.
  * (0 for day, 1 for night).
  */
  void checkTime(){

  }
  /** This method changes the time of day from day to night and vice versa
  * whenever the player has made five moves.
  * @param day
  */
  void timeOfDay(){

  }
  /** This method implements a time limit of ten days
  * with which the player has to complete the current dungeon.
  * @param limit
  */
  void checkTimeLimit(){

  }
  /** This method ends the game once the time limit is reached.
  * @param day,limit
  */
  void gameEnd(){

  }
  /**This method sets the player's current health.**/
  void setHealth(int health){
    this.currentHealth = health;
  }
  /**This method returns the player's current health.**/
  int getHealth(){
    return currentHealth;
  }

  int getScore(){
    return score;
  }
  void addToScore(){
	  score = score + 1;
  }
  void addToScore(int amount){
	  score = score + amount;
  }
  String getRank(){
	 if(score <= 5){
                return ranks[0];
        }
        if(score > 5 && score <= 10){
                return ranks[1];
        }
        if(score > 10 && score >= 15){
                return ranks[2];
        }
        if(score > 15 && score >= 20){
                return ranks[3];
        }
        if(score > 25 && score >= 30){
                return ranks[4];
        }
        if(score > 30 && score >= 35){
                return ranks[5];
        }
        if(score > 35 && score >= 40){
                return ranks[6];
        }
        else{
                return ranks[0];
        }
  }
}
