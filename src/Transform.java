/**  
  * When this class is triggered, the corresponding item will disappear 
  * and be replaced by another item whose name is in parentheses. 
  * This item will not have existed previously. 
  * This class also interacts with the GameState and Command classes. 
  *@author Benjamin Madren
  */
	public class Transform extends Command{ 
        public Item item1;
        public Item item2;	
	/**This method removes the selected item from the user's inventory. 
	 * @param Item1 **/	
	Item removeFromInventory(){ 
		GameState.instance().removeFromInventory(item1);	
	} 
	/**This method adds a new item to the user's inventory. 
	 * @param Item2 **/ 
	Item addToInventory(){ 
		GameState.instance().addToInventory(item2);	
	} 
	/**The class returns the new item. 
	 * @return Item2 **/
		return item2;	
	}
