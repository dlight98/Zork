import java.util.Scanner;

/**
* The <tt>Interpreter</tt> both creates a dungeon from a command-line argument,
* and passes what is typed by the user to <tt>{@link CommandFactory}</tt> for parsing.
* It is the <b>ONLY</b> way for the player to send anything typed to
* the game. Anything typed will be sent to the <tt>{@link CommandFactory}</tt> to
* be <tt>{@link CommandFactory#parse}d</tt>.
* Only one Interpreter should ever be needed for the program to run.
*
* @author Nicholas Turner
*/
public class Interpreter {

  private static GameState state; // not strictly necessary; GameState is
  // singleton

  public static String USAGE_MSG =
  "Usage: Interpreter zorkFile.zork|saveFile.sav.";

  /**
  * This is the command block activated while starting the program.
  * It sends everything typed by the player to the {@link CommandFactory}.
  * @param args[] the commandline argument passed to initialize the dungeon or the save file.
  */
  public static void main(String args[]) {

    if (args.length < 1) {
      System.err.println(USAGE_MSG);
      System.exit(1);
    }

    String command;
    Scanner commandLine = new Scanner(System.in);

    try {
      state = GameState.instance();
      if (args[0].endsWith(".zork")) {
        state.initialize(new Dungeon(args[0]));
        System.out.println("\nWelcome to " +
        state.getDungeon().getName() + "!");
        System.out.println("You have " +
        Clock.instance().getTimeLimit() +
        " days to win.");
      } else if (args[0].endsWith(".sav")) {
        state.restore(args[0]);
        System.out.println("\nWelcome back to " +
        state.getDungeon().getName() + "!");
        System.out.println("You have " +
        (Clock.instance().getTimeLimit()-
        Clock.instance().getDaysPassed()) +
        " days left.");
      } else {
        System.err.println(USAGE_MSG);
        System.exit(2);
      }

      System.out.print("\n" +
      state.getAdventurersCurrentRoom().describe() + "\n");

      command = promptUser(commandLine);

      while (!command.equals("q")) {

        System.out.print(
        CommandFactory.instance().parse(command).execute());

        command = promptUser(commandLine);
      }

      System.out.println("Bye!");

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
  * The <tt>promptUser</tt> method prints "> " and takes the user input with a Scanner,
  * returning the input as a String.
  *
  * @param commandLine the scanner searching for user input.
  * @return the input of the user.
  */
  private static String promptUser(Scanner commandLine) {

    System.out.print("> ");
    return commandLine.nextLine();
  }

}
