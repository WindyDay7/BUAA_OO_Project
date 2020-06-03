package Bank;


/**
 * @author Left
 * @version 1.0
 * @created 22-6月-2018 12:23:31
 */
public class CreditCardAccount extends Account {

	/**
	 * 最高透支额度
	 */
	private double overdraftLimit;

	public CreditCardAccount(){

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
	public CreditCardAccount(String id, String name, String pwd){

	}

	public void changeStatus(){

	}

	/**
	 * 信用卡账户存款
	 * 
	 * @param money
	 */
	public boolean depositMoney(double money){
		return false;
	}

	/**
	 * 信用卡账户消费
	 * 
	 * @param money
	 */
	public boolean drawMoney(double money){
		return false;
	}

	public double getMax(){
		return 0;
	}

	public boolean repOk(){
		return false;
	}

	/**
	 * 
	 * @param money
	 */
	public void setMax(double money){

	}

	/**
	 * 信用卡账户转账
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