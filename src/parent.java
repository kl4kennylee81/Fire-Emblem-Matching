import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class parent extends character {

	public children childrens;
	public String childName;
	
	public parent(String data,children child) {
		super(data);
		childrens = child;
		childName = child.name;
	}
	
	public parent(String data) {
		super(data);
		childrens = null;
		childName = "";
	}

	public parent() {
		super();
		childrens = null;
		childName = "";
	}
	public void assignChild (String child) {
		if (StableMatch.childList.containsKey((String) child)) {
			childrens = StableMatch.childList.get(child);
			childName = childrens.name;
		}
	}
	
	public void setMatchRank (List<parent> charList) {
		HashMap<String,Float> valueList = new HashMap<String,Float>();
		children child1 = childrens;
		parent current = null;
		float value = 0;
		Iterator<parent> i = charList.iterator();
		while (i.hasNext()) {
			current = (parent) i.next();
			if (child1 != null) {
				value=statChild(this,current,child1);
			System.out.println((this.name)+" "+ (current.name));
			System.out.println(value);
				if (current.childrens!=null) {
					children child2 = current.childrens;
					value =(value/2)+(statChild(this,current,child2)/2);
				}
			}
			else {
				if (current.childrens!=null) {
					value = statChild(this,current,current.childrens);
					System.out.println((this.name)+" "+ (current.name));
					System.out.println(value);
				}
			}
			valueList.put(current.name, value);
			value = 0f;	
		}
		ArrayList<String> match= new ArrayList<String>(15);
		Iterator<parent> i2 = charList.iterator();
		while (i2.hasNext()) {
			parent p1 = (parent) i2.next();
			Iterator<String> iterMatch = match.iterator();
			String p2 = "";
			if (iterMatch.hasNext()) {
				p2 = (String) iterMatch.next();
			}
			int count = 0;
			while((p1.compareParents(p2,valueList)==-1)&&iterMatch.hasNext()) {
				count = count+1;
				p2 = (String) iterMatch.next();
			}
			match.add(count,p1.name);
		}
		this.matchRank = match;
	}
	
	public int compareParents(String p2, HashMap<String,Float> valueList) {
		float v1=0;
		float v2=0;
		if (valueList.containsKey(this.name)&&valueList.containsKey(p2)) {
			v1 = valueList.get(this.name);
			v2 = valueList.get(p2);
		}
		if (v1==v2) {
			return 0;
		}
		if (v1>v2) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	public float statChild (parent p1,parent p2,children c) {
		stats both = (p1.stat.addStats(p2.stat)).divideStats(3f);
		stats child = c.stat.addStats(both);
		return child.statScore();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
