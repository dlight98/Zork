//temporary class

class LookCommand extends Command {
  
  LookCommand() {
    //not sure what do do with this
    //Don't need to take anything with it
  }

  String execute() {
    //Needs to get the current room from GameState
    //set beenHere to false
    //call Describe on the room
    
    Room tempR = new Room("Temporary room");  //temporary room
    tempR = GameState.instance().getAdventurersCurrentRoom(); //makes tempR the room
    tempR.setBeenHere(false);

    return "\n" +  tempR.describe() + "\n";
    }
}
