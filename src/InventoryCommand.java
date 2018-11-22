
import java.util.ArrayList;

/**this command lists all the items in the adventurer's inventory
@author Alexander Loveland
 */
class InventoryCommand extends Command {

    InventoryCommand() {
    }
/** This method will return a string that lists all the itmes in the adventurues inventory by
 * cycling through all the items in the
 * adventurues inventory in the order they appear in the ArrayList created within this method
 */
    public String execute() {
        ArrayList<String> names = GameState.instance().getInventoryNames();
        if (names.size() == 0) {
            return "You are empty-handed.\n";
        }
        String retval = "You are carrying:\n";
        for (String itemName : names) {
            retval += "   A " + itemName + "\n";
        }
        return retval;
    }
}
