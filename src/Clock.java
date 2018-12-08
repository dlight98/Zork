/**
  This class runs the external time keeping system. It is a singleton
  that keeps track of the time and adds it to the <tt>{@link GameState}</tt>.
  @author Nick Turner
*/
class Clock {
  private static Clock theInstance;
  private int movesForDay;  //says how many moves make up a day
  private int timeLimit;  //The time limit for the day 
  
  static synchronized Class instance() {
    if(theInstance==null) {
      theInstance = new Clock();
    }
    return theInstance;
  }

  private Clock() {
    //TODO not sure what to put here yet
  }
  
  
}
