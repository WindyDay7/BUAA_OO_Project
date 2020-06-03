package Bank;


/**
 * OVERVIEW:账户都具有账户id，用户id，用户姓名，用户密码，余额，激活状态
 * @author Left
 * @version 1.0
 * @created 22-6月-2018 12:17:29
 */
public class Account {

	/**
	 * 账户id
	 */
	private String AccountId;
	/**
	 * 账户余额
	 */
	private double balance = 0.0;
	private static int counter = 0;
	/**
	 * 账户激活状态
	 */
	private boolean isActivate = true;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户密码
	 */
	private String pwd;
	/**
	 * 用户id
	 */
	private String UserId;

	public Account(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param pwd
	 */
	public Account(String id, String name, String pwd){

	}

	/**
	 * 存款
	 * 
	 * @param money
	 */
	public boolean depositMoney(double money){
		return false;
	}

	/**
	 * 取款
	 * 
	 * @param money
	 */
	public boolean drawMoney(double money){
		return false;
	}

	public String getAccountId(){
		return AccountId;
	}

	public double getBalance(){
		return 0;
	}

	public boolean getIsActivate(){
		return false;
	}

	public String getName(){
		return "";
	}

	public String getPwd(){
		return "";
	}

	public String getUserId(){
		return UserId;
	}

	public boolean repOk(){
		return false;
	}

	/**
	 * 
	 * @param accountId
	 */
	public void setAccountId(String accountId){
		AccountId = newVal;
	}

	/**
	 * 
	 * @param balance
	 */
	public void setBalance(double balance){

	}

	/**
	 * 
	 * @param isActivate
	 */
	public void setIsActivate(boolean isActivate){

	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name){

	}

	/**
	 * 
	 * @param pwd
	 */
	public void setPwd(String pwd){

	}

	/**
	 * 
	 * @param id
	 */
	public void setUserId(String id){
		UserId = newVal;
	}

	public String toString(){
		return "";
	}

	/**
	 * 转账
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