
import java.util.Scanner;

/**
* An <tt>Exit</tt> has a source <tt>{@link Room}</tt>, a destination
* <tt>{@link Room}</tt>, and a direction leading from the source
* to the destination. An exit can go in only <b>one</b> direction, and has
* only one source and one destination.
*
* @author Nicholas Turner
*/
public class Exit {

  /**
  * <tt>NoExitException</tt> extends <tt>{@link Exception}</tt> and is
  * thrown when there is no <tt>Exit</tt> found.
  *
  * @author Nicholas Turner
  */
  class NoExitException extends Exception {}

    private String dir;
    private Room src, dest;
    private boolean isClosed;

    /**
    * An <tt>Exit</tt> has one source <tt>{@link Room}</tt>, one destination
    * <tt>{@link Room}</tt>, and one direction leading from the source
    * to the destination. An exit may only go in a single direction.
    * In order to go back and forth between two rooms there would
    * need to be two seperate exits. One from A to B, and one from B to A.
    *
    * @param dir  the direction the exit is going in; north, south, east, or west.
    * @param src  the source room of the exit.
    * @param dest the destination of the exit.
    */
    Exit(String dir, Room src, Room dest) {
      //FIXME also needs open/closed
      init();
      this.dir = dir;
      this.src = src;
      this.dest = dest;
      src.addExit(this);
    }

    /** Given a Scanner object positioned at the beginning of an "exit" file
    entry, read and return an Exit object representing it.
    @param d The dungeon that contains this exit (so that Room objects
    may be obtained.)
    @throws NoExitException The reader object is not positioned at the
    start of an exit entry. A side effect of this is the reader's cursor
    is now positioned one line past where it was.
    @throws IllegalDungeonFormatException A structural problem with the
    dungeon file itself, detected when trying to read this room.
    */
    Exit(Scanner s, Dungeon d) throws NoExitException,
    Dungeon.IllegalDungeonFormatException {

      init();
      String srcTitle = s.nextLine();
      if (srcTitle.equals(Dungeon.TOP_LEVEL_DELIM)) {
        throw new NoExitException();
      }
      src = d.getRoom(srcTitle);
      dir = s.nextLine();
      dest = d.getRoom(s.nextLine());

      // I'm an Exit object. Great. Add me as an exit to my source Room too,
      // though.
      src.addExit(this);

      // throw away delimiter
      if (!s.nextLine().equals(Dungeon.SECOND_LEVEL_DELIM)) {
        throw new Dungeon.IllegalDungeonFormatException("No '" +
        Dungeon.SECOND_LEVEL_DELIM + "' after exit.");
      }
    }

    /** Common object initialization tasks. */
    private void init() {
    }

    /**
    * This tells the user the direction of the exit, and which room it leads to.
    * @return the direction and destination of the exit.
    */
    String describe() {
      return "You can go " + dir + " to " + dest.getTitle() + ".";
    }

    /**
    * This returns the direction the exit is going in.
    * The exit goes north, south, east, or west.
    * @return the direction of the exit.
    */
    String getDir() { return dir; }

    /**
    * This returns the source <tt>{@link Room}</tt> that
    * the exit is coming from.
    * @return the source room; the room the <tt>Exit</tt> is going from.
    */
    Room getSrc() { return src; }

    /**
    * This returns the destination <tt>{@link Room}</tt> that
    * the exit is leading to.
    * @return the destination; the room the <tt>Exit</tt> is leading to.
    */
    Room getDest() { return dest; }

    /**
    * This checks if an exit is open or closed.
    * @return if the exit is open or closed
    */
    boolean isClosed() {
	    return false;
      //FIXME needs to be written
    }
  }
