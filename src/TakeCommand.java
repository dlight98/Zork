
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

    /* for (Item itemOnList : GameState.instance().getAdventurersCurrentRoom().getContents) {
       if (itemOnList = this.itemName) {
       GameState.instance().inventory.add(itemOnList);
       GameState.instance().getAdventurersCurrentRoom().remove(itemOnList);
       return "Took " + itemOnList + ".";
       }
       }*/
    String string = "";

    if(itemName.equals("all")){
      //TODO
      //loop thru list of items in room and add them to inventory
      //then loop and remove from room
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
