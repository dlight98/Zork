import java.util.Hashtable;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Item{
  private String primaryName;
  private int weight;
  private Hashtable<String, String> messages;	//should be command then the message?

  public Item(Scanner s) throws NoItemException { //Scanner

    Scanner in = new Scanner(System.in);  //Should we use the Scanner s thats given or this one?
    String name = "";
    String temp;
    int weight;

    //should also set the name, weight, and maybe description
    //name is the first line. the first word before the comma is the primary name and the rest are aliases
    //next line is weight. this line should be put as an int into the weight variable
    //rest is Item-specific commands. should be substring before the colon as first String in HT then message after colon as message

    name = s.nextLine();
    this.primaryName = name;
    //System.out.println(this.primaryName);  //FIXME debug
    //TODO needs to check other names

    weight = s.nextInt();
    this.weight = weight;
    //System.out.println(this.weight);  //FIXME debug 

    //TODO make Commands

  }

  public boolean goesBy(String name) { //Name of item
    if (name == primaryName) {
      return true;
    } else {
      return false;
    }
  }

  public String getPrimaryName() { return this.primaryName; }

  public int getWeight() { return this.weight; }

  public String toString() {
    //might need to change
    String string = "";
    string = this.getPrimaryName();
    return string;
  }

  public static void main(String args[]) {	//temporary
    try {
      Scanner in = new Scanner(new FileReader(args[0]));
      Item item = new Item(in);
    }
    catch(Exception e) {
      System.out.println("File not found.");
    }
  }
}



