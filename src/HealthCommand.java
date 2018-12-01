/**
  Returns a fuzzy value of the amount of health a player has.

  @author Nicholas Turner
*/

class HealthCommand extends Command {
  private int health;

  /**
  Default constructor for <tt>HealthCommand</tt>.
  */
  HealthCommand(){


  }

  public String execute(){
    this.health = GameState.instance().getHealth();

    if(health >= 25) {
      return "You are in perfectly good health.";
    } else if(health >= 20) {
      return "You feel fine.";
    } else if(health >= 15) {
      return "You have a slight headache and are feeling a bit queezy.";
    } else if(health >= 10) {
      return "You have a pounding headache.";
    } else if(health >= 5) {
      return "Your breathing is ragged. You are unsure how much longer you can go on.";
    } else if(health > 0) {
      return "You are about to die.";
    } else {
      return "If you're getting this message I must've messed something up.\n-Nick Turner";
    }

  }
}
