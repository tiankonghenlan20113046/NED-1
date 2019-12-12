import java.util.PriorityQueue;

public class CanonicalLabel implements Comparable<CanonicalLabel> {
	
	int label;
	int[] childArray;
	int significantLength;
	
	public CanonicalLabel() {
		this.label = 0;
		this.childArray = new int[0];
		this.significantLength = 0;
	}
	
	public CanonicalLabel(int length) {
		this.label = 0;
		this.childArray = new int[length];
		for(int i = 0; i < length; i++)
			this.childArray[i] = -1;
		this.significantLength = 0;
	}
	
	public void materialize(Node_INS n, int length){
		this.childArray = new int[length];
		PriorityQueue<Integer> cQueue = new PriorityQueue<Integer>();
		for(int i = 0; i < n.children.length; i++){
			cQueue.add(n.children[i].cLabel.label);
		}
		this.significantLength = cQueue.size();
		for(int i = length - 1; i >= cQueue.size(); i--)
			this.childArray[i] = -1;
		for(int i = cQueue.size() - 1; i >= 0; i--){
			this.childArray[i] = cQueue.poll();
		}
	}
	
	public void setLabel(int input_label){
		this.label = input_label;
	}

	@Override
	public int compareTo(CanonicalLabel other) {
		for(int i = 0; i < this.childArray.length; i++) {
			if(this.childArray[i] < other.childArray[i])
				return -1;
			else if(this.childArray[i] > other.childArray[i])
				return 1;
		}
		return 0;
	}
	
	public boolean equals(CanonicalLabel other){
		for(int i = 0; i < this.childArray.length; i++) {
			if(this.childArray[i] != other.childArray[i])
				return false;
		}
		return true;
	}
	
	public int diffTo(CanonicalLabel other) {
		int count = 0;
		int i = 0;
		int j = 0;
		
		while(i < this.childArray.length || j < other.childArray.length) {
			if(i == this.childArray.length)
				return count + other.significantLength - j;
			else if (j == other.childArray.length)
				return count + this.significantLength - i;
			else {
				if(this.childArray[i] == -1 && other.childArray[j] == -1)
					return count;
				else if(this.childArray[i] == -1  && other.childArray[j] != -1)
					return count + other.significantLength - j;
				else if(this.childArray[i] != -1  && other.childArray[j] == -1)
					return count + this.significantLength - i;
				else {
					if(this.childArray[i] > other.childArray[j]){
						count++;
						i++;
					}
					else if(this.childArray[i] < other.childArray[j]){
						count++;
						j++;
					}
					else{
						i++;
						j++;
					}
				}
			}
		}
		return count;
	}
	
	public String toString(){
		String str = this.label + "(";
		for(int i = 0; i < this.childArray.length; i++) {
			str += this.childArray[i] + ",";
		}
		if(this.childArray.length > 0)
			str = str.substring(0, str.length()-1);
		return str + ")";
	}

}