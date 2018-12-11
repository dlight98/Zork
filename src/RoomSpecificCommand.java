import java.util.ArrayList;

/**This class will use commands to check if a room is light or dark
* and then close off dark rooms from the player unless they have
* a light source turned on.**/
  class RoomSpecificCommand extends Command{

  	private String room;
  	private String state;

  	/**A constructor to which room and state instance variables are passed.
  	* @param room refers to the room.
  	* @param state refers to whether room is light or dark.**/

  	RoomSpecificCommand(String room, String state){
  		this.room = room;
  		this.state = state;
  	}

		String getState() {
			return this.state;
		}

		String getRoom() {
			return this.room;
		}

  	/**This method works with Exit to close off rooms that are dark according
  	* to the command from the player unless they have a light source
  	* turned on.
  	* @throws NoRoomException **/
  	public String execute() {
  		Room roomReferredTo = null;
  		try {
  			roomReferredTo = GameState.instance().getAdventurersCurrentRoom();
  		} catch (Room.NoRoomException e) {
  			return "There's no " + this.room + " here.\n";
  		}

  		String msg = roomReferredTo.getMessageForState(this.state);
  		if(msg == null){
  			return "Sorry, " + room + " isn't " + state + ".\n";
  		} else if (roomReferredTo.hasState(this.state) == true) {
  			ArrayList<String> states = new ArrayList(roomReferredTo.getState());

  			for(String state : states){
  				 //System.out.println(state + "DEBUG.\n");  //DEBUG
  				if(state.contains("Dark")){
  					Light darkRoom;
  				}
  			}
  		}
  	}
 }
