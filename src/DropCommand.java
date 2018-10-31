
class DropCommand extends Command {
  private String itemName;

  DropCommand(String item){
    this.itemName = item;
  }
  String execute() throws NoItemException {

    //use search to see if item is in inventory
    //if it is add it to the room
    //then remove from inventory


    return "Drop command successful\nDropped '" + this.itemName +"'\n";  //temporary
  }
}
