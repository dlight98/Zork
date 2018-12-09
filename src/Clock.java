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
  private int timePerDay=25;  /*says how many moves make up a day*/
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

  void addTime() {
    this.currentTime++;
    if (this.currentTime >= this.timePerDay) {
      Clock.instance().changeTimeOfDay();
    }
  }

  int getCurrentTime() {
    return currentTime;
  }

  int getDaysPassed() {
    return daysPassed;
  }

  boolean checkNight() {
    return night;
  }

  String changeTimeOfDay() {
    night = !night;
    if(night==false) {
      this.daysPassed++;
      if (this.daysPassed==this.timeLimit) {
        Clock.instance().outOfTime();
      }
      return "The sun has set. It is now night.";
    } else {
      return "Good morning! The " + this.daysPassed + " day has dawned.\n"
        +"There are " + (this.timeLimit - this.daysPassed) + " days left.";
    }
  }

  void outOfTime() {
    System.out.println("You hear a bell toll. You are out of time");
    Health.Die();
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
