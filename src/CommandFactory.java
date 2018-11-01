
import java.util.List;
import java.util.Arrays;

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

  private CommandFactory() { }

  public Command parse(String command) {
    int firstSpace; //to hold where the first space is. useful for take and drop
    String firstWord = command;  //to hold where the first word is.
    String secondWord = ""; //holds the noun of a command

    try {
      firstSpace = command.indexOf(" ");
      //System.out.println("firstSpace is: " + firstSpace); //FIXME DEBUG
      firstWord = command.substring(0, firstSpace); //gets the first word
      secondWord = command.substring(firstSpace+1);
      //System.out.println("First word is: " + firstWord + "\nsecond word is: " + secondWord); //FIXME DEBUG
    } catch (Exception e) { /* only one word? */}

    if (MOVEMENT_COMMANDS.contains(command)) {
      return new MovementCommand(command);
    } else if(command.equals("save")) {
      return new SaveCommand();
    } else if(command.equals("look")) {
      return new LookCommand();
    } else if(command.equals("i") || command.equals("inventory")) {
      return new InventoryCommand();
    } else if(firstWord.equals("take") || firstWord.equals("take ")) {
      return new TakeCommand(secondWord);
    } else if(firstWord.equals("drop") || firstWord.equals("drop ")) {
      return new DropCommand(secondWord);  
    } else {
      return new UnknownCommand(command);
    }
  }

}
