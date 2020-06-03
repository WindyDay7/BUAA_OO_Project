package oo6;
import java.io.File;
import java.util.Vector;
import java.io.PrintStream;
//import java.io.FileNotFoundException; 
public class Detail {
	private PrintStream output;
	private long count=0;
	private Vector<String> renamed=new Vector<String> ();
	//private long renamedindex;
	private Vector<String> modified=new Vector<String> ();
	private Vector<String> pathChanged=new Vector<String> ();
	private Vector<String> sizeChanged=new Vector<String> ();
	public Detail() {
		try {
			File f=new File("C:\\OO6output\\detailOutput.txt");
			f.createNewFile();
			PrintStream output = new PrintStream("C:\\OO6output\\detailOutput.txt");
			this.output=output;
			//System.setOut(output);
		}
		catch(Exception e) {
			
		}
	}
	synchronized public void addRnamed(String s) {
		this.renamed.add(s);
		
	}
	synchronized public void addModified(String s) {
		this.modified.add(s);
	}
	synchronized public void addPathChanged(String s) {
		this.pathChanged.add(s);
	}
	synchronized public void addSizeChanged(String s) {
		this.sizeChanged.add(s);
	}
	synchronized public void outputtest() {
		System.setOut(this.output);
		System.out.println("sssssssss111111111");
	}
	synchronized public void output() {
		
		System.setOut(this.output);
		System.out.println("DetailOutput "+this.count);
		this.count++;
		int i;
		
		System.out.println("renamed detail:");
		for(i=0;i<this.renamed.size();i++) {
			System.out.println("    #"+i+": "+this.renamed.get(i));
		}
		this.renamed.clear();
		
		System.out.println("modified detail:");
		for(i=0;i<this.modified.size();i++) {
			System.out.println("    #"+i+": "+this.modified.get(i));
		}
		this.modified.clear();
		
		System.out.println("pathChanged detail:");
		for(i=0;i<this.pathChanged.size();i++) {
			System.out.println("    #"+i+": "+this.pathChanged.get(i));
		}
		this.pathChanged.clear();
		
		System.out.println("sizeChanged detail:");
		for(i=0;i<this.sizeChanged.size();i++) {
			System.out.println("    #"+i+": "+this.sizeChanged.get(i));
		}
		this.sizeChanged.clear();
		
		System.out.println("#################################");
		//System.setOut(System.out);
	}
}
