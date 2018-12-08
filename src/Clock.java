/**
This class runs the external time keeping system. It is a singleton
that keeps track of the time and adds it to the <tt>{@link GameState}</tt>.
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
}
