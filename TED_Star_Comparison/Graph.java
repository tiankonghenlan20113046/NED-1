import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
	HashMap<Integer, HashSet<Integer>> nodeMap;
	
	public Graph(){ // Graph is stored in the node adjacent set
		this.nodeMap = new HashMap<Integer, HashSet<Integer>>();
	}
	
	public void readFromEdeListFile(String FILE_NAME){
        try{
            FileReader fstreamin = new FileReader(FILE_NAME);
            BufferedReader in = new BufferedReader(fstreamin);
            String strLine;
            while ((strLine = in.readLine()) != null) {
            	if(strLine.charAt(0) != '#'){ // All lines starting with '#' are ignored
                	String[] values = strLine.split("\\s"); // The graph file is an edge list
                	int src = Integer.parseInt(values[0]);
                	int dst = Integer.parseInt(values[1]);
                	if(!this.nodeMap.containsKey(src))
                		this.nodeMap.put(src, new HashSet<Integer>());
                	if(!this.nodeMap.containsKey(dst))
                		this.nodeMap.put(dst, new HashSet<Integer>());
                	this.nodeMap.get(src).add(dst); // Use this line only, if it is a directed graph
                	this.nodeMap.get(dst).add(src); // Use this line plus, if it is a undirected graph
            	}
            }
            in.close();
        }
        catch (Exception e){
            System.err.println("Graph Reading Error: " + e.toString() + "|" + e.getMessage());
        }
	}
	
	public String toString() {
		int numberNodes = 0;
		int numberEdges = 0;
		numberNodes = this.nodeMap.keySet().size();
		for(Integer key:this.nodeMap.keySet()){
			numberEdges += this.nodeMap.get(key).size();
		}
		return "# Nodes:\t" + numberNodes + "\t" + "# Edges:\t" + numberEdges;
	}
}
