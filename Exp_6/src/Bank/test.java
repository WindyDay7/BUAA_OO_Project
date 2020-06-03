package Bank;

import java.util.Iterator;



public class test {

	/**
	 * @param args
	 */
	public static AccountSet accountset = new AccountSet();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Account a1=new Account("01","jane","123");
		accountset.insert(a1);
		Account a2=new Account("02","greece","1234");
		accountset.insert(a2);
		Account a3=new Account("01","jane","12345");
		accountset.insert(a3);
		Account a4=new Account("02","greece","123456");   //1，3userID相同，CountID不同，2，4同
		accountset.insert(a4);
		/*
		code continue
		*/
		try {
			Account m1=accountset.queryByAccountId("1");
			System.out.println(m1.getAccountId()+","+m1.getName()+","+m1.getUserId());
		} catch (InvalidOperationException e) {
			System.out.println("No.1 test error");
		}
		try {
			AccountSet m1=accountset.queryByUserId("01");
			@SuppressWarnings("unchecked")
			Iterator<Account> atmp=m1.elements();
			while(atmp.hasNext()) {
				Account tmp=atmp.next();
				System.out.println(tmp.getAccountId()+","+tmp.getName()+","+tmp.getUserId());
			}
		} catch (InvalidOperationException e) {
			System.out.println("No.2 test error");
		}
		if(accountset.isIn(a1))
			System.out.println("a1 is in acoountset");
		else
			System.out.println("No.3 test error");
		try {
			accountset.removeByAccountId("1");
			if(accountset.isIn(a2))
				System.out.println("No.4 test error");
			else
				System.out.println("AccountId:1 has been deleted");
		} catch (InvalidOperationException e) {
			System.out.println("No.4 test error");
		}
		try {
			accountset.removeByUserId("01");   
			if(accountset.isIn(a1))
				System.out.println("No.5 test error");
			else
				System.out.println("UserId:01 has been deleted");
		} catch (InvalidOperationException e) {
			System.out.println("No.5 test error");
		}
		
		
		DebitCardAccount dca1=new DebitCardAccount("01","李白","123");  //测试普通卡账户
		CreditCardAccount cca1=new CreditCardAccount("01","杜甫","1234");  //测试信用卡账户
		cca1.setMax(50.0);
		
		if(!dca1.depositMoney(-13.0))  //
			System.out.println("负存款");
		else
			System.out.println("No.6 test error");
		if(cca1.depositMoney(-40.0))
			System.out.println("透支超限");
		else
			System.out.println("No.14 test error");
		
		if(cca1.depositMoney(50.0))
			System.out.println("存款80元");
		else
			System.out.println("No.15 test error");
		
		if(cca1.drawMoney(22.0))
			System.out.println("取款22元");
		else
			System.out.println("No.16 test error");
		
		if(!cca1.drawMoney(180.0))
			System.out.println("余额不足");
		else
			System.out.println("No.17 test error");
		
		try {
			if(cca1.transferMoney("3","greece",1.0,accountset))
				System.out.println("转账成功");
			else
				System.out.println("No.18 test error");
		} catch (InvalidOperationException e) {
			System.out.println("No.18 test error");
		}

	}
}