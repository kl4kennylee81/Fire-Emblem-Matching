import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class stats {
	public ArrayList<Float> growthRate; 
	public ArrayList<Float> modifier;
	
	public stats () {
		growthRate = new ArrayList<Float>();
		modifier = new ArrayList<Float>();
	}
	
	public stats (float i) {
		List<Float> tempList = Arrays.asList(i,i,i,i,i,i,i,i);
		ArrayList<Float> temp = new ArrayList<Float>();
		temp.addAll(tempList);
		growthRate = temp;
		modifier = new ArrayList<Float>();
	}
	
	public stats (ArrayList gR,ArrayList mod) {
		growthRate = gR;
		modifier = mod;
	}
	
	public void setGrowthRate (String growth) {
		String temp = growth;
		int spaceIndex = 0;
		while (temp.indexOf(" ")!= -1) {
			spaceIndex = temp.indexOf(" ");
			String attr = temp.substring(0,spaceIndex);
			try {
				this.growthRate.add(Float.parseFloat(attr));
			}
			catch (NumberFormatException e) {
			e.printStackTrace();
			}
			catch (NullPointerException  e2) {
			e2.printStackTrace();
			}
			temp = temp.substring(spaceIndex+1);
		}
		this.growthRate.add(Float.parseFloat(temp));
	}
	
	public stats addStats (stats s) { 
		ArrayList<Float> newGR = new ArrayList<Float>();
		ArrayList<Float> newMod = new ArrayList<Float>();
		for (int i= 0;i<=7;i++) {
			if (!(this.growthRate.isEmpty())) {
				float attr = (this.growthRate.get(i))+(s.growthRate.get(i));
				newGR.add(attr);
			}
			
			if (!(this.modifier.isEmpty())) {
				float mod = (this.modifier.get(i))+(s.modifier.get(i));
				newMod.add(mod);
			}
		}
		return new stats(newGR,newMod);
	}
	
	public stats divideStats (float n) { 
		ArrayList<Float> newGR = new ArrayList<Float>();
		ArrayList<Float> newMod = new ArrayList<Float>();
		for (int i= 0;i<=7;i++) {
			if (!(this.growthRate.isEmpty())) {
				float attr = (this.growthRate.get(i))/n;
				newGR.add(attr);
			}
			
			if (!(this.modifier.isEmpty())) {
				float mod = (this.modifier.get(i));
				newMod.add(mod);
			}
		}
		return new stats(newGR,newMod);
	}
	
	public float statScore() {
		float hp = this.growthRate.get(0);
		float atk = this.growthRate.get(1);
		float mag = this.growthRate.get(2);
		float off = 0;
		if (atk>=mag) {
			off = atk+(mag*mag)/(mag+atk);
		}
		else {
			off = mag +(atk*atk)/(atk+mag);
		}
		float sumRest = this.sumStats();
		return hp+off+sumRest;
	}
	
	private float sumStats() {
		ListIterator<Float> temp = growthRate.listIterator(3);
		Float sum = 0f;
		while (temp.hasNext()) {
			sum = ((Float) temp.next())+sum;
		}
		return sum;
	}
	
	public ArrayList getGrowthRate () {
		return this.growthRate;
	}
}
