import java.util.ArrayList;

/**
  Contains all methods pertaining to the health of the adventurer.

  @author Nicholas Turner
*/

public class Health {

  /**
   * Default constructor for <tt>Health</tt>.
   */
  public Health(){}


  /**
    Adds or removes health from an event to the player's current health.
    Calls on the GameState setHealth method.
    If health hits 0 it calls Die().
    @param wound the amount of damage a player takes.
  */
  public static String wound(int wound) { //FIXME probably not static. also needs string to work

    int temp = GameState.instance().getHealth();
    temp -= wound;

    if(temp >= 50) {
      temp = 50;
    }

    GameState.instance().setHealth(temp);

    if(temp <= 0) {
      return Health.Die();
      //System.exit(0); //maybe?
    }

    //return "didnt die"; //DEBUG
    return ""; //required to remove
  }

  /**
    Ends the game when Health hits 0.
    This is checked for every time wound() is called.
  */
  public static String Die() {  //FIXME maybe not static
    return ("Oh dear, you died!\nYour score is "
      + GameState.instance().getScore() + ".");
      //System.exit(0);
      //return "";
  }
}
