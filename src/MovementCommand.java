//temporary class

class MovementCommand extends Command {
  private String dir;

  MovementCommand(String dir) {
    //should set this.dir to the given direction command
    this.dir = dir;
  }

  String execute() {
    //Copied from Command.java
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
}
