package Bank;

public class InvalidOperationException extends Exception{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;//a detailed message

    public InvalidOperationException(String message) { 
 
         this.message=message;
 
    }
}