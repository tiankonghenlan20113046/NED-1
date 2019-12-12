public class Node_TED {
	
	int id;
	Node_TED parent;
	Node_TED[] children;
	int level;
	
	// Create a root node
	public Node_TED(int input_id){
		this.id = input_id;
		this.parent = null;
		this.children = new Node_TED[0];
		this.level = 0;
	}
	
	// Create other nodes
	public Node_TED(int input_id, Node_TED input_parent){
		this.id = input_id;
		this.parent = input_parent;
		this.children = new Node_TED[0];
		this.level = input_parent.level + 1;
	}
	
	public void addChild(Node_TED newChild){
		Node_TED[] tmp = new Node_TED[this.children.length + 1];
		for(int i = 0; i < this.children.length; i++)
			tmp[i] = this.children[i];
		tmp[this.children.length] = newChild;
		this.children = tmp.clone();
	}

	public String toString(){
		String str = this.id + "(";
		for(int i = 0; i < this.children.length - 1; i++) {
			str += this.children[i].id + ",";
		}
		if(this.children.length > 0)
			str += this.children[this.children.length - 1].id + ")";
		else
			str += ")";
		return str;
	}
}