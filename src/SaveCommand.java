
/** This class will create a file with a .zork extension
 * and print all the current game data to the file,
 * including inventory items, current room, and setting
 * all the beenHere varaibles for the rooms to true or
 * false.
 * @author Alexander Loveland
 * **/
class SaveCommand extends Command {

    private static String DEFAULT_SAVE_FILENAME = "zork";

    private String saveFilename;
/** will check if the sav file has been giving a name
 * and if it has not it will give the file a default name
 * then it sets the saveFilgename instance varable to files name 
 * @param saveFilename name of the file that the game is going to save to7
 */
    SaveCommand(String saveFilename) {
        if (saveFilename == null || saveFilename.length() == 0) {
            this.saveFilename = DEFAULT_SAVE_FILENAME;
        } else {
            this.saveFilename = saveFilename;
        }
    }
/**This method returns the string that other classes will use to
 * print hte appropiate text to the user
 */
    public String execute() {
        try {
            GameState.instance().store(saveFilename);
            return "Data saved to " + saveFilename +
                GameState.SAVE_FILE_EXTENSION + ".\n";
        } catch (Exception e) {
            System.err.println("Couldn't save!");
            e.printStackTrace();
            return "";
        }
    }
}
