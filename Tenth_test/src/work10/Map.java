package work10;
/**
 * @overview 统计地图文件中的信息，将他们加入到两个数组中，一个用来构建地图和连接情况，一个记录各点的数字。
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class Map {
	private Vector<Integer>[] lattice = new Vector[6400];
	private String filename;
	private String data;
	int[][] mapnumber = new int[80][80];

	Map(String fn) {
        /**
         * @REQUIRES:fn!=None;
         * @MODIFIES:
         *      \this.filename;
         *      \this.lattice;
         * @EFFECTS:
         *      \this.filename=fn;
         */
		filename = fn;
		for (int i = 0; i < 6400; i++)
			lattice[i] = new Vector<>();
	}
	public boolean repOK() {
		 /**
         * @REQUIRES:None;
         * @MODIFIES:
         *      None
         * @EFFECTS:
         *      \result=(lattice!=null)&&filename!=null&&data!=null&&mapnumber!=null;
         */
		return (lattice!=null)&&filename!=null&&data!=null&&mapnumber!=null;
	}
	public boolean set() {
		/**
         * @REQUIRES:filename!=None;
         * @MODIFIES:
         * 			this.mapnumber;
         * 			this.lattice;
         * @EFFECTS:
         * 	normal_behavior:
         * 		文件存在，并且文件的行数和列数均为80，并且满足了地图的创建要求，均为0|1|2|3，创建地图，将数字存入二维数组mapnumber中
         *     根据0|1|2|3的关系，判断他们的连接关系将他们存入二维数组lattice。返回true
         *  exceptional_behavior :
         *  	文件不存在或出现未知的问题，返回false;
         */
		BufferedReader bufferReader;
		StringBuilder sb = new StringBuilder();
		if (!new File(filename).exists()) {
			System.out.println("地图文件不存在");
			System.exit(1);
			return false;
		}
		try {
			InputStream inputStream = new FileInputStream(new File(filename));
			bufferReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			int lineCount = 0;
			while ((line = bufferReader.readLine()) != null) {
				String s = line.replaceAll("[ \t]", "");// 去制表符
				s = s.replaceAll(" ", "");// 去空格
				if (s.length() != 80)
					return false;// 长度不对报错
				for (int i = 0; i < s.length(); i++) {// 非法字符报错
					if (s.charAt(i) != '0' && s.charAt(i) != '1' && s.charAt(i) != '2' && s.charAt(i) != '3')
						return false;
					else
						this.mapnumber[lineCount][i] = s.charAt(i) - '0';
				}
				sb.append(s);
				lineCount++;
			}
			if (lineCount != 80)
				return false;// 行数不对报错
			bufferReader.close();
		} catch (Exception e) {
			return false;
		}
		data = sb.toString();
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			if (c == '1' || c == '3') {
				lattice[i].add(i + 1);
				lattice[i + 1].add(i);
			}
			if (c == '2' || c == '3') {
				lattice[i].add(i + 80);
				lattice[i + 80].add(i);
			}
		}
		return true;
	}

	public Vector[] getlattice() {
		/**
         * @REQUIRES:this.lattice!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         * 			\result=lattice;
         * 			返回各边连接情况的数组
         */
		return lattice;
	}

	public int[][] getmapnumber() {
		/**
         * @REQUIRES:this.mapnumber!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         * 			\result=mapunmber;
         * 			返回二维数组
         */
		return mapnumber;
	}
}
