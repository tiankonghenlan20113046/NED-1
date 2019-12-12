import java.util.HashSet;

public class GraphNode {
	
	int id;
	HashSet<GraphNode> neighbors;
	int level;
	
	// Create a root node
	public GraphNode(int root){
		this.id = root;
		this.neighbors = new HashSet<GraphNode>();
		this.level = 0;
	}
	
	// Create a root node
	public GraphNode(int input_id, GraphNode source){
		this.id = input_id;
		this.neighbors = new HashSet<GraphNode>();
		this.level = source.level + 1;
	}
	
	// Create a leaf node
	public void addNeighbor(GraphNode neighbor){
		this.neighbors.add(neighbor);
	}

	public String toString(){
		String str = this.id + "(";
		for(GraphNode n : this.neighbors) {
			str += n.id + ",";
		}
		if(this.neighbors.size() > 0)
			str = str.substring(0, str.length()-1) + ")";
		else
			str += ")";
		return str;
	}

}