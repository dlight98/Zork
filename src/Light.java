/**This class will allow the player to pick up and use light sources.
 * There are many dark rooms throughout the dungeon. The player needs a
 * light source to progress through these rooms.
 * @author Benjamin Madren **/

public class Light{
  public Item light;
  public boolean power = false;
  /**This method allows the player to turn the light source on and off.*/
  void setPower(boolean power){
    if(power == true){
      power = true;
    }
    else{
    	power = false;
    }
  }
  /**This method works with closed exits in the Exit class
   * to close off dark rooms from the player unless they have
   * a light source turned on.*/
  void darkRoom(boolean dark) {
    if(dark == true && power == false){
      Exit isClosed;
    }
  }
}
