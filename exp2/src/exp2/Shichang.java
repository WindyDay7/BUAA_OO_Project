package exp2;

class Shichang extends Employee {
	protected int kaohe2=0;
	
	public Shichang(int kao2) {
		this.kaohe2 = kao2;
	}
	
	public double getSalary(int kao2) {
		if(kao2==1) {
			return 1.3 * 65000.0;
		}
		else {
			return 1.1 * 65000.0;
		}
	}
	
	public void baoxiao(String name, int kind, int money, String way,int cloor) {
		if(cloor==2 && way!=null) {
			System.out.println("�ѳɹ��ύ");
		}
		else {
			System.out.println("�ñ�����Ŀ���ϸ�");
		}
	}
}
