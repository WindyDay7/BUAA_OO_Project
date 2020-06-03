package oo.other1;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Scheduler {
	/**
	 * @OVERVIEW: 
	 * schedule request list to handle with in passing
	 */
	private Lift lift = new Lift();
	private double[] floor_list = new double[11];
	private Stage[] stage_list = new Stage[11];
	private int[] request_mark = new int[200]; 
	public double time_now;
	private String save;
	public static int count = 0;
	public static Request[] requestlist = new Request[200];
	private double run_time;
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: this.floor_list; this.stage_list; this.request_mark; this.time_now; this.run_time;
	 * @EFFECTS: this.time_now == 0.0; this.run_time == 0.0; (\all 0<=i<11; this.floor_list[i] == 0.0);
	 * 			 (\all 0<=i<11; this.stage_list == new Stage()); (\all 0<=i<200; this.request_mark[i] == 0);
	 */
	public Scheduler() {
		int i=0;
		for(i=1;i<11;i++) {
			floor_list[i] = 0.0;
			Stage stage = new Stage();
			stage_list[i] = stage;
		}
		for(i=0;i<200;i++) {
			request_mark[i] = 0;
		}
		time_now = 0.0;
		run_time = 0.0;
	}
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: this.requset_mark; this.time_now; this.run_time;
	 * @EFFECTS: true ==> (schedule devided into UP/DOWN/STILL);
	 */
	public void run() {
		int i, j;
		int sd_num = 0;
		Request request_main;
		int[] request_sd = new int[120];
		for(i=0;i<count;i++) {
			Request request = requestlist[i];
			if(i == 0) {
				printInfo(request);
				System.out.println("/(1,STILL,1.0)");
				time_now += 1.0;
				request_mark[i] = 1;
				stage_list[lift.getFloor_target()].getLight()[0] += 1.0;			
			}
			else {
				if(request_mark[i] == 0) {
					request_main = request;
					int main_num = i;
					boolean is_up = lift.getFloor_now() < request_main.getFloor();
					boolean is_down = lift.getFloor_now() > request_main.getFloor();
					boolean is_still = lift.getFloor_now() == request_main.getFloor();
					
					while(true) {
						for(j=1;j<count;j++) {
							if(judge(request_main, requestlist[j]) == 1 && request_mark[j] == 0) {
								request_sd[sd_num++] = j;
//								System.out.print(j + " ");
							}
						}
//						System.out.println();
						
						if(is_up) {
							int min = 0;
							for(j=0;j<sd_num;j++) {
								if(requestlist[request_sd[j]].getFloor() < requestlist[request_sd[min]].getFloor() ) {
									min = j;
								}
							}
							if(sd_num == 0) {
								work(request_main,false);
								request_mark[main_num] = 1;
								break;
							}
							else {
								if(requestlist[request_sd[min]].getFloor() == lift.getFloor_now()) {
									if(same(requestlist[request_sd[min]], true) == 1) {
										System.out.print("#SAME");
										printInfo(requestlist[request_sd[min]]);
										System.out.println();
										request_mark[request_sd[min]] = 1;
									}
									else {
										printInfo(requestlist[request_sd[min]]);
										System.out.println(save);
										request_mark[request_sd[min]] = 1;
									}
									
								}
								else {
									int mark = 0;
									for(j=0;j<sd_num;j++) {
										if(requestlist[request_sd[j]].getFloor() > requestlist[request_sd[min]].getFloor()) {
											mark = 1;
											break;
										}
									}
									if(requestlist[request_sd[min]].getFloor() < request_main.getFloor()) {
										work(requestlist[request_sd[min]], true);
										request_mark[request_sd[min]] = 1;
									}
									else if(requestlist[request_sd[min]].getFloor() > request_main.getFloor()){
										work(request_main, true);
										request_mark[main_num] = 1;
										request_main = requestlist[request_sd[0]];
										main_num = request_sd[0];
									}
									else {
										if(mark == 1) {
											work(request_main, true);
											request_mark[main_num] = 1;
											request_main = requestlist[request_sd[j]];
											main_num = request_sd[j];
										}
										else {
											work(request_main, true);
											request_mark[main_num] = 1;
											if(same(requestlist[request_sd[min]], true) == 1) {
												System.out.print("#SAME");
												printInfo(requestlist[request_sd[min]]);
												System.out.println();
												request_mark[request_sd[min]] = 1;
											}
											else {
												printInfo(requestlist[request_sd[min]]);
												System.out.println(save);
												request_mark[request_sd[min]] = 1;
											}
											break;
										}
									}
								}
							}
						}
						
						if(is_down) {
							int max = 0;
							for(j=0;j<sd_num;j++) {
								if(requestlist[request_sd[j]].getFloor() > requestlist[request_sd[max]].getFloor() ) {
									max = j;
								}
							}
							if(sd_num == 0) {
								work(request_main, false);
								request_mark[main_num] = 1;
								break;
							}
							else {
								if(requestlist[request_sd[max]].getFloor() == lift.getFloor_now()) {
									if(same(requestlist[request_sd[max]], true) == 1) {
										System.out.print("#SAME");
										printInfo(requestlist[request_sd[max]]);
										System.out.println();
										request_mark[request_sd[max]] = 1;
									}
									else {
										printInfo(requestlist[request_sd[max]]);
										System.out.println(save);
										request_mark[request_sd[max]] = 1;
									}
								}
								else {
									for(j=0;j<sd_num;j++) {
										if(requestlist[request_sd[j]].getFloor() < requestlist[request_sd[max]].getFloor()) {
											break;
										}
									}
									if(requestlist[request_sd[max]].getFloor() > request_main.getFloor()) {
										work(requestlist[request_sd[max]], true);
										request_mark[request_sd[max]] = 1;
									}
									else if(requestlist[request_sd[max]].getFloor() < request_main.getFloor()){
										work(request_main, true);
										request_mark[main_num] = 1;
										request_main = requestlist[request_sd[0]];
										main_num = request_sd[0];
									}
									else {
										work(request_main, true);
										request_mark[main_num] = 1;
										if(same(requestlist[request_sd[max]], true) == 1) {
											System.out.print("#SAME");
											printInfo(requestlist[request_sd[max]]);
											System.out.println();
											request_mark[request_sd[max]] = 1;
										}
										else {
											printInfo(requestlist[request_sd[max]]);
											System.out.println(save);
											request_mark[request_sd[max]] = 1;
										}
										break;
									}
								}
							}
						}
						
						if(is_still) {
							work(request_main, false);
							request_mark[main_num] = 1;
							break;
						}
						
						sd_num = 0;
						for(int l=0;l<count;l++) {
							request_sd[l] = 0;
						}
					}
				}
			}
		}
	}
	
	/**
	 * @REQUIRES: request != null && is_sd instance of boolean;
	 * @MODIFIES: None;
	 * @EFFECTS: true ==> (Simulate elevator operating process);
	 */
	public void work(Request request, boolean is_sd) {
		lift.setFloor_target(request.getFloor());
		lift.setState(request.getState());
		if(!is_sd) {
			if(request.getTime() > this.time_now) {
				this.time_now = request.getTime();
			}
		}
		if(lift.getState() == 0) {
			if( request.getTime() != 0 ) {
				if(request.getTime() > stage_list[lift.getFloor_target()].getLight()[1]) {
					this.run_time = Math.abs((lift.getFloor_target() - lift.getFloor_now())*0.5);
					stage_list[lift.getFloor_target()].getLight()[1] = Math.max(request.getTime(), this.time_now) + this.run_time + 1.0;
					door_open(request);
				}
				else {
					System.out.print("#SAME");
					printInfo(request);
					System.out.println();
				}
			}
			else {
				if(stage_list[lift.getFloor_target()].getLight()[1] == 0) {
					this.run_time = Math.abs((lift.getFloor_target() - lift.getFloor_now())*0.5);
					stage_list[lift.getFloor_target()].getLight()[1] = Math.max(request.getTime(), this.time_now) + this.run_time + 1.0;
					door_open(request);
				}
				else {
					System.out.print("#SAME");
					printInfo(request);
					System.out.println();
				}
			}
			
		}
		else if(lift.getState() == 1) {
			if(request.getTime() != 0) {
				if(request.getTime() > stage_list[lift.getFloor_target()].getLight()[0]) {
					this.run_time = Math.abs((lift.getFloor_target() - lift.getFloor_now())*0.5);
					stage_list[lift.getFloor_target()].getLight()[0] = Math.max(request.getTime(), this.time_now) + this.run_time + 1.0;
					door_open(request);
				}
				else {
					System.out.print("#SAME");
					printInfo(request);
					System.out.println();
				}
			}
			else {
				if(stage_list[lift.getFloor_target()].getLight()[0] == 0) {
					this.run_time = Math.abs((lift.getFloor_target() - lift.getFloor_now())*0.5);
					stage_list[lift.getFloor_target()].getLight()[0] = Math.max(request.getTime(), this.time_now) + this.run_time + 1.0;
					door_open(request);
				}
				else {
					System.out.print("#SAME");
					printInfo(request);
					System.out.println();
				}
			}
			
		}
		else {
			if(request.getTime() != 0) {
				if(request.getTime() > floor_list[lift.getFloor_target()]) {
					this.run_time = Math.abs((lift.getFloor_target() - lift.getFloor_now())*0.5);
					floor_list[lift.getFloor_target()] = Math.max(request.getTime(), this.time_now) + this.run_time + 1.0;
					door_open(request);
				}
				else {
					System.out.print("#SAME");
					printInfo(request);
					System.out.println();
				}
			}
			else {
				this.run_time = Math.abs((lift.getFloor_target() - lift.getFloor_now())*0.5);
				floor_list[lift.getFloor_target()] = Math.max(request.getTime(), this.time_now);
				floor_list[lift.getFloor_target()] += this.run_time;
				floor_list[lift.getFloor_target()] += 1.0;
				door_open(request);
			}
		}
	}
	
	/**
	 * @REQUIRES: request != null;
	 * @MODIFIES: None;
	 * @EFFECTS: this.time_now == this.time_now + this.run_time + 1.0;
	 * 			 (lift.getFloor_target() == lift.getFloor_now()) ==> (save == "STILL" && System.out(save));
	 * 			 (lift.getFloor_target() > lift.getFloor_now()) ==> (save == "UP" && System.out(save));
	 * 			 (lift.getFloor_target() < lift.getFloor_now()) ==> (save == "DOWN" && System.out(save));
	 */
	public void door_open(Request request) {
		DecimalFormat df = new DecimalFormat("0.0");
		this.time_now += this.run_time;
		if(lift.getFloor_target() ==  lift.getFloor_now()) {
			this.time_now += 1.0;
			printInfo(request);
			System.out.println("/("+lift.getFloor_target()+","+"STILL,"+df.format(this.time_now)+")");
			save = "/("+lift.getFloor_target()+","+"STILL,"+df.format(this.time_now)+")";
		}
		else if(lift.getFloor_target() > lift.getFloor_now()){
			printInfo(request);
			System.out.println("/("+lift.getFloor_target()+","+"UP,"+df.format(this.time_now)+")");
			save = "/("+lift.getFloor_target()+","+"UP,"+df.format(this.time_now)+")";
			this.time_now += 1.0;
		}
		else {
			printInfo(request);
			System.out.println("/("+lift.getFloor_target()+","+"DOWN,"+df.format(this.time_now)+")");
			save = "/("+lift.getFloor_target()+","+"DOWN,"+df.format(this.time_now)+")";
			this.time_now += 1.0;
		}
		lift.setFloor_now(lift.getFloor_target());
	}
	
	
	/*
	 * 判定捎带
	 */
	/**
	 * @REQUIRES: request_main != null && request_test != null;
	 * @MODIFIES: None;
	 * @EFFECTS: (can be incident) ==> \result == 1;
	 * 			 (can't be incident) ==> \result == 0;
	 * 			 (request_main.equal(request_test)) ==> \result == 0;
	 */
	public int judge(Request request_main, Request request_test) {
		int code = 0;
		int floor_now = lift.getFloor_now();
		int floor_target = request_main.getFloor();
		int state;
		double time_temp=0.0;
		if(floor_target >= floor_now) {
			state = floor_target > floor_now ? 1 : 2;			
		}
		else {
			state = 0;
		}
		if(state == 1) {
			if(request_test.getFloor() >= floor_now) {
				if(request_test.getState() == 2 || (request_test.getState() == 1 && request_test.getFloor() <= floor_target)) {
					time_temp = Math.abs(0.5*(request_test.getFloor() - floor_now));
					if(time_now + time_temp > request_test.getTime()) {
						code =  1;
					}
				}
			}
		}
		else if(state == 0 ){
			if(request_test.getFloor() <= floor_now) {
				if(request_test.getState() == 2 || (request_test.getState() == 0 && request_test.getFloor() >= floor_target)) {
					time_temp = Math.abs(0.5*(request_test.getFloor() - floor_now));
					if(time_now + time_temp > request_test.getTime()) {
						code =  1;
					}
				}
			}
		}
		if(request_main.equals(request_test)) {
			code = 0;
		}
		return code;
	}
	
	/**
	 * @REQUIRES: request != null;
	 * @MODIFIES: System.out;
	 * @EFFECTS: (request.getState() == 2) ==> (System.out == "[ER,"+request.getFloor()+","+(int)request.getTime()+"]");
	 * 			 (request.getState() == 0) ==> (System.out == "[FR,"+request.getFloor()+",DOWN,"+(int)request.getTime()+"]");
	 * 			 (request.getState() == 1) ==> (System.out == "[FR,"+request.getFloor()+",UP,"+(int)request.getTime()+"]");
	 */
	public void printInfo(Request request) {
		if(request.getState() == 2) {
			System.out.print("[ER,"+request.getFloor()+","+(int)request.getTime()+"]");
		}
		else if(request.getState() == 0){
			System.out.print("[FR,"+request.getFloor()+",DOWN,"+(int)request.getTime()+"]");
		}
		else {
			System.out.print("[FR,"+request.getFloor()+",UP,"+(int)request.getTime()+"]");
		}
	}
	/*
	 * 判定同质
	 */
	/**
	 * @REQUIRES: request != null;
	 * @MODIFIES: None;
	 * @EFFECTS: (same request) ==> \result == 1;
	 * 			 (not same request) ==> \result == 0;
	 */
	public int same(Request request, boolean is_sd) {
		lift.setFloor_target(request.getFloor());
		lift.setState(request.getState());
//		if(!is_sd) {
//			if(request.getTime() > this.time_now) {
//				this.time_now = request.getTime();
//			}
//		}
		if(lift.getState() == 0) {
			if( request.getTime() != 0 ) {
				if(request.getTime() > stage_list[lift.getFloor_target()].getLight()[1]) 
					return 0;
				else 
					return 1;
			}
			else {
//				if(stage_list[lift.getFloor_target()].getLight()[1] == 0) 
//					return 0;
//				else 
				return 1;
			}
			
		}
		else if(lift.getState() == 1) {
			if(request.getTime() != 0) {
				if(request.getTime() > stage_list[lift.getFloor_target()].getLight()[0]) 
					return 0;
				else 
					return 1;
			}
			else {
				if(stage_list[lift.getFloor_target()].getLight()[0] == 0) 
					return 0;
				else 
					return 1;
			}
			
		}
		else {
			if(request.getTime() != 0) {
				if(request.getTime() > floor_list[lift.getFloor_target()]) 
					return 0;
				else 
					return 1;
			}
			else {
				if(floor_list[lift.getFloor_target()] == 0) 
					return 0;
				else 
					return 1;
			}
		}
	}
	
	/**
	 * @REQUIRES: request != null;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == request.getError() 
	 * || (this.count == 0 && !(request.getIs_in() == 0 && request.getState() == 1 && request.getFloor() == 1 && request.getTime() == 0))
	 * || (\all 0<=i<this.count; request.getTime() < requestlist[i].getTime());
	 */
	public static int is_error(Request request) {
		int errorcode=0;
		int i=0;
		if(request.getError() == 1) {
			errorcode = 1;
		}		
		else {
			if(count == 0) {
				if(request.getIs_in() == 0 && request.getState() == 1 && request.getFloor() == 1 && request.getTime() == 0) {
					errorcode = 0;
				}
				else errorcode = 1;
			}
			else {
				for(i=0;i<count;i++) {			
					if(request.getTime() < requestlist[i].getTime()) {
						errorcode = 1;
						break;
					}	
				}
			}
		}
		return errorcode;
	}
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: true ==> (start the program);
	 */
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(System.in);
			String str;
			while(count < 100) {
				str = sc.nextLine();
				str = str.replaceAll(" ", "");
				if(str.equals("RUN")) {
					break;
				}
				else {
					Request request = new Request(str);
					if(is_error(request) == 1) {
						System.out.println("INVALID["+str+"]");
					}
					else {
						requestlist[count++] = request;
//						requesttime[count-1] = request.getTime();
					}
				}
			}
			sc.close();
			
			Scheduler scheduler = new Scheduler();
			scheduler.run();
		}
		catch (Exception e){
			System.out.println("INVALID");
		}
		
	}
	
	/**
	 * @EFFECTS: \result == invariant(this);
	 */
	public boolean repOK() {
		if(time_now < 0) return false;
		return true;
	}
}
