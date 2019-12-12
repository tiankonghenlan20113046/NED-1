import java.util.HashMap;
import java.util.HashSet;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Graph_GED {
	
	HashMap<Integer, Node_GED> nodeMap;
	Node_GED root;
	
	public Graph_GED(){
		this.nodeMap = new HashMap<Integer, Node_GED>();
		this.root = new Node_GED(-1);
	}
	
	public Graph_GED(String FILENAME){
		this.nodeMap = new HashMap<Integer, Node_GED>();
		try{
			FileReader fileReader = new FileReader(FILENAME);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int rootID = 0;
			while((line = bufferedReader.readLine()) != null) {
				String[] str = line.split("\t");
				if(str[0].equals("rootID")) {
					rootID = Integer.parseInt(str[1]);
				}
				else {
					int src = Integer.parseInt(str[0]);
					int dst = Integer.parseInt(str[1]);
					if(!this.nodeMap.containsKey(src)){
						this.nodeMap.put(src, new Node_GED(src));
					}
					if(!this.nodeMap.containsKey(dst)){
						this.nodeMap.put(dst, new Node_GED(dst));
					}
					this.nodeMap.get(src).addNeighbor(this.nodeMap.get(dst));
					this.nodeMap.get(dst).addNeighbor(this.nodeMap.get(src));
				}
	        }
			bufferedReader.close();
			this.root = this.nodeMap.get(rootID);
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
	
	public HashSet<Node_GED> getNodeSet(){
		HashSet<Node_GED> nodeSet = new HashSet<Node_GED>();
		for(int n : this.nodeMap.keySet()){
			nodeSet.add(nodeMap.get(n));
		}
		return nodeSet;
	}
	
}
