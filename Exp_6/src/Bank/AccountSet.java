package Bank;

import java.util.ArrayList;
import java.util.Iterator;


public class AccountSet {
	
ArrayList<Account> List;
//OVERVIEW:帐户的集合，不区别帐户类别
//任意选择一种数据结构，对照规格补全代码
	
   //constructors
   public AccountSet(){
   /**
    * @REQUIRES:None;
    * @MODIFIES:this;
    * @EFFECTS： (\result = \this) && (\this.isEmpty());
    */
	 this.List = new ArrayList<Account>();
	 
	//请补全构造函数
   }
   
   
   public boolean insert(Account a){
   /**
    * @ REQUIRES: a!= null ;
    * @ MODIFIES: this;
    * @ EFFECTS: 
    * (\result==false)==>(\old(\this).contains(a)==true)
    * (\this.size == \old(\this).size+1) && (\this.contains(a)==true)&&(\result==true); 
    */
	   
	 if(this.isIn(a))
		 return false;
	 this.List.add(a);
	 return true;
	//请根据规格补充代码
   }   
   
   public void removeByAccountId(String id) throws InvalidOperationException{
    /**
     * @ MODIFIES:this;
     * @ EFFECTS:
     * (\old(\this).queryByAccountId(id) !=null) ==> (\this.size == \old(\this).size-1) && (\this.contains(\old(\this).queryByAccountId(id))==false);
     * (\old(\this).queryByAccountId(id)==null)==>exceptional_behavior(InvalidOperationException("Invalid Account!"));                   
     */
	   if(this.queryByAccountId(id)==null) {
		   throw new InvalidOperationException("Invalid Account!");
	   }
	   else {
		   for(int i=0; i<this.List.size(); i++) {
			   if(this.List.get(i).equals(this.queryByAccountId(id))) {
				   this.List.remove(i);
				   break;
			   }
		   }
	   }
	   
	//请根据规格补充代码
   }

   public void removeByUserId(String id)throws InvalidOperationException{
    /**
     * @ MODIFIES:this;
     * @ EFFECTS:
     * (\old(\this).queryByUserId(id) !=null) ==> (\this.size == \old(\this).size-\old(\this).queryByUserId(id).size()) && (this.contains(\old(\this).queryByUserId(id))==false));           
     * (\old(\this).queryByUserId(id) ==null)==>exceptional_behavior(InvalidOperationException("Invalid User!"))                   
    */
	   if(this.queryByUserId(id)==null) {
		   throw new InvalidOperationException("Invalid Account!");
	   }
	   else {
		   for(int i=0; i<this.List.size(); i++) {
			   if(this.List.get(i).getUserId().equals(id)) {
				   this.List.remove(i);
				   i--;
			   }
				   
		   }
	   }
	//请根据规格补充代码
   }
   
  
   public boolean isIn(Account a){
   /**
    * @ REQUIRES: a!= null ;
    * @ MODIFIES:none; 
    * @ EFFECTS:
    * (\result==true)==>(this.contains(a)==true);
    * (\result==false)==>(this.contains(a)==false);
    */
	   for(int i=0; i<this.List.size(); i++) {
		   if(this.List.get(i).equals(a))
			   return true;
	   }
	   return false;
	//请根据规格补充代码
   }   

   public long size(){
   /**
    * @ EFFECTS: 
    * (\result==this.size());
    */
	   return this.List.size();
	//请根据规格补充代码
   }   
  
   public Iterator elements(){
    /**
	 * @ Effects: 
	 * (\result != null) && (\result instanceof Iterator) && (\all int i; 0<=i<\this.size;\result.contains(\this[i])); 
	 */
	   Iterator listIt = this.List.iterator();
	   return listIt;
	//请根据规格补充代码
   }   
   
   public Account queryByAccountId(String accountId) throws InvalidOperationException{
   /**
    * @ REQUIRES: accountId!= null ;
    * @ EFFECTS: 
    * (\result==a)==>(\all Account a;(\this.contains(a)==true)&&a.getAccountId()==accountId);
    * (\all Account a;a.getAccountId()==accountId && (\this.contains(a)==false))==>exceptional_behavior("Invalid Account!") ;
    */
   //请根据规格补充代码
	   
	   for(int i=0; i<this.List.size(); i++) {
		   if(this.List.get(i).getAccountId()==accountId)
			   return this.List.get(i);
	   }
	   throw new InvalidOperationException("Invalid Account!");
   }   
          
   public AccountSet queryByUserId(String userId)throws InvalidOperationException{
   /**
    * @ REQUIRES: userId!= null ;        
    * @ EFFECTS: 
    * (\all Account a;(\result.contains(a)==true)==>a.getUserId()==userId && (\this.contains(a)==true));
    * (\all Account a;a.getUserId()==userId && (\this.contains(a)==false))==>exceptional_behavior("Invalid User!") ;  
    */
	   AccountSet new_set = new AccountSet();
	   for(int i=0; i<this.List.size(); i++) {
		   if(this.List.get(i).getUserId()==userId) {
			   new_set.List.add(this.List.get(i));
		   }
	   }
	   if(new_set.size()==0) {
		   throw new InvalidOperationException("Invalid Account!");
	   }
	   return new_set;
	   
   //请根据规格补充代码
   }   
    
   public boolean repOK(){
   //请将repOK()方法补充完整   
	   if(this.List.isEmpty())
		   return false;
	   return true;
   }
   
}

