/**
 * This class will first list the description of the class
 * then the command will list all the items that can be found in the room by cycling through the
 * room's Hashtable..
 *
 * @author Alexander Loveland
 */

class LookCommand extends Command {
/**
 * This class will first list the description of the class
 * then the command will list all the items that can be found in the room by cycling through the
 * room's Hashtable..
 *
 * @author Alexander Loveland
 */
    LookCommand() {
    }
/**
 * will return the sring that will list the description and the contents
 * of the room (all the items that are inside of it)
 * This method prints the response for the user to see
 */
    public String execute() {
      Clock.instance().addTime();

        Room currRoom = GameState.instance().getAdventurersCurrentRoom();
        return "\n" + currRoom.describe(true) + "\n";
    }
}
