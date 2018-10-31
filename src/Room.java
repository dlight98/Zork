
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
public class Room {

    class NoRoomException extends Exception {}

    private String title;
    private String desc;
    private boolean beenHere;
    private ArrayList<Exit> exits; 
    Room(String title) {
        init();
        this.title = title;
	}
  private ArrayList<Item>contents;

  /** Given a Scanner object positioned at the beginning of a "room" file
    entry, read and return a Room object representing it. 
    @throws NoRoomException The reader object is not positioned at the
    start of a room entry. A side effect of this is the reader's cursor
    is now positioned one line past where it was.
    @throws IllegalDungeonFormatException A structural problem with the
    dungeon file itself, detected when trying to read this room.
   */

  Room(Scanner s, Dungeon d) throws NoRoomException, Dungeon.IllegalDungeonFormatException {
    this(s, d, true);
  }

  Room(Scanner s) throws NoRoomException,
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
        desc += lineOfDesc + "\n";
        lineOfDesc = s.nextLine();
      }

      // throw away delimiter
      if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
        throw new Dungeon.IllegalDungeonFormatException("No '" +
            Dungeon.SECOND_LEVEL_DELIM + "' after room.");
      }
    }

  // Common object initialization tasks.
  private void init() {
    exits = new ArrayList<Exit>();
    beenHere = false;
  }

  String getTitle() { return title; }

  void setDesc(String desc) { this.desc = desc; }

  /*
   * Store the current (changeable) state of this room to the writer
   * passed.
   */
  void storeState(PrintWriter w) throws IOException {
    // At this point, nothing to save for this room if the user hasn't
    // visited it.
    if (beenHere) {
      w.println(title + ":");
      w.println("beenHere=true");
      w.println(Dungeon.SECOND_LEVEL_DELIM);
    }
  }
    Room(Scanner s, Dungeon d, boolean initState) throws NoRoomException, Dungeon.IllegalDungeonFormatException{
	    	if(initState == false){
			s.nextLine();
			s.nextLine();
			String value = "";
			if(!s.nextLine().equals(Dungeon.ROOM_STATES_MARKER)){
				throw new Dungeon.IllegalDungeonFormatException( "wrong format");
			}
			else{

			String sTitle =  s.nextLine();
			this.title = sTitle.substring(0, sTitle.length() - 2);
		        value =  s.nextLine();
			}
			if(value.substring(8, value.length() - 1).equals("true")){
				this.beenHere = true;
			}
			else{
				this.beenHere = false;
			}
			 String sContent = s.nextLine();
			 int Start = 9;
			 int End = sContent.indexOf(",");
			 String inBetween = "";
			 while(true){
			try{
				inBetween = sContent.substring(Start, End);
				contents.add(GameState.instance().getDungeon().getItem(inBetween));
				Start = End;
				End = sContent.substring(Start, sContent.length() - 1).indexOf(",");
			}
			catch(Exception e){
				break;

			}
		}
		}
		if(initState = true){
			s.nextLine();
			s.nextLine();
			s.nextLine();
			if(!s.nextLine().equals("Items:")){
				throw new Dungeon.IllegalDungeonFormatException("wrong format");
		}
    }
    }

  void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

    String line = s.nextLine();
    if (!line.startsWith("beenHere")) {
      throw new GameState.IllegalSaveFormatException("No beenHere.");
    }
    beenHere = Boolean.valueOf(line.substring(line.indexOf("=")+1));

    s.nextLine();   // consume end-of-room delimiter
  }

  public String describe() {
    String description;
    if (beenHere) {
      description = title;
    } else {
      description = title + "\n" + desc;
    }
    for (Exit exit : exits) {
      description += "\n" + exit.describe();
    }
    beenHere = true;
    return description;
  }

  public Room leaveBy(String dir) {
    for (Exit exit : exits) {
      if (exit.getDir().equals(dir)) {
        return exit.getDest();
      }
    }
    return null;
  }

  void addExit(Exit exit) {
    exits.add(exit);
  }

  void setBeenHere(boolean beenHere ) {
    this.beenHere = beenHere;
  }
public static void main(String[]args){
	Scanner tester = null;
	Room work = null; 
	Dungeon UC = null;
	try{
		 tester = new Scanner(new FileReader(args[0]));
	}
	catch(FileNotFoundException e){
		System.out.println("No file");
	}
	try{
		work = new Room(tester, UC, false);}
				
	catch(Dungeon.IllegalDungeonFormatException e){
	}
	catch(NoRoomException e){}
	try{
             UC = new Dungeon("UC", work);
	}
	catch(FileNotFoundException e){}
	catch(Dungeon.IllegalDungeonFormatException e){}
	System.out.println(work.describe());
	

}
}
