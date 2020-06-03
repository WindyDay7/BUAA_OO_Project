package Bank;


/**
 * @author Left
 * @version 1.0
 * @created 22-6月-2018 12:29:25
 */
public class InvalidOperationException extends Exception {

	/**
	 * a detailed message
	 */
	private String message;
	private static final long serialVersionUID = 1L;

	public InvalidOperationException(){

	}

	/**
	 * 
	 * @param message    message
	 */
	public InvalidOperationException(String message){

	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize()
	  throws Throwable{

	}

}