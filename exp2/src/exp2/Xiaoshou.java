package exp2;

class Xiaoshou extends Employee {
	protected int mai = 0;
	public double getSalary(int ma) {
		if(ma < 300000) {
			return 40000.0;
		}
		else {
			return 50000.0 + (ma-300000)*0.2 ;
		}
	}
	
	public void baoxiao(String name, int kind, int money, String way,int cloor) {
		if(cloor==2 && way!=null) {
			System.out.println("已成功提交");
		}
		else {
			System.out.println("该报销条目不合格");
		}
	}
}
