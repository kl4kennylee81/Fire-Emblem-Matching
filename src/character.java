import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class character {
	public String name;
	public ArrayList<String> matchRank;
	public stats stat;
	public String gender;
	
	public static List<String> females = Arrays.asList	("Avatar","Lissa",
			"Sully","Miriel","Sumia","Maribelle","Panne","Cordelia",
			"Nowi","Tharja","Olivia","Cherche","N/A");
	public static List<String> males = Arrays.asList	("Chrom","Frederick",
			"Virion","Stahl","Vaike","Kellam","Donnel","Lon'qu",
			"Ricken","Gaius","Gregor","Libra","Henry");
	
	private static List<String> children = Arrays.asList ("Lucina","Owain","Inigo"
			,"Brady","Kjelle","Cynthia","Severa","Gerome","Morgan","Yarne","Laurent"
			,"Noire","Nah");
	
	public character () {
		name = "N/A";
		matchRank = new ArrayList<String>();
		Float temp = 0.0f;
		stat = new stats(temp);
		gender = "";
	}
	
	public character (String data) {
		int spaceIndex = data.indexOf(" ");
		if (spaceIndex == -1) {
			name = "";
			gender = "";
		}
		else {
			name = data.substring(0,spaceIndex);
			gender = "";
			if (isFemale(name)||isMale(name)||children.contains((String)name)) {
				if (isFemale(name)) {
					gender = "female";
				}
				if (isMale(name)) {
					gender = "male";
				}
				if (children.contains((String)name)) {
					gender = "child";
				}
				stat = new stats();
				stat.setGrowthRate(data.substring(spaceIndex+1));
			}
			else {
				name = "";
			}
			matchRank = new ArrayList<String>();
		}
	}
	
	
	
	public boolean isMale (String n) {
		ListIterator<String> maleIter = males.listIterator();
		boolean truth = false;
		while (maleIter.hasNext()&& !truth) {
			if (maleIter.next().equals(n)) {
				truth = true;
			}
		}
		return truth;
	}

	
	public boolean isFemale (String n) {
		ListIterator<String> femaleIter = females.listIterator();
		boolean truth = false;
		while (femaleIter.hasNext() && !truth) {
			if (femaleIter.next().equals(n)) {
				truth = true;
			}
		}
		return truth;
	}
	
	public static void main(String[] args) {
		ArrayList<String> t = new ArrayList<String>();
		t.addAll(Arrays.asList("hi","hello","herro"));
		t.add(2,"hillo");
		System.out.println(t.get(3));
		

	}

}
