
class TakeCommand extends Command {
  String itemName;

  TakeCommand(String item){
    this.itemName = item;
  }

  String execute() throws NoItemException {

    //use the item name to search the room
    //once you get that, add it to the inventory
    //then remove from the room

    //if it says "take all" then take the whole room

    return null; //temporary
  }
}
