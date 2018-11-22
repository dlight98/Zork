/** This command will preform an action specific to that comman
 *
 * @author Alecander Lovelandd
 */
class ItemSpecificCommand extends Command {

    private String verb;
    private String noun;
                        
/**Conrustor which verb and noun instance varaibles to the paramaters passe
 *
 * @param verb specific action preformed by object
 * @param noun noun that describes what the object isd
 */
    ItemSpecificCommand(String verb, String noun) {
        this.verb = verb;
        this.noun = noun;
    }
/** This method returns a string whcih contains the verb from the item which has a specific command, and if the 
 * item does not exist it will  catch an exception and return that the item does not exist
 */
    public String execute() {
        
        Item itemReferredTo = null;
        try {
            itemReferredTo = GameState.instance().getItemInVicinityNamed(noun);
        } catch (Item.NoItemException e) {
            return "There's no " + noun + " here.";
        }
        
        String msg = itemReferredTo.getMessageForVerb(verb);
        return (msg == null ? 
            "Sorry, you can't " + verb + " the " + noun + "." : msg) + "\n";
    }
}
