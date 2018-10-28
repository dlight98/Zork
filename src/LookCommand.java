//temporary class

class LookCommand extends Command {

  LookCommand() {
    //not sure what do do with this
    //Don't need to take anything with it
  }

  String execute() {

    Room tempR = new Room("Temporary room");  //temporary room
    tempR = GameState.instance().getAdventurersCurrentRoom(); //makes tempR the room
    tempR.setBeenHere(false);

    //TODO add the items currently in the room

    return "\n" +  tempR.describe() + "\n";
    }
}
