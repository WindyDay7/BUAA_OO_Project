package oo_5;

class Scheduler {
	protected long [] instr_o = new long[5];
	protected long state_c = (long) 0; //用state表示电梯状态
	protected double stop_time = 0; //此时电梯一定处于停止状态
	protected long[][] request_n =  new long[1000][5]; //正确的请求队列，
	protected int i = 0;
	
	protected void deal_t() {
		double j = 0;
		int k = i;
		instr_o = request_n[i];
		j = Math.abs(state_c - instr_o[0]);
		j = j * 0.5;
		j = j + 1.0;
		if(instr_o[1] > stop_time) {
			j = j + instr_o[1];
		}
		else {
			j = j + stop_time;
		}
		while((request_n[k][0]!=0) && (request_n[k][1] <= j))
		{
			if(request_n[k][0] == request_n[i][0] && k!=i && request_n[k][3] == request_n[i][3] && request_n[k][2] == request_n[i][2]) {
					request_n[k][4] = 1;
				}
			k++;
		}
	}
	
	protected void fetch() {
		instr_o = request_n[i];
	}
	
}
