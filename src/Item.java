import java.util.Hashtable;
import java.util.Scanner;

public class Item {
  private String primaryName;
  private int weight;
  private Hashtable<String, String> messages;

  Item(Scanner s) { //Scanner
    Scanner in = new Scanner(System.in);
  }

  boolean goesBy(String name) { //Name of iter
    if (name == primaryName) {  
      return true;
    } else {
      return false;
    }  
  }

  String getPrimaryName() { //gets true name
    return primaryName;
  }

  String getMessageForVerb(String verb) { //get the message
    return verb;
  }

  public String toString() { 
    return "messages";
  }
}
