import java.util.HashSet;
import java.util.LinkedList;

public class AStarNode_GED implements Comparable<AStarNode_GED>{

	LinkedList<Operation_GED> OperationPath;
	int h;
	int g;
	int f;
	
	public AStarNode_GED(){
		this.OperationPath = new LinkedList<Operation_GED>();
		this.h = 0;
		this.g = 0;
		this.f = 0;
	}
	
	public AStarNode_GED(AStarNode_GED aNode, Operation_GED op){
		this.OperationPath = new LinkedList<Operation_GED>();
		this.OperationPath.addAll(aNode.OperationPath);
		this.OperationPath.add(op);
		this.h = 0;
		this.g = aNode.g + distance(aNode, op);
		this.f = this.h + this.g;
	}
	
	public int distance(AStarNode_GED aNode, Operation_GED op){
		int disagreement = 0;
		if(op.src.isEmpty() || op.dst.isEmpty())
			disagreement++;
		for(Operation_GED o: aNode.OperationPath){
			if (!o.src.isNeighbor(op.src) && o.dst.isNeighbor(op.dst))
				disagreement++;
			if (o.src.isNeighbor(op.src) && !o.dst.isNeighbor(op.dst))
				disagreement++;
		}
		return disagreement;
	}
	
	public boolean isComplete(HashSet<Node_GED> srcns, HashSet<Node_GED> dstns) {
		for(Operation_GED op : this.OperationPath){
			Node_GED src = op.src;
			Node_GED dst = op.dst;
			srcns.remove(src);
			dstns.remove(dst);
		}
		return srcns.isEmpty() && dstns.isEmpty();
	}
	
	public int size(){
		return this.OperationPath.size();
	}
	
	@Override
	public int compareTo(AStarNode_GED other) {
		if(this.f != other.f)
			return this.f - other.f;
		else
			if(this.g != other.g)
				return this.g - other.g;
			else
				return this.h - other.h;
	}
	
	public String toString(){
		String str = "{";
		for(Operation_GED op : this.OperationPath){
			str += op.toString() + ", ";
		}
		str = str.substring(0, str.length()-2) + "} : " + this.f;;
		return str;
	}
	
}
