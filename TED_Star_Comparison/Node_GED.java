import java.util.HashSet;

public class Node_GED {
	
	int id;
	HashSet<Node_GED> neighbor;
	
	public Node_GED(int id){
		this.id = id;
		this.neighbor = new HashSet<Node_GED>();
	}
	
	public void addNeighbor(Node_GED p){
		this.neighbor.add(p);
	}
	
	public boolean isNeighbor(Node_GED p){
		return this.neighbor.contains(p);
	}
	
	public boolean isEmpty(){
		return this.id == -1;
	}
	
	public String toString(){
		String str = "";
		str += this.id + " : ";
		for(Node_GED n : this.neighbor){
			str += n.id + ", ";
		}
		str = str.substring(0, str.length()-2);
		return str;
	}

}
