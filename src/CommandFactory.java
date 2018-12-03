
import java.util.List;
import java.util.Arrays;
/** creates command objects based on the type of command they are
 * and will also stores boolean verbose (dictates if the room description will be displayed after it has already been visited
 * varaible move wich changes the adventurues current room
 * varaible Light which can light up the room with a luminating object if the room is dark
 * External Clock which will keep track of time outside the rooms and is affected by actions
 * Score which adds points to players after they preform actions
 * Health which will keep tracks of player's health
 * transform which changes one item to another
 * Teleport which moves the adventurer to a different room
 *
 * @author Alexander Loveland
 */
public class CommandFactory {

    private static CommandFactory theInstance;
    public static List<String> MOVEMENT_COMMANDS =
        Arrays.asList("n","w","e","s","u","d" );

    public static synchronized CommandFactory instance() {
        if (theInstance == null) {
            theInstance = new CommandFactory();
        }
        return theInstance;
    }
/** creates command objects
 */
    private CommandFactory() {
    }

    public Command parse(String command) {
        String parts[] = command.split(" ");
        String verb = parts[0];
        String noun = parts.length >= 2 ? parts[1] : "";
        if (verb.equals("look")) {
            return new LookCommand();
        }
        if (verb.equals("save")) {
            return new SaveCommand(noun);
        }
        if (verb.equals("take")) {
            return new TakeCommand(noun);
        }
        if (verb.equals("drop")) {
            return new DropCommand(noun);
        }
        if (verb.equals("i") || verb.equals("inventory")) {
            return new InventoryCommand();
        }
        if (verb.equals("health")) {
          return new HealthCommand();
        }
        if (MOVEMENT_COMMANDS.contains(verb)) {
            return new MovementCommand(verb);
        }
        if (parts.length == 2) {
            return new ItemSpecificCommand(verb, noun);
        }
	if(verb.equals("Teleport")){
		if (MOVEMENT_COMMANDS.contains(verb)) {
		return new Teleport(verb);
	}
	}
       	return new UnknownCommand(command);

}
}
