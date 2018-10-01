
// For now, only direction commands. If the "direction" is bogus, then this
// effectively doubles as an UnknownCommand (to be a subclass later).
public class Command {

    private String dir;     // for now, this class is only for direction 
                            // commands.

    Command(String dir) {
        this.dir = dir;
    }

    public String execute() {
        if (CommandFactory.MOVEMENT_COMMANDS.contains(dir)) {
            Room currentRoom = 
                GameState.instance().getAdventurersCurrentRoom();
            Room nextRoom = currentRoom.leaveBy(dir);
            if (nextRoom != null) {  // could try/catch here.
                GameState.instance().setAdventurersCurrentRoom(nextRoom);
                return "\n" + nextRoom.describe() + "\n";
            } else {
                return "Sorry, you can't go " + dir + " from " +
                    currentRoom.getTitle() + ".\n";
            }
        }
        return "I'm sorry, I don't understand the command '" + dir + "'.\n";
    }
}
