//Temporary class

class UnknownCommand extends Command {
  private String bogusCommand;

  UnknownCommand(String command) {
    //not sure what to do with this yet
    this.bogusCommand = command;
  }

  String execute(){
    //copied from Command.java
    //not sure if this is completely right
    return "I'm sorry, I don't understand the command '" + bogusCommand + "'.\n";
  }
}
