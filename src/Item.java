
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;

/**
* An <tt>Item</tt> has a name, aliases, weight, and commands specific to it;
* it is an object that the user can pick up from a room, put into their inventory and
* interact with. The player can only hold a certain total weight. Once that
* weight is reached the player must drop an item into a room to pick up
* a new one. The item can also vanish from inventory to return
* to the room, or become unobtainable entirely.
* Each item has their own set of commands (<i>i.e.</i> the user
* kicks a ball and it goes down the hall, leaving the inventory).
* An item can be transformed into a different one, such as
* a piece of paper being folded into a paper crane.
* Items can also have an effect both the player's health and their score.
* If a bomb object blows up it will negatively affect the players
* score.
*
* @author Nicholas Turner
*/
public class Item {

  /**
  * <tt>NoItemException</tt> extends <tt>{@link Exception}</tt>
  * and is meant to be used for saying when the <tt>Item</tt>
  * specified does not exist.
  *
  * @author Nicholas Turner
  */
  static class NoItemException extends Exception {}

    private String primaryName;
    private int weight;
    private Hashtable<String,String> messages;
    private Hashtable<String,String> events;
    private Set<String> aliases;

    /**
    * This creates items by reading the name, weight, specific commands,
    * and aliases of each item from the dungeon file. The Scanner is passed to
    * read the item from file. If there are no items left to read a
    * <tt>{@link NoItemException}</tt> is thrown. If the format
    * itself is incorrect an <tt>IllegalDungeonFormatException</tt>
    * is thrown.
    *
    * @param  s                                     meant to read the lines of the file.
    * @throws NoItemException                       if what is being read is not an item.
    * @throws Dungeon.IllegalDungeonFormatException if the file format is incorrect.
    */
    Item(Scanner s) throws NoItemException,
    Dungeon.IllegalDungeonFormatException {

      messages = new Hashtable<String,String>();
      events = new Hashtable<String,String>();
      aliases = new HashSet<String>();

      // Read item name.
      String names[] = s.nextLine().split(",");
      if (names[0].equals(Dungeon.TOP_LEVEL_DELIM)) {
        throw new NoItemException();
      }
      primaryName = names[0];
      for (int i=1; i<names.length; i++) {
        aliases.add(names[i]);
      }

      // Read item weight.
      weight = Integer.valueOf(s.nextLine());

      // Read and parse verbs lines, as long as there are more.
      String verbLine = s.nextLine();
      while (!verbLine.equals(Dungeon.SECOND_LEVEL_DELIM)) {
        if (verbLine.equals(Dungeon.TOP_LEVEL_DELIM)) {
          throw new Dungeon.IllegalDungeonFormatException("No '" +
          Dungeon.SECOND_LEVEL_DELIM + "' after item.");
        }
        String[] verbParts = verbLine.split(":");


        if(verbParts[0].contains("[")){ //TODO if there is an event related to it
          String eventPieces = verbParts[0];
          String[] eventParts = eventPieces.split("\\[");
          if(eventParts[1].contains(",")) { //FIXME might need to do more than once
            String[] multipleEvents = eventParts[1].split(",");
            events.put(eventParts[0], multipleEvents[0]);
            events.put(eventParts[0], multipleEvents[1].substring(0, multipleEvents[1].length()-1));
            /*DEBUG
            System.out.println("Part: " + eventParts[0] + "| event: " +multipleEvents[0]);
            DEBUG
            System.out.println("Part: " + eventParts[0] + "| event: " + multipleEvents[1].substring(0, multipleEvents[1].length()-1));*/
          } else {
          events.put(eventParts[0], eventParts[1].substring(0, eventParts[1].length() - 1));
          //System.out.println(eventParts[1]); //DEBUG
        }
          /*
          TODO
          make the events split if there are multiple events
          */

          messages.put(eventParts[0],verbParts[1]);
        } else {
          messages.put(verbParts[0],verbParts[1]);
        }
        verbLine = s.nextLine();
      }
    }

    /**
    * Gets the weight of an Item.
    * @return the weight of the item.
    */
    int getWeight() {
      return weight;
    }

    /**
    * Checks if an item has the param String as either
    * a name or an alias. Returns true if the String is
    * an alias and false if it isn't.
    * @param  name the String to be checked.
    * @return      true if the String is a name or alias, false if not.
    */
    boolean goesBy(String name) {
      if (this.primaryName.equals(name)) {
        return true;
      }
      for (String alias : this.aliases) {
        if (alias.equals(name)) {
          return true;
        }
      }
      return false;
    }

    /**
    * Gets the primary name of an item.
    * This returns the primary name and
    * will not return an alias.
    * @return the primary name of the item.
    */
    String getPrimaryName() { return primaryName; }

    /**
    * This returns the message given to the player after
    * he or she calls a command on an item.
    * If the user types an {@link ItemSpecificCommand}
    * on an item, this returns the message meant to be
    * sent back (<i>i.e.</i> the user kicks a ball and
    * it goes down the hall, leaving the inventory).
    * @param  verb the command given to act on an item.
    * @return      the description of what happens printed to the user.
    */
    public String getMessageForVerb(String verb) {
      return messages.get(verb);
    }

    public String getEventForVerb(String verb) { //TODO

      return events.get(verb);

    }

    /**
    * Takes the item and returns its primary name.
    * This would be used to get a String name instead
    * of the item object itself.
    * @return the primary name of the item.
    */
    public String toString() {
      return primaryName;
    }

    public boolean hasEvent(String word){
      if(events.get(word) != null){
        return true;
      } else {
        return false;
      }
    }
  }
