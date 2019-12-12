import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class KAT {
	
	TreeNode root;
	int k;
	HashMap<Integer, Integer> nodeIndex; // Node Index: the keys are the node id in the KAT, the values are the node id in the original Graph
	int size;
	HashMap<Integer, ArrayList<TreeNode>> AT; // Record node array per level
	int[] maxDegree; // Record maximal degree of the nodes per level
	
	// Create a k-adjacent tree from a graph (adjacent list)
	public KAT(int input_rootID, int input_k, Graph g, int start){
		this.k = input_k;
		this.root = new TreeNode(start);
		this.nodeIndex = new HashMap<Integer, Integer>();
		this.nodeIndex.put(start, input_rootID);
		this.AT = new HashMap<Integer, ArrayList<TreeNode>>();
		this.maxDegree = new int[k+1];
		for(int i = 0; i <= this.k; i++) {
			AT.put(i, new ArrayList<TreeNode>());
			maxDegree[i] = 0;
		}
		this.AT.get(0).add(root);

		int nodeID = start; // Node ID Generator
		LinkedList<TreeNode> bfsQueue = new LinkedList<TreeNode>(); // The queue for BFS
		bfsQueue.add(this.root); // Add the root node ID to the queue
		while(bfsQueue.size() > 0){
			TreeNode currentNode = bfsQueue.poll();
			if(currentNode.level < this.k){
				HashSet<Integer> newChildren = g.nodeMap.get(nodeIndex.get(currentNode.id));
				if(currentNode.parent != null) {
					currentNode.children = new TreeNode[newChildren.size()-1];
					int iterCounter = 0;
					for(int child : newChildren){
						if(this.nodeIndex.get(currentNode.parent.id) != child) {
							nodeID++;
							TreeNode newNode = new TreeNode(nodeID, currentNode);
							currentNode.children[iterCounter] = newNode;
							iterCounter++;
							this.nodeIndex.put(nodeID, child);
							this.AT.get(newNode.level).add(newNode);
							bfsQueue.add(newNode);
						}
					}
				}
				else {
					currentNode.children = new TreeNode[newChildren.size()];
					int iterCounter = 0;
					for(int child : newChildren){
						nodeID++;
						TreeNode newNode = new TreeNode(nodeID, currentNode);
						currentNode.children[iterCounter] = newNode;
						iterCounter++;
						this.nodeIndex.put(nodeID, child);
						this.AT.get(newNode.level).add(newNode);
						bfsQueue.add(newNode);
					}
				}
				if(maxDegree[currentNode.level] < currentNode.children.length)
					maxDegree[currentNode.level] = currentNode.children.length;
			}
		}
		this.size = nodeID - start + 1;
	}
	
	public String toEdgeList(){
		String str = "";
		str += "k\t" + this.k + "\n";
		str += "maxDegree\t";
		for(int  i = 0; i < maxDegree.length; i++)
			str += maxDegree[i] + ",";
		if(str.charAt(str.length()-1) == ',')
			str = str.substring(0, str.length()-1) + "\n";
		str += "rootID\t" + root.id + "\n";
		for(int  i = 0; i < this.k + 1; i++){
			for(TreeNode  n : this.AT.get(i)){
				for(TreeNode m : n.children)
					str += n.id + "\t" + m.id + "\n";
			}
		}
		return str;
	}
	
	public String toString(){
		String str = "K = " + this.k + "\n";
		for(int i = 0; i <= this.k; i++) {
			str += "Level " + i + ":\t";
			for(int j = 0; j < this.AT.get(i).size(); j++)
				str += this.AT.get(i).get(j).toString() + ",";
			if(this.AT.get(i).size() > 0)
				str = str.substring(0, str.length()-1);
			str += "\n";
		}
		return str.substring(0, str.length()-1);
	}
}