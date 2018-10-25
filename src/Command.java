/* TODO (From Nick)
*Make command class abstract
*Make subclasses
*Have the "LookCommand" made to set beenHere to false
*/

// For now, only direction commands and "save". If the "direction" is bogus,
// then this effectively doubles as an UnknownCommand (to be a subclass
// later).
abstract class Command {

    //private String dir;     // for now, this class is only for direction
                            // commands, plus "save" which is kind of a weird
                            // special case.

    // Command(String dir) {
    //     this.dir = dir;
    // }

    abstract String execute(); //{
      //commenting everything out in case I need it later - Nick

    //     if (dir.equals("save")) {
    //         try {
    //             GameState.instance().store();
    //             return "Data saved to " + GameState.DEFAULT_SAVE_FILE +
    //                 GameState.SAVE_FILE_EXTENSION + ".\n";
    //         } catch (Exception e) {
    //             System.err.println("Couldn't save!");
    //             e.printStackTrace();
    //             return "";
    //         }
    //     } else if (CommandFactory.MOVEMENT_COMMANDS.contains(dir)) {
    //         Room currentRoom =
    //             GameState.instance().getAdventurersCurrentRoom();
    //         Room nextRoom = currentRoom.leaveBy(dir);
    //         if (nextRoom != null) {  // could try/catch here.
    //             GameState.instance().setAdventurersCurrentRoom(nextRoom);
    //             return "\n" + nextRoom.describe() + "\n";
    //         } else {
    //             return "Sorry, you can't go " + dir + " from " +
    //                 currentRoom.getTitle() + ".\n";
    //         }
    //     }
    //     return "I'm sorry, I don't understand the command '" + dir + "'.\n";
    //}
}
