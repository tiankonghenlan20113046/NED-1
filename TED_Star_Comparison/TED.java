import java.util.HashSet;
import java.util.PriorityQueue;

public class TED {
	
	int tsrc_size;
	int tdst_size;
	int iterations;
	double executeTime;
	int distance;
	AStarNode_TED finalPath;
	
	public TED(Tree_TED tsrc, Tree_TED tdst){
		
		this.tsrc_size = tsrc.size();
		this.tdst_size = tdst.size();
		this.iterations = 0;
		this.executeTime = 0.0;
		this.distance = 0;
		this.finalPath = new AStarNode_TED();
		
		long startTimer = System.nanoTime();
		
		Node_TED emptyNode = new Node_TED(-1);	
		AStarNode_TED rootAStarNode = new AStarNode_TED(new AStarNode_TED(), new Operation_TED(tsrc.root, tdst.root));

		PriorityQueue<AStarNode_TED> OPEN = new PriorityQueue<AStarNode_TED>();			
		for(int i : tdst.nodeMap.keySet()){
			Node_TED tdstNode = tdst.nodeMap.get(i);
			if(tdstNode != tdst.root)
				OPEN.add(new AStarNode_TED(rootAStarNode, new Operation_TED(tsrc.nodeMap.get(tsrc.root.id+1), tdstNode)));
		}
		OPEN.add(new AStarNode_TED(rootAStarNode, new Operation_TED(tsrc.nodeMap.get(tsrc.root.id+1), emptyNode)));
		
		while(!OPEN.isEmpty()){
			AStarNode_TED aNode = OPEN.poll();
			this.iterations++;
			if( this.iterations > 999999 ) {
				this.distance = -1;
				this.finalPath = null;
				this.executeTime = -1;
				break;
			}
			if(aNode.isComplete(tsrc.getNodeSet(), tdst.getNodeSet())){
				this.distance = aNode.f;
				this.finalPath = aNode;
				long endTimer = System.nanoTime();
				this.executeTime = (endTimer - startTimer) / 1000000.0;
				break;
			}
			HashSet<Node_TED> ns = new HashSet<Node_TED>();
			ns.addAll(tdst.getNodeSet());
			for(Operation_TED op : aNode.OperationPath){
				ns.remove(op.dst);
			}
			int k = aNode.size();
			if (k < tsrc.size()){
				for(Node_TED n : ns){
					OPEN.add(new AStarNode_TED(aNode, new Operation_TED(tsrc.nodeMap.get(tsrc.root.id+k), n)));
				}
				OPEN.add(new AStarNode_TED(aNode, new Operation_TED(tsrc.nodeMap.get(tsrc.root.id+k), emptyNode)));
			}
			else {
				for(Node_TED n : ns){
					OPEN.add(new AStarNode_TED(aNode, new Operation_TED(emptyNode, n)));
				}
			}
		}
	}
}