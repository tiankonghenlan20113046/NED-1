import java.util.ArrayList;
import java.util.PriorityQueue;

public class INS {

	int k;
	Tree_INS tx;
	Tree_INS ty;
	int[] maxDegree;
	int[] padding;
	int[] matching;
	double executeTime;
	
	public INS(Tree_INS input_tx, Tree_INS input_ty){
		if(input_tx.k <= input_ty.k)
			this.k = input_tx.k;
		else
			this.k = input_ty.k;
		this.tx = input_tx;
		this.ty = input_ty;
		this.maxDegree = new int[this.k+1];
		this.padding = new int[this.k+2];
		this.matching = new int[this.k+1];
		for(int i = 0; i <= this.k; i++){
			this.maxDegree[i] = 0;
			this.padding[i] = 0;
			this.matching[i] = 0;
		}
		this.padding[k+1] = 0;
		for(int i = 0; i <= this.k; i++)
			this.maxDegree[i] = Math.max(input_tx.maxDegree[i], input_ty.maxDegree[i]);
		this.executeTime = 0.0;
	}
	
	public int distance(){
		long startTimer = System.nanoTime();
		
		this.padding[k] = paddingCost(tx.AT.get(k), ty.AT.get(k));
		int distance = padding[k];
		for(int i = this.k - 1; i >= 0; i--){
			 this.padding[i] = paddingCost(tx.AT.get(i), ty.AT.get(i));
			 if(tx.AT.get(i).size() != 0 && ty.AT.get(i).size() != 0)
				 this.matching[i] = matchingCost(i, tx.AT.get(i), ty.AT.get(i), maxDegree[i]);
			 else
				 this.matching[i] = 0;			
			 distance += this.padding[i] + this.matching[i];
		}
		long endTimer = System.nanoTime();
		this.executeTime = (endTimer - startTimer) / 1000000.0;
		return distance;
	}
	
	public int paddingCost(ArrayList<Node_INS> x, ArrayList<Node_INS> y){
		if(x.size() <= y.size())
			return y.size() - x.size();
		else
			return x.size() - y.size();
	}
	
	public int matchingCost(int i, ArrayList<Node_INS> x, ArrayList<Node_INS> y, int length){
		
		canonization(x, y, length);
		int[][] bGraph = constructBipartiteGraph(x, y);
		HungarianAlgorithm h = new HungarianAlgorithm(bGraph);
		int[] results = h.execute();
		int matching = h.cost(results, bGraph);
		reCanonization(x, y, results);
		return (matching - this.padding[i+1]) / 2;
	}
	
	public void canonization(ArrayList<Node_INS> x, ArrayList<Node_INS> y, int length){
		PriorityQueue<Node_INS> cQueue = new PriorityQueue<Node_INS>();
		for(int i = 0; i < x.size(); i++){			
			x.get(i).cLabel.materialize(x.get(i), length);
			cQueue.add(x.get(i));
		}
		for(int i = 0; i < y.size(); i++){			
			y.get(i).cLabel.materialize(y.get(i), length);
			cQueue.add(y.get(i));
		}
		int newLabel = 0;
		Node_INS previous = new Node_INS(-1);
		previous.cLabel.materialize(previous, length);
		while(!cQueue.isEmpty()){
			Node_INS current = cQueue.poll();
			if(current.cLabel.equals(previous.cLabel)){
				current.cLabel.setLabel(newLabel);
			}
			else{
				previous = current;
				newLabel++;
				current.cLabel.setLabel(newLabel);
			}
		}
	}

	public int[][] constructBipartiteGraph(ArrayList<Node_INS> x, ArrayList<Node_INS> y){
		int size = 0;
		if(x.size() >= y.size())
			size = x.size();
		else
			size = y.size();
		int[][] matrix = new int[size][size];
		
		for(int j = 0; j < x.size(); j++){
			for(int k = 0; k < y.size(); k++){
				if(x.get(j).children.length == 0)
					matrix[j][k] = y.get(k).children.length;
				else if (y.get(k).children.length == 0)
					matrix[j][k] = x.get(j).children.length;
				else{				
					matrix[j][k] = x.get(j).cLabel.diffTo(y.get(k).cLabel);
				}
			}
		}
		if(x.size() > y.size()){
			for(int j = 0; j < x.size(); j++){
				for(int k = y.size(); k < x.size(); k++)
					matrix[j][k] = x.get(j).children.length;
			}
		}
		else if(x.size() < y.size()){
			for(int j = x.size(); j < y.size(); j++){
				for(int k = 0; k < y.size(); k++)
					matrix[j][k] = y.get(k).children.length;
			}
		}
		return matrix;
	}
	
	public void reCanonization(ArrayList<Node_INS> x, ArrayList<Node_INS> y, int[] results){
		if(x.size() >= y.size()){
			for(int j = 0; j < x.size(); j++) {
				if(results[j] < y.size()){
					y.get(results[j]).cLabel.setLabel(x.get(j).cLabel.label);
				}
			}
		}
		else{
			for(int j = 0; j < x.size(); j++) {
				x.get(j).cLabel.setLabel(y.get(results[j]).cLabel.label);
			}
		}
	}
	
}