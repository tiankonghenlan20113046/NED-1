public class TreeNode {
	
	int id;
	TreeNode parent;
	TreeNode[] children;
	int level;
	
	// Create a root node
	public TreeNode(int input_id){
		this.id = input_id;
		this.parent = null;
		this.children = new TreeNode[0];
		this.level = 0;
	}
	
	// Create a leaf node
	public TreeNode(int input_id, TreeNode input_parent){
		this.id = input_id;
		this.parent = input_parent;
		this.children = new TreeNode[0];
		this.level = input_parent.level + 1;
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