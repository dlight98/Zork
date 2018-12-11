import java.util.ArrayList;
//import java.io.NoItemException;

/** This command will preform an action specific to that comman
*
* @author Alecander Lovelandd
*/
class ItemSpecificCommand extends Command {

  private String verb;
  private String noun;

  /**Conrustor which verb and noun instance varaibles to the paramaters passed.
  *
  * @param verb specific action preformed by object.
  * @param noun noun that describes what the object is.
  */
  ItemSpecificCommand(String verb, String noun) {
    this.verb = verb;
    this.noun = noun;
  }
  /** This method returns a string whcih contains the verb from the item which has a specific command, and if the
  * item does not exist it will  catch an exception and return that the item does not exist
  */
  public String execute() throws NoItemException {

    String returnVal = "";  //value returned

    Item itemReferredTo = null;
    try {
      itemReferredTo = GameState.instance().getItemInVicinityNamed(noun);
    } catch (Item.NoItemException e) {
      return "There's no " + this.noun + " here.\n";
    }

    String msg = itemReferredTo.getMessageForVerb(this.verb);

    if (msg == null) {
      return "Sorry, you can't " + verb + " the " + noun + ".\n";
    } else if (itemReferredTo.hasEvent(this.verb) == true) {

      ArrayList<String> actions = new ArrayList(itemReferredTo.getEventForVerb(verb));

      for (String action : actions) {
        //System.out.println(action + "debug.\n"); //DEBUG
        if (action.contains("Drop")) {
          //System.out.println("Contains Drop."); //DEBUG
          new DropCommand(itemReferredTo.toString()).execute();  //maybe wrong
        } else if (action.contains("Disappear")) {
          //System.out.println("Contains Disappear."); //DEBUG
          new Disappear(itemReferredTo).removeFromInventory(itemReferredTo);
        } else if (action.contains("Wound")) {
          String[] number = action.split("\\(");
          int wound = Integer.parseInt(number[1].substring(0, number[1].length()-1));
          returnVal = Health.wound(wound) + "\n";
          //System.out.println("Contains Wound. Took " + wound + " damage."); //DEBUG
        } else if (action.contains("Die")) {
          returnVal = Health.wound(10000000) + "\n"; //Maybe not the most elegant way, but it works!
          //System.out.println("Contains Die."); //DEBUG
        } else if (action.contains("Score")) {
          //System.out.println("Contains Score."); //DEBUG
          String[] number = action.split("\\(");
          int points = Integer.parseInt(number[1].substring(0, number[1].length()-1));
          GameState.instance().addToScore(points);
          //System.out.println("points: " +points);  //DEBUG
          //System.out.println("Total points: " +GameState.instance().getScore());  //DEBUG

        } else if (action.contains("Win")) {
          //System.out.println("Contains Win."); //DEBUG
          returnVal = Score.Win();

        } else if (action.contains("Transform")) {
          //System.out.println("Contains Transform."); //DEBUG
          try {
            String[] parts = action.split("\\(");
            String itemName = parts[1].substring(0, parts[1].length()-1);
            Item itemTo = GameState.instance().getDungeon().getItem(itemName);
            new Transform(itemReferredTo, itemTo).execute();
          }catch (Item.NoItemException e) {
            throw new NoItemException("This item doesnt exits");
          }
        } else if (action.contains("Teleport")) {
          //System.out.println("Contains Teleport.");  //DEBUG

        } else if (action.contains("Power")) {
          //System.out.println("Contains Power.");  //DEBUG
        }
        else {
          System.out.println("contains something else idk. if you make it here I messed up bad.\n"); //DEBUG
        }

      }


      if(returnVal.contains("Oh dear,")){
        System.out.println(msg + "\n" + returnVal);
        System.exit(0);
        return null;  //required return value
      } else if (returnVal.contains("Congr")){
        System.out.println(msg + "\n" + returnVal);
        System.exit(0);
        return null;  //required return value
      } else {
        return Clock.instance().addTime() + msg + ".\n" + returnVal; //This is from hasEvent!\n";  //DEBUG
      }
    } else {
      return Clock.instance().addTime() + msg + ".\n" + returnVal;
    }
  }
}
