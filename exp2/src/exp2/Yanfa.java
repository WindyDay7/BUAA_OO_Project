package exp2;

class Yanfa extends Employee {
	protected int time = 0, kaohe3 = 0,clo=0;
	public Yanfa(int nian,int kao4,int cloy) {
		this.time = nian;
		this.kaohe3 = kao4;
		this.clo = cloy;
	}
	
	public double getSalary() {
		if(this.kaohe3==1) {
			return (1.3* (50000+1000*time));
		}
		else {
			return (1.1* (50000+1000*time));
		}
	}
	
	public void baoxiao(String name, int kind, int money, String way,int cloor) {
		if(money < 1000 && kind==1 && cloor==1) {
			System.out.println("�ѳɹ��ύ");
		}
		else {
			System.out.println("�ñ�����Ŀ���ϸ�");
		}
	}
}
 //��ɫΪ1����ɫΪ2