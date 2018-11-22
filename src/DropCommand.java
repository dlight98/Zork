/** This type of command will remove an item from the adventurues inventory 
 */
class DropCommand extends Command {

    private String itemName;
/** Contrsucter which sets the itemName instance variable to the itemName
 */
    DropCommand(String itemName) {
        this.itemName = itemName;
    }
/** This method first checks if an item to drop was even specified
 * and if not, asks the user what he wants to drop
 * Or it will return a string indicating that the item was dropped
 * and if the item is not in the adventurer's inventory it will return a string saying 
 * the item is not there
 */
    public String execute() {
        if (itemName == null || itemName.trim().length() == 0) {
            return "Drop what?\n";
        }
        try {
            Item theItem = GameState.instance().getItemFromInventoryNamed(
                itemName);
            GameState.instance().removeFromInventory(theItem);
            GameState.instance().getAdventurersCurrentRoom().add(theItem);
            return theItem.getPrimaryName() + " dropped.\n";
        } catch (Item.NoItemException e) {
            return "You don't have a " + itemName + ".\n";
        }
    }
}
