package test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LHandler {
	FileReader r;
	BufferedReader br;
	boolean[][] lmap;
	
	private String regex = "[0-1]{80}";
	private Pattern p = Pattern.compile(regex);

	/**
	 * @REQUIRES:(\exist String fname);
	 * @MODIFIES: this;
	 * @EFFECTS:
	 * ! (\exist File fp; fp.path == fname)
	 * ==> exceptional_behavior(FileNotFoundException);
	 * (\exist File fp; fp.path == fname)==>
	 * (r == FileReader(fname)) && (br == BufferedReader(r));
	 */
	public LHandler(String fname) throws FileNotFoundException{
		try {
			r = new FileReader(fname);
			br = new BufferedReader(r);
		}catch(FileNotFoundException e) {
			throw e;
		}
		lmap = new boolean[80][80];
	}
	boolean repOK() {
		return true;
	}
	
	/**
	 * @REQUIRES:
	 * The file read by br must obey the rules in Readme;
	 * @MODIFIES: lmap;
	 * @EFFECTS:
	 * (\all int i; 0 <= i < 80) && (\all int j; 0 <= j < 80)
	 * ==> (lmap[i][j] == fp + i * 80 + j);
	 */
	public void scan() throws IOException {
		int i, j;
		String temp;
		for(i = 0; i < 80; i++) {
			try {
				temp = br.readLine();
			}catch(IOException e) {
				throw e;
			}
			
			if(validate(temp)) {
				for(j = 0; j < 80; j++) {
					lmap[i][j] = (temp.charAt(j) == '1');
				}
			}
			else {
				System.out.println("Error in traffic light file !");
				break;
			}
		}
	}
	/**
	 * @REQUIRES: String in;
	 * @MODIFIES: none;
	 * @EFFECTS:
	 * (\result == (p.matches(in)));
	 */
	public boolean validate(String in) {
		String temp = in.replaceAll("[\t ]", "");
		Matcher m = p.matcher(temp);
		return m.matches();
	}
}
