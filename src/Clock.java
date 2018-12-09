import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

/**
The <tt>Clock</tt> is a singleton that runs the external time keeping system.
It keeps track of the time, the days passed, the time limit, and if it is night.
@author Nick Turner
*/
public class Clock {
  private static Clock theInstance;
  private int timePerDay=5; //FIXME /*says how many moves make up a day*/
  private int timeLimit=-1;  /**the number of days before the game ends.*/
  private int daysPassed=0; /** how many days have passed.*/
  private int currentTime=0;  /** the current time.*/
  private boolean night=false;  /**false is day, true is night*/

  static synchronized Clock instance() {
    if(theInstance==null) {
      theInstance = new Clock();
    }
    return theInstance;
  }

  private Clock() {
    //TODO not sure what to put here yet
    this.currentTime=0;
    this.night=false;
  }

  String addTime() {
    this.currentTime++;
    if (this.currentTime >= this.timePerDay) {
      currentTime = 0;
      return Clock.instance().changeTimeOfDay();
    } else {
      return "";
    }
  }

  void init(Scanner s) throws
  Dungeon.IllegalDungeonFormatException {

    String line = s.nextLine();
    if(line.startsWith("TimeLimit")){
      String[] lineParts = line.split(":");
      int number = Integer.parseInt(lineParts[1]);
      this.timeLimit = number;
      //System.out.println("You have " + number + " days to win.");
    } else {
      //TODO maybe something here?
    }
  }

  int getCurrentTime() {
    return currentTime;
  }

  int getDaysPassed() {
    return daysPassed;
  }

  int getTimeLimit() {
    return timeLimit;
  }

  boolean checkNight() {
    return night;
  }

  String changeTimeOfDay() {
    String retVal = "";
    night = !night;
    if(night==true) {
      return "\nThe sun has set. It is now night.\n";
    } else {
      this.daysPassed++;
      if (daysPassed>timeLimit && timeLimit>0) {
        return Clock.instance().outOfTime();
      }
      if(daysPassed+1 >= timeLimit){
        return "Dawn of the Final Day.";
      }else{
        return "\nGood morning! A new day has dawned\n"
        + this.daysPassed + " day(s) have passed.\n" +
        "There are " + (this.timeLimit - this.daysPassed +1) + " days left.\n";
      }
    }

  }

  String outOfTime() {
    System.out.println("\nYou hear a bell toll. You are out of time.\n");
    System.out.println(Health.Die());
    System.exit(0);
    return "";
  }

  void storeState(PrintWriter w) throws IOException {
    w.println("DaysPassed:" + this.daysPassed);
    w.println("Time:" + this.currentTime);
    w.println("Night:" + this.night);
  }

  void restoreState(Scanner s) throws
  GameState.IllegalSaveFormatException {

    String line = s.nextLine();
    int number; //the number after the :
    boolean n;  //chacks for night
    if (!line.startsWith("DaysPassed")) {
      throw new GameState.IllegalSaveFormatException("No daysPassed.");
    } else {
      String[] lineParts = line.split(":");
      number = Integer.parseInt(lineParts[1]);
      this.daysPassed = number;
    }
    line = s.nextLine();
    if (!line.startsWith("Time")) {
      throw new GameState.IllegalSaveFormatException("No time.");
    } else {
      String[] lineParts = line.split(":");
      number = Integer.parseInt(lineParts[1]);
      this.currentTime = number;
    }
    line = s.nextLine();
    if (!line.startsWith("Night")) {
      throw new GameState.IllegalSaveFormatException("No night.");
    } else {
      String[] lineParts = line.split(":");
      n = Boolean.parseBoolean(lineParts[1]);
      this.night = n;
    }
  }

}
