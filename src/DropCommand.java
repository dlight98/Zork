
class DropCommand extends Command {
  private String itemName;

  DropCommand(String item){
    this.itemName = item;
  }

  String execute() throws NoItemException {

    String string = "";
    if(itemName.equals("")||itemName.equals(" ")){
      return "Drop what?\n";
    } else if(itemName.equals("all")){
      try{
        for(Item item : GameState.instance().getInventory()){
          GameState.instance().removeFromInventory(item);
          GameState.instance().getAdventurersCurrentRoom().add(item);
          string += item.getPrimaryName() + " dropped.\n";
        }
      }catch (Exception e) { return "You are empty-handed.\n"; }
      return string;
    } else {
      try {
        //if (itemOnList.getPrimaryName = GameState.instance().getItemNamed(
        GameState.instance().removeFromInventory(GameState.instance().getDungeon().getItem(itemName));
        string = itemName + " dropped.\n";
      } catch (Exception e) { string = "You don't have this item.\n"; }
      try {
        GameState.instance().getAdventurersCurrentRoom().add(GameState.instance().getDungeon().getItem(itemName));
      } catch (Exception e) { return string; }

      return string;
      //return "Drop command successful\nDropped '" + this.itemName +"'\n";  //FIXME DEBUG
    }
  }
}
