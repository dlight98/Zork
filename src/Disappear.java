/**When this class is triggered, the selected item will cease to exist,
 * removing it from the room inventory and dungeon.
 * @author Benjamin Madren**/
public class Disappear {
     private Item item1;

     Disappear(Item item) {
       item1 = item;
     }

     /**This method will completely remove the item from all areas.**/
     void removeFromInventory(Item item1){
        GameState.instance().removeFromInventory(item1);
     }
}
