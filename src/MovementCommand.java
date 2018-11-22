/**
 * This command changes the adventurues current room.
 * @author Alexander Loveland
 */
class MovementCommand extends Command {

    private String dir;

/**Contructor that just sets the direction instance varaible to the direction
 * direction listed as the paramater passed
 *
 * @param dir direction to move
 */
    MovementCommand(String dir) {
        this.dir = dir;
    }
/** This method returns a string that indicates if the player successfully moved to the next
 * room by stating the new current room's describe strinng, and if the player moves in the wrong
 * direction the string indicares that the player cannot move there
 */
    public String execute() {
        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
        Room nextRoom = currentRoom.leaveBy(dir);
        if (nextRoom != null) {  // could try/catch here.
            GameState.instance().setAdventurersCurrentRoom(nextRoom);
            return "\n" + nextRoom.describe() + "\n";
        } else {
            return "You can't go " + dir + ".\n";
        }
    }
}
