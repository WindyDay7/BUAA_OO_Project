package test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MHandler {
	/**@OVERVIEW:
	 * A MHandler read a specific file and parse it into an int[][] ;
	 * 
	 * @INVARIANT:
	 * (\all int i; 0 <= i < 80)(\all int j; 0 <= j < 80)
	 * ==>(0 <= map[i][j] <= 3);
	 */
	private String regex = "[0-3]{80}";
	FileReader r;
	BufferedReader br;
	int[][] map;

	/**
	 * @REQUIRES:(\exist String fname; fname != null);
	 * @MODIFIES:this;
	 * @EFFECTS:
	 * ! (\exist File fp; fp.path == fname)
	 * ==> exceptional_behavior(FileNotFoundException);
	 * (\exist File fp; fp.path == fname)==>
	 * (r == FileReader(fname)) && (br == BufferedReader(r));
	 */
	public MHandler(String fname) throws FileNotFoundException{
		try {
			r = new FileReader(fname);
			br = new BufferedReader(r);
		}catch(FileNotFoundException e) {
			throw e;
		}
		map = new int[80][80];
	}
	boolean repOK() {
		boolean ret = true;
		int i, j;
		for(i = 0; i < 80; i++) {
			for(j = 0; j < 80; j++) {
				if(map[i][j] > 3 || map[i][j] < 0) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * @REQUIRES:(\exist File map.txt);
	 * @MODIFIES:map;
	 * @EFFECTS:(map.txt.valid() ==> map==map.txt.parse()&&\result==true);
	 * (!map.txt.valid()==>\result==false);
	 */
	public boolean scan() {
		try {
			String read;
			int i;
			for(i = 0; i < 80; i++) {
				read = br.readLine();
				if(this.validate(read)) {
					this.parse(read, i);
				}
				else {
					System.out.println("Wrong format map");
					return false;
				}
			}
			br.close();
			return true;
		}catch(Exception e) {
			System.out.println("Exception in map.txt");
			return false;
		}
	}
	/**
	 * @REQUIRES:(\exist String in);
	 * @MODIFIES:none;
	 * @EFFECTS:(\result == in.matches(regex));
	 */
	public boolean validate(String in) {
		Pattern p = Pattern.compile(regex);
		String get = in.replaceAll("[ \t]", "");
		Matcher m = p.matcher(get);
		return m.matches();
	}
	/**
	 * @REQUIRES:(\exist String in; in.matches(regex));
	 * (\exist int line; 0<=line<=79);
	 * @MODIFIES:map;
	 * @EFFECTS:(map[line] == in.parse());
	 */
	public void parse(String in, int line) {
		int j;
		for(j = 0; j < 80; j++) {
			int con = in.charAt(j) - '0';
			map[line][j] = con;
		}
	}
}
