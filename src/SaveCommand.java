//Temporary

class SaveCommand extends Command {
  private String saveFilename;

  SaveCommand() {
    //not sure what to do with this yet

    //this.saveFilename = filename;
  }

  String execute() {
    //Copied this from the original Command.java
    //not sure if I should change DEFAULT_SAVE_FILE yet
    try {
        GameState.instance().store();
        return "Data saved to " + GameState.DEFAULT_SAVE_FILE +
            GameState.SAVE_FILE_EXTENSION + ".\n";
    } catch (Exception e) {
        System.err.println("Couldn't save!");
        e.printStackTrace();
        return "";
      }
  }
}
