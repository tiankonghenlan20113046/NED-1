import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class AutoCompare {

	public static void main(String args[]) throws IOException{
		
		String path = "/scratch/zhu/TED_Star_Comparison_Test/";
		String srcFolder = "CAroad/";
		String dstFolder = "PAroad/";
		
		int k = 2;
		int testSize = 20;

		if(args.length == 1) {
			k = Integer.parseInt(args[0]);
		}
		
		String srcGED, dstGED, srcTED, dstTED;
		
		String srcGEDPath = path + srcFolder + "/" + k + "/G-";
		String srcTEDPath = path + srcFolder + "/" + k + "/T-";
		String dstGEDPath = path + dstFolder + "/" + k + "/G-";		
		String dstTEDPath = path + dstFolder + "/" + k + "/T-";
		
		String LOG_FILE_NAME = path + k + "-results.txt";
		FileWriter fstreamout_log = new FileWriter(LOG_FILE_NAME);
		BufferedWriter out_log = new BufferedWriter(fstreamout_log);
		out_log.write("K" + "\t" + "GED" + "\t" + "TED" + "\t" + "TED*" + "\n");
			
		for(int i = 0; i < testSize; i++) {
			for(int j = 0; j < testSize; j++) {
				
				srcGED = srcGEDPath + i;
				srcTED = srcTEDPath + i;
				
				dstGED = dstGEDPath + j;
				dstTED = dstTEDPath + j;
				
				Graph_GED gsrc = new Graph_GED(srcGED);
				Graph_GED gdst = new Graph_GED(dstGED);
			
				GED ged = new GED(gsrc, gdst);
				int ged_distnace = ged.distance;
				
				Tree_TED tsrc = new Tree_TED(srcTED);
				Tree_TED tdst = new Tree_TED(dstTED);
				TED ted = new TED(tsrc, tdst);
				int ted_distnace = ted.distance;
				
				Tree_INS katx = new Tree_INS(srcTED);
				Tree_INS katy = new Tree_INS(dstTED);
				INS ins = new INS(katx, katy);
				int ins_distance = ins.distance();
				
				if(ged_distnace != -1 && ted_distnace != -1) {
					out_log.write(k + "\t" + ged_distnace + "\t" + ted_distnace + "\t" + ins_distance + "\n");
					out_log.flush();
				}
			}
		}
		out_log.close();
	}
}
