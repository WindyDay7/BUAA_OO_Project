package oo.app;

import java.util.Vector;

public class GraphInfo {
	static class GraphCal{
		static int [][] distance = new int [6405][6405];
		
		public synchronized static int[][] initMatrix(int map[][]) {
			int graph[][] = new int[6405][6405];
			int MAXNUM = 1000000;
			for (int i = 0; i < 6400; i++) {
				for (int j = 0; j < 6400; j++) {
					if (i == j)
						graph[i][j] = 0;
					else
						graph[i][j] = MAXNUM;
				}
			}
			for (int i = 0; i < 80; i++) {
				for (int j = 0; j < 80; j++) {
					if (map[i][j] == 1 || map[i][j] == 3) {
						graph[i * 80 + j][i * 80 + j + 1] = 1;
						graph[i * 80 + j + 1][i * 80 + j] = 1;
					}
					if (map[i][j] == 2 || map[i][j] == 3) {
						graph[i * 80 + j][(i + 1) * 80 + j] = 1;
						graph[(i + 1) * 80 + j][i * 80 + j] = 1;
					}
				}
			}
			return graph;
		}
		public synchronized static Vector<NNode> pointBFS(int root,int dst,int[][] graph) {
			try {
				int[] offset = new int[] { 0, 1, -1, 80, -80 };
				Vector<NNode> queue = new Vector<NNode>();
				NNode[] accessednode = new NNode[6405];
				boolean[] view = new boolean[6405];
				for (int i = 0; i < 6400; i++) {
					for (int j = 0; j < 6400; j++) {
						if (i == j) {
							distance[i][j] = 0;
						} else {
							distance[i][j] = graph[i][j];//initialize
						}
					}
				}
				int x = root;
				for (int i = 0; i < 6400; i++)
					view[i] = false;
				queue.add(new NNode(x, 0));
				accessednode[root] = queue.get(0);
				while (queue.size() > 0) {
					NNode node = queue.get(0);
					view[node.No] = true;
					for (int i = 1; i <= 4; i++) {
						int next = node.No + offset[i];
						if (next >= 0 && next < 6400 && view[next] == false && graph[node.No][next] == 1) {
							view[next] = true;
							NNode nextnode = new NNode(next, node.depth + 1);
							nextnode.last = node;
							accessednode[next] = nextnode;
							queue.add(nextnode);
							
							distance[x][next] = node.depth + 1;
							distance[next][x] = node.depth + 1;
						}
					}
					queue.remove(0);
				}
				return getPath(accessednode[dst],root);
			}catch(Exception e) {
				return null;
			}
		}
		
		public synchronized static Vector<NNode> getPath(NNode dst,int root) {
			try {
				Vector<NNode> path = new Vector<NNode>();
				NNode node = dst;
				
				while(node.No!=root) {//nullpointer
					path.insertElementAt(node, 0);
					node = node.last;
				}
				path.insertElementAt(node, 0);
				return path;
			}catch(Exception e) {
				return null;
			}
		}
	}
}
