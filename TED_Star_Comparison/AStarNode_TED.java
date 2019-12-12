import java.util.HashSet;
import java.util.LinkedList;

public class AStarNode_TED implements Comparable<AStarNode_TED>{

	LinkedList<Operation_TED> OperationPath;
	int h;
	int g;
	int f;
	
	public AStarNode_TED(){
		this.OperationPath = new LinkedList<Operation_TED>();
		this.h = 0;
		this.g = 0;
		this.f = 0;
	}
	
	public AStarNode_TED(AStarNode_TED aNode, Operation_TED op){
		this.OperationPath = new LinkedList<Operation_TED>();
		this.OperationPath.addAll(aNode.OperationPath);
		this.OperationPath.add(op);
		this.h = 0;
		this.g = aNode.g + distance(aNode, op);
		this.f = this.h + this.g;
	}
	
	public int distance(AStarNode_TED aNode, Operation_TED op){
		int disagreement = 0;
		if(op.src.id == -1 || op.dst.id == -1 ) {
			disagreement++;
		}
		else {
			Node_TED srcParent = op.src.parent;
			Node_TED dstParent = op.dst.parent;
			for(Operation_TED o: aNode.OperationPath){
				if ( o.src == srcParent ) {
					Node_TED p = o.src;
					Node_TED q = o.dst;
					while ( q.id == -1  ){
						p = p.parent;
						for(Operation_TED oIter: aNode.OperationPath){
							if ( oIter.src == p )
								q = oIter.dst;
						}
					}
					srcParent = p;
				}
				if ( o.dst == dstParent ) {
					Node_TED p = o.dst;
					Node_TED q = o.src;
					while ( q.id == -1  ){
						p = p.parent;
						for(Operation_TED oIter: aNode.OperationPath){
							if ( oIter.dst == p )
								q = oIter.src;
						}
					}
					dstParent = p;
				}
			}
			for(Operation_TED o: aNode.OperationPath){
				if(o.src == srcParent && o.dst != dstParent)
					disagreement += 2;
			}
		}
		return disagreement;
	}
	
	
	public boolean isComplete(HashSet<Node_TED> srcns, HashSet<Node_TED> dstns) {
		for(Operation_TED op : this.OperationPath){
			Node_TED src = op.src;
			Node_TED dst = op.dst;
			srcns.remove(src);
			dstns.remove(dst);
		}
		return srcns.isEmpty() && dstns.isEmpty();
	}
	
	public int size(){
		return this.OperationPath.size();
	}
	
	@Override
	public int compareTo(AStarNode_TED other) {
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
		for(Operation_TED op : this.OperationPath){
			str += op.toString() + ", ";
		}
		str = str.substring(0, str.length()-2) + "} : " + this.f;;
		return str;
	}
	
}