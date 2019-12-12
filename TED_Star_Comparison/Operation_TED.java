
public class Operation_TED {
	
	Node_TED src;
	Node_TED dst;
	
	public Operation_TED(Node_TED src, Node_TED dst){
		this.src = src;
		this.dst = dst;
	}
	
	public String toString(){
		return "(" + this.src.id + "->" + this.dst.id + ")";
	}

}
