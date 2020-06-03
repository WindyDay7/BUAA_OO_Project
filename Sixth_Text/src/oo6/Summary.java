package oo6;

import java.io.File;
import java.io.PrintStream;

public class Summary {
	private PrintStream output;
	private long renamed=0;
	private long modified=0;
	private long pathChanged=0;
	private long sizeChanged=0;
	private long count=0;
	
	public Summary() {
		try {
			File f=new File("C:\\OO6output\\summaryOutput.txt");
			f.createNewFile();
			PrintStream output = new PrintStream("C:\\OO6output\\summaryOutput.txt");
			this.output=output;
			//System.setOut(output);
		}
		catch(Exception e) {
			
		}
	}
	synchronized public void addRenamed() {
		this.renamed++;
	}
	synchronized public void addModified() {
		this.modified++;
	}
	synchronized public void addPathChanged() {
		this.pathChanged++;
	}
	synchronized public void addSizeChanged() {
		this.sizeChanged++;
	}
	synchronized public void output() {
		System.setOut(this.output);
		System.out.println("SummaryOutput "+this.count);
		this.count++;
		System.out.println("renamed: "+this.renamed);
		System.out.println("modified: "+this.modified);
		System.out.println("pathChanged: "+this.pathChanged);
		System.out.println("sizeChanged: "+this.sizeChanged);
		System.out.println("##############################");
	}
}
