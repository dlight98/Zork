/** This command will preform an action specific to that comman
*
* @author Alecander Lovelandd
*/
class ItemSpecificCommand extends Command {

  private String verb;
  private String noun;

  /**Conrustor which verb and noun instance varaibles to the paramaters passe
  *
  * @param verb specific action preformed by object
  * @param noun noun that describes what the object isd
  */
  ItemSpecificCommand(String verb, String noun) {
    this.verb = verb;
    this.noun = noun;
  }
  /** This method returns a string whcih contains the verb from the item which has a specific command, and if the
  * item does not exist it will  catch an exception and return that the item does not exist
  */
  public String execute() {

    Item itemReferredTo = null;
    try {
      itemReferredTo = GameState.instance().getItemInVicinityNamed(noun);
    } catch (Item.NoItemException e) {
      return "There's no " + this.noun + " here.";
    }

    String msg = itemReferredTo.getMessageForVerb(this.verb);

    if (msg == null) {
      return "Sorry, you can't " + verb + " the " + noun + ".\n";
    } else if (itemReferredTo.hasEvent(this.verb) == true) { //TODO for events

      /*TODO
      0. Drop
      1. Wound/heal events
        1b. Die event
      2. Score events
        2b. Win events
      3.Transform
      4. Teleport

      Will need to continue running even if 1 thing already happened.
        Not else if.
       */


      return msg + ".\n"; //DEBUG This is from hasEvent!\n";

    } else {
      return msg + ".\n";
    }
  }
}
