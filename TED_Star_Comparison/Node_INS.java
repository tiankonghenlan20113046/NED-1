public class Node_INS implements Comparable<Node_INS>{
	
	int id;
	Node_INS parent;
	Node_INS[] children;
	int level;
	CanonicalLabel cLabel;
	
	// Create a root node
	public Node_INS(int input_id){
		this.id = input_id;
		this.parent = null;
		this.children = new Node_INS[0];
		this.level = 0;
		this.cLabel = new CanonicalLabel();
	}
	
	// Create a leaf node
	public Node_INS(int input_id, Node_INS input_parent){
		this.id = input_id;
		this.parent = input_parent;
		this.children = new Node_INS[0];
		this.level = input_parent.level + 1;
		this.cLabel = new CanonicalLabel();
	}
	
	public void addChild(Node_INS newChild){
		Node_INS[] tmp = new Node_INS[this.children.length + 1];
		for(int i = 0; i < this.children.length; i++)
			tmp[i] = this.children[i];
		tmp[this.children.length] = newChild;
		this.children = tmp.clone();
	}

	public String toString(){
		String str = this.id + "<" + this.cLabel.label + ">(";
		for(int i = 0; i < this.children.length - 1; i++) {
			str += this.children[i].id + "<" + this.children[i].cLabel.label + ">" + ",";
		}
		if(this.children.length > 0)
			str += this.children[this.children.length - 1].id + "<" + this.children[this.children.length - 1].cLabel.label + ">" + ")";
		else
			str += ")";
		return str;
	}

	@Override
	public int compareTo(Node_INS other) {
		return this.cLabel.compareTo(other.cLabel);
	}
}