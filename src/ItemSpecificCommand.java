
class ItemSpecificCommand extends Command {
  private String verb;
  private String noun;

  ItemSpecificCommand(String action, String name){
    //action is the verb/command being called on the name, the item
    this.verb = action;
    this.noun = name;
  }

  String execute() {

    //check if the item exists
    //if it does check if it has the command
    //if it does then have it say the text in the message

    //if it doesn't exist then say either "verb what?"
    //or "I don't know how to verb"

    return null;  //temporary
  }
}
