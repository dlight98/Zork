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
    //System.out.println("Health: " + GameState.instance().getHealth() +"\n");  //DEBUG
    if(health >= 25) {
      return "You are in perfectly good health.\n";
    } else if(health >= 20) {
      return "You feel fine.\n";
    } else if(health >= 15) {
      return "You have a slight headache and are feeling a bit queezy.\n";
    } else if(health >= 10) {
      return "You have a pounding headache.\n";
    } else if(health >= 5) {
      return "Your breathing is ragged. You are unsure how much longer you can go on.\n";
    } else if(health > 0) {
      return "You are about to die.\n";
    } else {
      return "If you're getting this message I must've messed something up.\n-Nick Turner\n";
    }

  }
}
