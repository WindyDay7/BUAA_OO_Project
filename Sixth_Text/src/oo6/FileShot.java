package oo6;


class FileShot {
	int log;
	int children;
	FileShot[] next;
	long size;
	long lastmodify;
	int renamed=0;
	String absolutename;
	String parent;
	String name;
	public FileShot(int log, int children, long size, long lastmodify, String absolutename, String name, String parent) {
		this.log=log;
		this.children=children;
		this.size=size;
		this.lastmodify=lastmodify;
		this.name=name;
		this.absolutename=absolutename;
		this.next=new FileShot[this.children];
		this.parent=parent;
		this.renamed=0;
	}
}