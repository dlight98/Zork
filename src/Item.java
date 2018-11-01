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
    String temp1 = "";
    String temp2 = "";
    int weight;
    int tempI;

    name = s.nextLine();
    this.primaryName = name;
    System.out.println(this.primaryName);  //FIXME debug
    //TODO needs to check other names

    weight = s.nextInt();
    this.weight = weight;
    System.out.println(this.weight);  //FIXME debug

    //FIXME should do commands
    temp1 = s.nextLine();
    while(!("---").equals(temp1)){
      if(!("---").equals(temp1)){
        System.out.println("temp is: " + temp1); //DEBUG
        tempI = temp1.indexOf(":");
        temp1 = temp1.substring(0,tempI);  //might just be tempI
        temp2 = temp1.substring(tempI);
        messages.put(temp1, temp2);
        temp2 ="";
      }
      temp1 = s.nextLine();
    }

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
