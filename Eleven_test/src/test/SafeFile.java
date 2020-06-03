package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SafeFile {
	private File file;
	private BufferedWriter writer;

	/**
	 * @REQUIRES:(\exist String fname);
	 * @MODIFIES:this;
	 * @EFFECTS:
	 * (!fname.valid())==>exceptional_behavior(IOException);
	 * (fname.valid)==>(create the file and its writer);
	 */
	public SafeFile(String fname){
		try{
			file = new File(fname);
			file.createNewFile();
			writer = new BufferedWriter(new FileWriter(file));
		}
		catch(IOException e){
			System.out.println("# An IOException has occurred.");
			e.printStackTrace();
		}
	}
	public boolean repOK() {
		return file.canExecute();
	}
	
	/**
	 * @REQUIRES:(\exist String str);
	 * @MODIFIES:Buffer
	 * @EFFECTS:(Buffer == \old(Buffer) + str);
	 */
	public synchronized void write(String str) {
		try {
			writer.write(str);
		}catch(IOException e) {
			System.out.println("IOException in output.txt");
			e.printStackTrace();
		}
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:file;
	 * @EFFECTS:file == \old(file)+Buffer;
	 */
	public synchronized void flush(){
		try {
			writer.flush();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("IOException in output.txt");
			e.printStackTrace();
		}
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:file;
	 * @EFFECTS: file == \old(file) + LineSaparator;
	 */
	public synchronized void newLine() {
		try {
			writer.newLine();
		}catch(IOException e) {
			System.out.println("IOException in output.txt");
			e.printStackTrace();
		}
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:writer;
	 * @EFFECTS: close writer;
	 */
	public synchronized void close() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("IOException in output.txt");
			e.printStackTrace();
		}
	}
}
