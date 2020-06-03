package six;



class File_main {
	public void test() {
		In_put in_put = new In_put();
		in_put.input();
		/*String path1 = "‪F:\\oo_code\\tey";
		String path2 = "F:\\oo_code\\tey\\Demand.java";
		Path_changed Rename_try = new Path_changed(path1,path2,2);
		new Thread(Rename_try).start();*/
		Test_thread test_thread = new Test_thread();
		new Thread(test_thread).start();
		return;
	}
	
	public static void main(String[] args) {
		File_main Run = new File_main();
		
		Run.test();
	}
}
