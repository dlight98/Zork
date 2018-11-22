/** A class that is made when passed command doesnt fall into any
 * of the appropriate preset categories for commands
 */
class UnknownCommand extends Command {

    private String bogusCommand;
/**sets bogus command to the command that did not fit any category that 
 * was passed as a parameter
 */
    UnknownCommand(String bogusCommand) {
        this.bogusCommand = bogusCommand;
    }
/**This method simply returns a string indicating that the command passed does not 
 * make sense and cannot be interpreted
 */
    String execute() {
        return "I'm not sure what you mean by \"" + bogusCommand + "\".\n";
    }
}
