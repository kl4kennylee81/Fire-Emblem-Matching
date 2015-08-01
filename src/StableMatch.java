import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class StableMatch {

	public static HashMap<String, children> childList = new HashMap<String,children>();
	public static HashMap<String, parent> maleList = new HashMap<String,parent>();;
	public static HashMap<String, parent> femaleList = new HashMap<String,parent>();;
	
	public StableMatch (String http) {
		Response response;
		try {
			response = Jsoup.connect(http)
			           .ignoreContentType(true)
			           .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
			           .referrer("http://www.google.com")   
			           .timeout(12000) 
			           .followRedirects(true)
			           .execute();		
			Document doc = response.parse();
			Element body = doc.body();
			Elements tables = body.getElementsByTag("table");
			Element gRTable= tables.get(3);
			Elements character = gRTable.getElementsByTag("tr");
			ListIterator<Element> charIter  = character.listIterator();
			charIter.next();
			while (charIter.hasNext()) {
				String charStats = (charIter.next()).text();
					parent temp = new parent(charStats);
					if (temp.name != "") {
						if (temp.gender == "female") {
							femaleList.put(temp.name,temp);
						}
						if (temp.gender == "male") {
							maleList.put(temp.name,temp);
						}
					}		
			}
			femaleList.put("N/A",new parent());
			
			Element gRChildTable= tables.get(4);
			Elements characterChild = gRChildTable.getElementsByTag("tr");
			ListIterator<Element> childIter  = characterChild.listIterator();
			childIter.next();
			while (childIter.hasNext()) {
				String charStats = (childIter.next()).text();
				children temp = new children(charStats);
				if (temp.name != "") {
					if (temp.gender == "child") {
						childList.put(temp.name,temp);
					}
				}		
			}				
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void matchChildren () {
		BufferedReader buffread = null;
		try {
			buffread = new BufferedReader(new FileReader("children.txt"));
			String str = buffread.readLine();
			while (str!=null) {
				int index = str.indexOf(":");
				String parent = str.substring(0,index);
				String child = str.substring(index+1);
				if (femaleList.containsKey(parent)) {
					childList.get(child).assignParent(parent);
					femaleList.get(parent).assignChild(child);
				}
				if (maleList.containsKey(parent)) {
					childList.get(child).assignParent(parent);
					maleList.get(parent).assignChild(child);
				}
				str = buffread.readLine();
			}
			buffread.close();
		}
		catch (FileNotFoundException e){
		    System.out.println(e);
		}
		catch (IOException e){
		    System.out.println(e);
		}
		
	}
	
	public void createMatchRank () {
		BufferedReader buffread = null;
		try {
			buffread = new BufferedReader(new FileReader("Matching.txt"));
			String str = buffread.readLine();
			while (str!=null) {
				ArrayList<parent> tempList = parseFile(str);
				int index = str.indexOf(":");
				String name = str.substring(0,index);
				if (femaleList.containsKey(name)) {
					femaleList.get(name).setMatchRank(tempList);
				}
				if (maleList.containsKey(name)) {
					maleList.get(name).setMatchRank(tempList);
				}
				str = buffread.readLine();
			}
			buffread.close();
		}
		catch (FileNotFoundException e){
		    System.out.println(e);
		}
		catch (IOException e){
		    System.out.println(e);
		}		
		
	}
	
	public ArrayList<parent> parseFile (String line) {
		int semiColonIndex = line.indexOf(":");
		int spaceIndex = line.indexOf(" ");
		String n = line.substring(0,semiColonIndex);
		ArrayList<parent> temp = new ArrayList<parent>();
		boolean isMale = maleList.containsKey(n);
		if (spaceIndex==-1 ) {
			if (line.substring(semiColonIndex+1).equals((String)"All")) {
				if(isMale) {
					temp.addAll(femaleList.values());
					return temp;
				}
				if(!isMale) {
					temp.addAll(maleList.values());
					return temp;
				}
			}
			if (line.indexOf("-")!=-1) {
				String x = line.substring(line.indexOf("-")+1);
				int position = Integer.parseInt(x);
				if(isMale) {
					String deleteF = character.females.get(position-1);
					temp.addAll(femaleList.values());
					removeList(temp,deleteF);
					return temp;
				}
				if(!isMale) {
					String deleteM = character.males.get(position-1);
					temp.addAll(maleList.values());
					removeList(temp,deleteM);
					return temp;
				}
			}
		}
		else {
			temp = createList(line.substring(semiColonIndex+1),isMale);
		}
		return temp;
	}
	
	public static ArrayList<parent> createList (String s,Boolean gender) {
		ArrayList<String> names = new ArrayList<String>();
		String num = "";
		for (int i = 0;i<s.length();i++) {
			if (s.charAt(i)==' ') {
				int position = Integer.parseInt(num);
				if (gender==true) {
					names.add(character.females.get(position-1));
				}
				if (gender==false) {
					names.add(character.males.get(position-1));
				}
				num = "";
			}
			else {
				if (s.indexOf(" ",i)==-1) {
					int position = Integer.parseInt(s.substring(i));
					if (gender==true) {
						names.add(character.females.get(position-1));
					}
					if (gender==false) {
						names.add(character.males.get(position-1));
					}
					break;
				}
				num = num + s.charAt(i);
			}

		}
		ArrayList<parent> parentList = new ArrayList<parent>();
		Iterator<String> nameIter = names.iterator();
		while (nameIter.hasNext()) {
			String cur = nameIter.next();
			if (character.females.contains(cur)) {
				parentList.add(femaleList.get(cur));
			}
			if (character.males.contains(cur)) {
				parentList.add(maleList.get(cur));
			}
		}
		return parentList;
		
	}
	public static ArrayList<parent> removeList (ArrayList<parent> pList,String n) {
		Iterator pIter = pList.iterator();
		int count = 0;
		while (pIter.hasNext()) {
			parent current = (parent) pIter.next();
			if (current.name.equals(n)) {
				pList.remove(count);
				return pList;
			}
			count = count+1;
		}
		return pList;	
		}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		StableMatch sm = new StableMatch("https://old.serenesforest.net/fe13/char_growth.html");
		sm.matchChildren();
		/**Iterator<children> y = sm.childList.values().iterator();
		/**while (y.hasNext()) {
			children z = y.next();
			System.out.println((z.firstParent.name)+" "+(z.name));
		}**/
		/**ArrayList<parent> p = sm.parseFile("Lon'qu:-5");
		Iterator<parent> piter = p.iterator();
		while (piter.hasNext()) {
			parent z = piter.next();
			System.out.println(z.name);**/
		sm.createMatchRank();
		}
	}

