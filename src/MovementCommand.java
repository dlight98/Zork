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

      //String time = Clock.instance().addTime();

        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
        Room nextRoom = currentRoom.leaveBy(dir);
	if(currentRoom.leaveBy(dir).getisDark() == true && GameState.instance().getLit() == false){
		return "This exit is closed because the room it leads to is Dark. Find a light source and turn it on to enter.\n";
	}
       	else if (nextRoom != null) {  // could try/catch here.
            GameState.instance().setAdventurersCurrentRoom(nextRoom);
            return Clock.instance().addTime() + "\n" + nextRoom.describe() + "\n";
        } else {
            return "You can't go " + dir + ".\n";
        }
    }
}
