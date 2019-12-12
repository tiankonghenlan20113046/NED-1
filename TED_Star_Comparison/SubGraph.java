import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class SubGraph {
	
	GraphNode root;
	int k;
	HashMap<Integer, Integer> nodeIndex; // Node Index: the keys are the node id in the subGraph, the values are the node id in the original Graph 
	HashMap<Integer, Integer> invertedNodeIndex; // Node Index: the keys are the node id in the original Graph, the values are the node id in the subGraph
	int size;
	HashMap<Integer, GraphNode> nodeMap;
	
	// Create a k-hop subgraph from a graph
	public SubGraph(int input_rootID, int input_k, Graph g, int start){
		this.k = input_k;
		this.root = new GraphNode(start);
		this.nodeIndex = new HashMap<Integer, Integer>();
		this.invertedNodeIndex = new HashMap<Integer, Integer>();
		this.nodeIndex.put(start, input_rootID);
		this.invertedNodeIndex.put(input_rootID, start);
		this.nodeMap = new HashMap<Integer, GraphNode>();
		this.nodeMap.put(start, this.root);

		int nodeID = start; // Node ID Generator
		LinkedList<GraphNode> bfsQueue = new LinkedList<GraphNode>(); // The queue for BFS
		bfsQueue.add(this.root); // Add the root node ID to the queue
		while(bfsQueue.size() > 0){
			GraphNode currentNode = bfsQueue.poll();
			if(currentNode.level < this.k){
				HashSet<Integer> neighbors = g.nodeMap.get(nodeIndex.get(currentNode.id));
				for(int id : neighbors){
					if(!this.invertedNodeIndex.keySet().contains(id)) {
						nodeID++;
						this.nodeIndex.put(nodeID, id);
						this.invertedNodeIndex.put(id, nodeID);
						this.nodeMap.put(nodeID, new GraphNode(nodeID, currentNode));
						bfsQueue.add(this.nodeMap.get(nodeID));
					}
					currentNode.addNeighbor(this.nodeMap.get(this.invertedNodeIndex.get(id)));
					this.nodeMap.get(this.invertedNodeIndex.get(id)).addNeighbor(currentNode);
				}
			}
		}
		this.size = nodeID - start + 1;
	}
	
	public String toEdgeList(){
		String str = "";
		str += "rootID\t" + root.id + "\n";
		for(int i : this.nodeMap.keySet()) {
			GraphNode n = this.nodeMap.get(i);
			for(GraphNode m : n.neighbors)
				str += n.id + "\t" + m.id + "\n";
		}
		return str;
	}	
	
	public String toString(){
		String str = "";
		for(int i : this.nodeMap.keySet()) {
			GraphNode n = this.nodeMap.get(i);
			str += n.toString() + "\n";
		}
		return str;
	}
}