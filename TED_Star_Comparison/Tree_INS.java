import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Tree_INS {
	
	HashMap<Integer, Node_INS> nodeMap;
	Node_INS root;
	int k; // Record number of levels
	HashMap<Integer, ArrayList<Node_INS>> AT; // Record node array per level
	int[] maxDegree; // Record maximal degree of the nodes per level
	
	public Tree_INS(String FILENAME){
		this.nodeMap = new HashMap<Integer, Node_INS>();
		this.k = 0;
		this.AT = new HashMap<Integer, ArrayList<Node_INS>>();
		this.maxDegree = new int[this.k+1];
		try{
			FileReader fileReader = new FileReader(FILENAME);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			this.k = 0;
			int rootID = 0;
			while((line = bufferedReader.readLine()) != null) {
				String[] str, degrees;
				str = line.split("\t");
				if(str[0].equals("k")) {
					this.k = Integer.parseInt(str[1]);
					this.maxDegree = new int[this.k+1];
				}
				else if(str[0].equals("maxDegree")) {
					degrees = str[1].split(",");
					for(int i = 0; i <= k; i++) {
						AT.put(i, new ArrayList<Node_INS>());
						maxDegree[i] = Integer.parseInt(degrees[i]);
					}
				}
				else if(str[0].equals("rootID")) {
					rootID = Integer.parseInt(str[1]);
				}
				else {
					int src = Integer.parseInt(str[0]);
					int dst = Integer.parseInt(str[1]);
					
					if(!nodeMap.containsKey(src)){
						this.nodeMap.put(src, new Node_INS(src));
					}
					if(!nodeMap.containsKey(dst)){
						this.nodeMap.put(dst, new Node_INS(dst, this.nodeMap.get(src)));
					}			
					this.nodeMap.get(src).addChild(this.nodeMap.get(dst));
				}
	        }
			bufferedReader.close();
			this.root = this.nodeMap.get(rootID);
			for(int id : this.nodeMap.keySet()) {
				this.AT.get(this.nodeMap.get(id).level).add(this.nodeMap.get(id));
			}
		}
		catch(FileNotFoundException ex) {
            System.err.println("Unable to open file '" + FILENAME + "'");                
        }
        catch(IOException ex) {
        	ex.printStackTrace();
        }
	}
	
	public int size(){
		return this.nodeMap.keySet().size();
	}
	
	public HashSet<Node_INS> getNodeSet(){
		HashSet<Node_INS> nodeSet = new HashSet<Node_INS>();
		for(int n : this.nodeMap.keySet()){
			nodeSet.add(nodeMap.get(n));
		}
		return nodeSet;
	}
	
	public String toString(){
		String str = "K = " + this.k + "\n";
		for(int i = 0; i <= this.k; i++) {
			str += "Level " + i + ":\t";
			for(int j = 0; j < this.AT.get(i).size(); j++)
				str += this.AT.get(i).get(j).toString() + ",";
			if(this.AT.get(i).size() > 0)
				str = str.substring(0, str.length()-1);
			str += "\n";
		}
		return str.substring(0, str.length()-1);
	}
}