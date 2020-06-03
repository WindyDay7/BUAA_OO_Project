package Bank;


/**
 * @author Left
 * @version 1.0
 * @created 22-6月-2018 12:23:50
 */
public class DebitCardAccount extends Account {

	/**
	 * 年存款利率
	 */
	private double annualInterestRate;

	public DebitCardAccount(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param pwd
	 */
	public DebitCardAccount(String id, String name, String pwd){

	}

	public void changeStatus(){

	}

	/**
	 * 储蓄账户存款
	 * 
	 * @param money
	 */
	public boolean depositMoney(double money){
		return false;
	}

	/**
	 * 储蓄账户取款
	 * 
	 * @param money
	 * @param pwd
	 */
	public boolean drawMoney(double money, String pwd){
		return false;
	}

	public double getRate(){
		return 0;
	}

	public boolean repOk(){
		return false;
	}

	/**
	 * 
	 * @param interestRate
	 */
	public void setRate(double interestRate){

	}

	/**
	 * 储蓄账户转账
	 * 
	 * @param AccountId
	 * @param name
	 * @param money
	 * @param accounts
	 * @exception InvalidOperationException
	 */
	public boolean transferMoney(String AccountId, String name, double money, AccountSet accounts)
	  throws InvalidOperationException{
		return false;
	}

}