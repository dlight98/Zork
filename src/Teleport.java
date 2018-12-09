/**
* When triggered, this class will randomly teleport the user
* to some other room in the dungeon, which they may or may not
* have previously visited.
* This class will interact with the GameState and Command classes.
*@author Benjamin Madren
*/
class Teleport extends Command{
	public Room room;
	public Room destination;

	Teleport() { }

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
	/**This method will return the description of
	* the player's destination room.**/
	String execute(){
		return destination.describe();
	}
}
