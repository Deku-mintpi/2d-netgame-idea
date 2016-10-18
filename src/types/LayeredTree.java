package types;

import java.util.ArrayDeque;
import java.util.Map;

public class LayeredTree {
	public ArrayDeque<QuadLayer> layerList;
	
	class QuadLayer {
		public Map nodeMap;
		boolean isDynamic;
		
		public QuadNode getNode(int index) {
			
		}
	}
	
	class QuadNode {
		public int[] children;
		public int[] 
	}
}
