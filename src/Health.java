import java.util.ArrayList;

/**
  Contains all methods pertaining to the health of the adventurer.

  @author Nicholas Turner
*/
public class Health {

  /**
    Adds or removes health from an event to the player's current health.
    Calls on the GameState setHealth method.
    If health hits 0 it calls Die().
    @param wound the amount of damage a player takes.
  */
  void wound(int wound) { //maybe move to GameState?

    int temp = GameState.instance().getHealth();
    temp -= wound;

    if(temp >= 25) {
      temp = 25;
    }

    GameState.instance().setHealth(temp);
    if(GameState.instance().getHealth() <= 0) {
      Die();
      System.exit(0); //maybe?
    }

    GameState.instance().setHealth(temp);
  }

  /**
    Ends the game when Health hits 0.
    This is checked for every time wound() is called.
  */
  String Die() {
    return "Oh dear, you died!\nYour score is "
      + GameState.instance().getScore() + ".";
  }
}
