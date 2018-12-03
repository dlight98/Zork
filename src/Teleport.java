/**
 * When triggered, this class will randomly teleport the user 
 * to some other room in the dungeon, which they may or may not 
 * have previously visited. 
 * This class will interact with the GameState and Command classes. 
 *@author Benjamin Madren
 */
	public class Teleport extends Command{ 
        public GameState room;	
	public GameState destination;	
		/**This method will check what room the user
		 * is currently in.
		 * @param room **/
		GameState getAdventurersCurrentRoom(GameState room){
		     return room;
		}	     
		/**This method will set the user's current room 
		 * to their desired destination. 
		 * @param destination **/
		GameState setAdventurersCurrentRoom(GameState destination){ 
	             return destination;	
		}

		String execute(){
			return destination.getDesc();
	}
	}
