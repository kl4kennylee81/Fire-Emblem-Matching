
public class children extends character {

	parent firstParent;
	parent tempParent;
	
	public children(String data) {
		super(data);
		firstParent = null;
		tempParent = null;
	}
	
	public children() {
		super();
		firstParent = null;
		tempParent = null;
	}
	
	public void assignParent (String parent) {
		if (StableMatch.femaleList.containsKey(parent)) {
			firstParent = StableMatch.femaleList.get(parent);
		}
		if (StableMatch.maleList.containsKey(parent)) {
			firstParent = StableMatch.maleList.get(parent);
		}
	}
}
