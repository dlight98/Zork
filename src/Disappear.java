/**When this class is triggered, the selected item will cease to exist,
 * removing it from the room inventory and dungeon.
 * @author Benjamin Madren**/
abstract class DAppear extends Command{
     public Item item1;
     /**This method will completely remove the item from all areas.**/
     void removeFromInventory(Item item1){
        GameState.instance().removeFromInventory(item1);
     }
}
