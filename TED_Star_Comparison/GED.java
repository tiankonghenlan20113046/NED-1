import java.util.HashSet;
import java.util.PriorityQueue;

public class GED {
	
	int gsrc_size;
	int gdst_size;
	int iterations;
	double executeTime;
	int distance;
	AStarNode_GED finalPath;

	public GED(Graph_GED gsrc, Graph_GED gdst){
		
		this.gsrc_size = gsrc.size();
		this.gdst_size = gdst.size();
		this.iterations = 0;
		this.executeTime = 0.0;
		this.distance = 0;
		this.finalPath = new AStarNode_GED();
		
		long startTimer = System.nanoTime();
		
		Node_GED emptyNode = new Node_GED(-1);
		AStarNode_GED rootAStarNode = new AStarNode_GED(new AStarNode_GED(), new Operation_GED(gsrc.root, gdst.root));
		PriorityQueue<AStarNode_GED> OPEN = new PriorityQueue<AStarNode_GED>();				
		for(int i : gdst.nodeMap.keySet()){
			Node_GED gdstNode = gdst.nodeMap.get(i);
			if(gdstNode != gdst.root)
				OPEN.add(new AStarNode_GED(rootAStarNode, new Operation_GED(gsrc.nodeMap.get(gsrc.root.id+1), gdstNode)));
		}
		OPEN.add(new AStarNode_GED(rootAStarNode, new Operation_GED(gsrc.nodeMap.get(gsrc.root.id+1), emptyNode)));
		
		while(!OPEN.isEmpty()){
			AStarNode_GED aNode = OPEN.poll();
			this.iterations++;
			if( this.iterations > 999999 ) {
				this.distance = -1;
				this.finalPath = null;
				this.executeTime = -1;
				break;
			}
			if(aNode.isComplete(gsrc.getNodeSet(), gdst.getNodeSet())){
				this.distance = aNode.f;
				this.finalPath = aNode;
				long endTimer = System.nanoTime();
				this.executeTime = (endTimer - startTimer) / 1000000.0;
				break;
			}
			HashSet<Node_GED> ns = new HashSet<Node_GED>();
			ns.addAll(gdst.getNodeSet());
			for(Operation_GED op : aNode.OperationPath){
				ns.remove(op.dst);
			}
			int k = aNode.size();
			if (k < gsrc.size()){
				for(Node_GED n : ns){
					OPEN.add(new AStarNode_GED(aNode, new Operation_GED(gsrc.nodeMap.get(gsrc.root.id+k), n)));
				}
				OPEN.add(new AStarNode_GED(aNode, new Operation_GED(gsrc.nodeMap.get(gsrc.root.id+k), emptyNode)));
			}
			else {
				for(Node_GED n : ns){
					OPEN.add(new AStarNode_GED(aNode, new Operation_GED(emptyNode, n)));
				}
			}
		}
	}
}
