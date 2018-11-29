/**  
  * When this class is triggered, the corresponding item will disappear 
  * and be replaced by another item whose name is in parentheses. 
  * This item will not have existed previously. 
  * This class also interacts with the GameState and Command classes. 
  *@author Benjamin Madren
  */
	abstract class Transform extends Command{ 
        public Item item1;
        public Item item2;	
	/**This method removes the selected item from the user's inventory. 
	 * @param Item1 **/	
	void removeFromInventory(Item item1){ 
		GameState.instance().removeFromInventory(item1);	
	} 
	/**This method adds a new item to the user's inventory. 
	 * @param Item2 **/ 
	void addToInventory(Item item2){ 
		GameState.instance().addToInventory(item2);	
	} 	
}
