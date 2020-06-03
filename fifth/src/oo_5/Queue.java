package oo_5;

class Queue {
	protected String [] num1 = new String[1000];
	protected int count_n =0;
	protected void produce(String str_n) {
		this.num1[this.count_n] = str_n; 
		this.count_n++;
	}
}
