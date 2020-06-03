package exp1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class expHandler{
	public static void err(int code){
		if(code==1){
			System.out.println("err code : " + code);
		}
		else if(code==2)
		{
			System.out.println("new error: " + code);
		}
		System.exit(1);
	}
}

class matrix{
	private int[][] mat;
	private static String str_i = "\\{(\\{(\\([0-9]{1,6},[0-9]{1,6}\\),){0,}\\([0-9]{1,6},[0-9]{1,6}\\)\\},){0,}\\{(\\([0-9]{1,6},[0-9]{1,6}\\),){1,}\\([0-9]{1,6},[0-9]{1,6}\\)\\}\\}";
	public matrix(){
		mat = null;
	}
	
	public matrix (int order){
		mat = new int[order][order];
	}
	
	public matrix(String str){
		int order;
		if(find(str,str_i)) {
			expHandler.err(1);
		}
    	String [] strs=str.split("[{},]");//鍒掑垎涓嶆纭紝杞箟瀛楃
    	int i;
    	for(i=2;i<strs.length;i++){
    		if(!strs[i].equals(""))
    			continue;
    		else 
    			break;
    	}
    	order=i-2;
    	
    	if(order==0){
    		expHandler.err(1);
    	}
    	mat=new int[order][order]; //娌℃湁姝ｇ‘鐨勮祴鍊�,澹版槑鐨勬槸mat鏁扮粍
    	if((order*order)!=strs.length) {
    		expHandler.err(1);
    	}//鍒ゆ柇鏄惁鏄柟闃�
    	
    	int j;
    	for(i=0;i<strs.length;i+=2+order){
    		for(j=0;j<order;j++){
    			mat[i/(2+order)][j]=Integer.parseInt(strs[i+2+j]);
    		}
    	}
    }
	
	public boolean find(String str_out, String str_in) {
		Pattern p = Pattern.compile(str_in);
		Matcher m = p.matcher(str_out);
		return m.find();
	}   //鍒ゆ柇鏄惁鏈夐潪娉曡緭鍏�
	
	public int getOrder(){
		return mat.length;
	}
	
	public matrix add(matrix addThis){
		int i, j, order;
		order = getOrder();
		matrix temp = new matrix(order);
		for(i = 0; i < order; i++){
			for(j = 0; j < order; j++){
				temp.mat[i][j] = mat[i][j] + addThis.mat[i][j];
				if(temp.mat[i][j] > 2147483647 || temp.mat[i][j] < -2147483648){
					expHandler.err(1); //鍒ゆ柇鏄惁婧㈠嚭锛屽苟杞悜绗簩绉嶉敊璇�
				}
			}
		}
		return temp;
	}
	
	public matrix sub(matrix subThis){
		int i, j, order;
		order = getOrder();
		matrix temp = new matrix(order);
		for(i = 0; i < order; i++){
			for(j = 0; j < order; j++){
				temp.mat[i][j] = mat[i][j] - subThis.mat[i][j];
				if(temp.mat[i][j] > 2147483647 || temp.mat[i][j] < -2147483648){
					expHandler.err(1); //鍒ゆ柇鏄惁婧㈠嚭锛屽苟杞悜绗簩绉嶉敊璇�
				}
			}
		}
		return temp;
	}
	
	public matrix transpose(){
		int order;
		order = getOrder();
		matrix temp = new matrix(order);
		int i, j;
		for(i = 0; i < order; i++){
			for(j = 0; j < order; j++){
				temp.mat[i][j] = mat[j][i];
			}
		}
		return temp;
	}
	
	public matrix multiply(matrix multiplyThis){
		int i, j, k, order, element;
		order = getOrder();
		matrix temp = new matrix(order);
		for(i = 0; i < order; i++){
			for(j = 0; j < order; j++){
				element = 0;
				for(k = 0; k < order; k++){
					element += mat[i][k] * multiplyThis.mat[k][j];
					if(temp.mat[i][j] > 2147483647 || temp.mat[i][j] < -2147483648){
						expHandler.err(1); //鍒ゆ柇鏄惁婧㈠嚭锛屽苟杞悜绗簩绉嶉敊璇�
					}
				}
				temp.mat[i][j] = element;
			}
		}
		return temp;
	}
	
	public String toString(){
		String s = new String();
		int i, j, order;
		order = getOrder();
		for(i = 0; i < order; i++){
			for(j = 0; j < order; j++){
				s += String.valueOf(mat[i][j]);
				s += '\t';
			}
			s = s + '\n';
		}
		return(s);
	}
	
}


public class matrixCal {
	int[][] matrix1, matrix2, answer ;
	int dim;
	char operator;
	public static void main (String[] args){
		Scanner keyboard = new Scanner(System.in);
		matrix m1 = new matrix(keyboard.nextLine()); //灏嗚緭鍏ュ彉鎴愮煩闃靛舰寮忥紝鐢ㄦ瀯閫犳柟娉�
		String op;
		char operator = '\0';
		op = keyboard.nextLine(); //杈撳叆杩愮畻绗﹀彿
		if(op == null) expHandler.err(1);
		if(op.length() == 1) operator = op.charAt(0);
		else expHandler.err(1);
		if(operator == 't'){
			System.out.print(m1.transpose());
		}
		if(operator == '+'){
			matrix m2 = new matrix(keyboard.nextLine());
			System.out.print(m1.add(m2));
		}
		if(operator == '-'){
			matrix m2 = new matrix(keyboard.nextLine());
			System.out.print(m1.sub(m2));
		}
		if(operator == '*'){
			matrix m2 = new matrix(keyboard.nextLine());
			System.out.print(m1.multiply(m2));
		}
		keyboard.close();
	}
	
	
}