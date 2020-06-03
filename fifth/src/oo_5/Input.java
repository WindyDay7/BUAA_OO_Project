package oo_5;


class Input implements Runnable {
	private W_legal request_line;
	
	public Input(W_legal request_line) {
		this.request_line = request_line;
	}
	
	public void run() {
		request_line.in_put();  //表示输入，然后将这一次输入加入到请求队列中去
		//System.out.printf("END的至是：%d\n", request_line.end);
	}
}
