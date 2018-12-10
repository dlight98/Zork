
class VerboseCommand extends Command
	{
		private boolean value;
	public VerboseCommand(boolean value)
	{
		this.value = value;
	}


	public String execute(){
		if(value == true)
		{
			GameState.instance().getDungeon().VerboseExecute(true);
			return "Verbose is now on, all rooms will display their descriptions";
		}
		else{
			GameState.instance().getDungeon().VerboseExecute(false);
       			return "Verbose is now off, rooms will not display their descriptionss";
	}
	}
	}	
