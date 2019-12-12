import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class AutoPreprocess {

	public static void main(String[] args) throws Exception {

		String Folder_Name = "CAroad";
		String Node_File = "LOW-DEGREE-FILE.txt";
		
		if(args.length == 1) {
			Folder_Name = args[0];
		}
		
		String Original_Graph_File = "/scratch/zhu/graph/" + Folder_Name + "/sortedData.txt";
		String ROOT_FILE_NAME = "/scratch/zhu/graph/" + Folder_Name + "/" + Node_File;
 		String pathK = "/scratch/zhu/TED_Star_Comparison_Test/" + Folder_Name  + "/";

		Graph g = new Graph();
		g.readFromEdeListFile(Original_Graph_File);
		System.out.println("Graph Reading Finished\n");
        
		FileReader fileReader = new FileReader(ROOT_FILE_NAME);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		HashMap<Integer, Integer> nodeK = new HashMap<Integer, Integer>();
		HashMap<Integer, Boolean> flagK = new HashMap<Integer, Boolean>();	
		
		for(int i = 2; i <= 6;  i++){
			nodeK.put(i, 0);
			flagK.put(i, true);
		}
		
		String line;
		int countLines = 1;
		boolean flag = true;
		while((line = bufferedReader.readLine()) != null && flag) {		
			if(line.charAt(0) != '#') {
				int rootID = Integer.parseInt(line);
				if(countLines % 100000 == 0)
					System.out.println(countLines + " smaple nodes have been processed.");
				for(int k = 2; k <= 6; k++) {
					SubGraph sg = new SubGraph(rootID, k, g, 0);
					KAT kat = new KAT(rootID, k, g, 0);
					if(sg.size >= k && kat.size >= k && sg.size <= 12 && kat.size <= 12 && nodeK.get(k) < 20){
						String SubGraph_File_K = pathK + k + "/G-" + nodeK.get(k);	 		
				 		String KAT_File_K = pathK + k + "/T-" + nodeK.get(k);
				 		nodeK.put(k, nodeK.get(k) + 1);
				 		if(nodeK.get(k) == 20)
				 			flagK.put(k, false);
				 		
						FileWriter fstreamout_g = new FileWriter(SubGraph_File_K);
						BufferedWriter out_g = new BufferedWriter(fstreamout_g);
						out_g.write(sg.toEdgeList());
						out_g.close();

						FileWriter fstreamout_t = new FileWriter(KAT_File_K);
						BufferedWriter out_t = new BufferedWriter(fstreamout_t);
						out_t.write(kat.toEdgeList());
						out_t.close();
					}
				}
				flag = false;
				for(int i = 2; i <= 6;  i++){
					flag = flag || flagK.get(i);
				}
				countLines ++;
			}
		}
		bufferedReader.close();
	}
}