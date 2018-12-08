/**This class will allow the player to pick up and use light sources.
 * There are many dark rooms throughout the dungeon. The player needs a
 * light source to progress through these rooms.**/
public class Light{
  public Item light;
  public boolean power = true;
  public boolean dark = true;	
  /**This method allows the player to turn the light source on and off.*/
  boolean onAndOff(boolean power){
    if(power == true){
      return false;
    }
    else{
      return true;
    }
  }
  /**This method works with closed exits in the Exit class
   * to close off dark rooms from the player unless they have
   * a light source turned on.*/
  void darkRoom(boolean dark){
    if(dark == true && power == true){
      Exit isClosed;
    }
  }
}
