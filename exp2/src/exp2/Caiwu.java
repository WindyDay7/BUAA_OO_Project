package exp2;

class Caiwu extends Employee {
	protected int kaohe=0;
	
	public double getSalary(int kao) {  //����
		if(kao==1) {
			return 1.3 * 50000;
		}
		else {
			return 1.1 * 50000;
		}
	}
	
	public void baoxiao(String name, int kind, int money, String way,int cloor) { //����
		if(money < 1000 && kind==1 && cloor==1) {
			System.out.println("�ѳɹ��ύ");
		}
		else {
			System.out.println("�ñ�����Ŀ���ϸ�");
		}
	} 
	
}
