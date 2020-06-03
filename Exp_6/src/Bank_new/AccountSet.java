package Bank;


/**
 * @author Left
 * @version 1.0
 * @created 22-6月-2018 12:18:12
 */
public class AccountSet {

	ArrayList<Account> List;



	public void finalize() throws Throwable {

	}

	/**
	 * constructors
	 */
	public AccountSet(){

	}

	public Iterator elements(){
		return null;
	}

	/**
	 * 
	 * @param a
	 */
	public boolean insert(Account a){
		return false;
	}

	/**
	 * 
	 * @param a
	 */
	public boolean isIn(Account a){
		return false;
	}

	/**
	 * 
	 * @param accountId
	 * @exception InvalidOperationException
	 */
	public Account queryByAccountId(String accountId)
	  throws InvalidOperationException{
		return null;
	}

	/**
	 * 
	 * @param userId
	 * @exception InvalidOperationException
	 */
	public AccountSet queryByUserId(String userId)
	  throws InvalidOperationException{
		return null;
	}

	/**
	 * 
	 * @param id
	 * @exception InvalidOperationException
	 */
	public void removeByAccountId(String id)
	  throws InvalidOperationException{

	}

	/**
	 * 
	 * @param id
	 * @exception InvalidOperationException
	 */
	public void removeByUserId(String id)
	  throws InvalidOperationException{

	}

	public boolean repOK(){
		return false;
	}

	public long size(){
		return 0;
	}

}