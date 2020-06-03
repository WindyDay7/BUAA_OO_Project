package oo.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputInfo {
	private static File output2file;
	private String outputstr;
	static private OutputStream outputstream;
	
	public OutputInfo(File output2file) {
		this.output2file = output2file;
		this.outputstr = "";
		try {
			this.outputstream = new FileOutputStream(this.output2file,true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void appendDetail(String info) {
		outputstr = outputstr + info;
	}
	
	public void flush() {
		try {
		byte[] tmpbarr;
		tmpbarr = this.outputstr.getBytes();
		this.outputstream.write(tmpbarr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
