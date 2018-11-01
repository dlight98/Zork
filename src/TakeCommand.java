
class TakeCommand extends Command {
  String itemName;

  TakeCommand(String item){
    this.itemName = item;
  }

String execute() throws NoItemException {

    /* for (Item itemOnList : GameState.instance().getAdventurersCurrentRoom().getContents) {
    if (itemOnList = this.itemName) {
    GameState.instance().inventory.add(itemOnList);
    GameState.instance().getAdventurersCurrentRoom().remove(itemOnList);
    return "Took " + itemOnList + ".";
  }
}*/
    String string = "";
    if(itemName.equals("")||itemName.equals(" ")){
    return "Take what?";
    } else if(itemName.equals("all")){
    try{
      for(Item item : GameState.instance().getAdventurersCurrentRoom().getContents()){
        GameState.instance().addToInventory(item);
        GameState.instance().getAdventurersCurrentRoom().remove(item);
        string += item.getPrimaryName() + " taken\n";
      }
    }catch (Exception e) { return "There are no items here.\n"; }
    return string;
    } else {
    try {
      //if (itemOnList.getPrimaryName = GameState.instance().getItemNamed(
      GameState.instance().addToInventory(GameState.instance().getAdventurersCurrentRoom().getItemNamed(itemName));
      string = itemName + " taken.\n";
    } catch (Exception e) { /*item not on list*/ }
    try {
      GameState.instance().getAdventurersCurrentRoom().remove(GameState.instance().getDungeon().getItem(itemName));
    } catch (Exception e) { /*item not in room*/ }

    try{
      for (Item itemOnList : GameState.instance().inventory) {
        if (itemOnList == GameState.instance().getDungeon().getItem(itemName)) {
          return "You already have the " + itemName + ".";
        }
      }
    } catch (Exception e) { /*empty*/ }

    return "There is no " + itemName + " here.\n";

    //return "Take command sucessful\nTook '" + this.itemName +"'\n"; //FIXME DEBUG
    }
    }
}
