
public class Operation_GED {
	
	Node_GED src;
	Node_GED dst;
	
	public Operation_GED(Node_GED src, Node_GED dst){
		this.src = src;
		this.dst = dst;
	}
	
	public String toString(){
		return "(" + this.src.id + "->" + this.dst.id + ")";
	}

}
