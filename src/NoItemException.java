/**
 * The <tt>NoItemException</tt> is an Exception
 * which is meant to be thrown when there is no
 * item of that name found.
 * @author Nicholas Turner
 */
class NoItemException extends Exception {

  /**
   * [NoItemException description]
   * @param e a required string for an exception to be thrown
   */
  NoItemException(String e){
    System.out.println("Item not Found.");
  }
}
