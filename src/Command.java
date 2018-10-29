/* TODO (From Nick)
*Make command class abstract
*Make subclasses
*Have the "LookCommand" made to set beenHere to false
*/

// For now, only direction commands and "save". If the "direction" is bogus,
// then this effectively doubles as an UnknownCommand (to be a subclass
// later).
abstract class Command {

    //private String dir;     // for now, this class is only for direction
                            // commands, plus "save" which is kind of a weird
                            // special case.

    // Command(String dir) {
    //     this.dir = dir;
    // }

    abstract String execute(); //


}
