/** This class will add an item that is taken by the user
 * to the user's inventory.
 * @author Alexander Loveland
 */
class TakeCommand extends Command {

    private String itemName;
/**Contructer which just sets the itemName instance variable to the itemName
 */
    TakeCommand(String itemName) {
        this.itemName = itemName;
    }
/** exectute returns a string which indicates if the item was taken,
 * if the item could not be taken because it execedes the adventurues allowed weight
 * or if the item does not exist in the current room
 */
    public String execute() {
        if (itemName == null || itemName.trim().length() == 0) {
            return "Take what?\n";
        }
        try {
            Room currentRoom =
                GameState.instance().getAdventurersCurrentRoom();
            Item theItem = currentRoom.getItemNamed(itemName);
            if (theItem.getWeight() +
                GameState.instance().getAdventurersCurrentWeight() > 40) {
                return "Your load is too heavy.\n";
            }
            GameState.instance().addToInventory(theItem);
            currentRoom.remove(theItem);
            return theItem.getPrimaryName() + " taken.\n";
        } catch (Item.NoItemException e) {
            // Check and see if we have this already. If no exception is
            // thrown from the line below, then we do.
            try {
                GameState.instance().getItemFromInventoryNamed(itemName);
                return "You already have the " + itemName + ".\n";
            } catch (Item.NoItemException e2) {
                return "There's no " + itemName + " here.\n";
            }
        }
    }
}
