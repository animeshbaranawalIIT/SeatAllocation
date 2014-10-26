
import java.util.*;

public class MeritList {
	
	private ArrayList<Candidate> finlist;
	private HashMap<Candidate,Integer>[] meritlist;
	
	public MeritList(){ 
		meritlist = (HashMap<Candidate,Integer>[]) new HashMap[8];  
	}
	
	public void addcandidate(Candidate c,int[] ranklist){
		finlist.add(c);
		for(int i=0;i<8;i++){
			if(ranklist[i]!=0){ meritlist[i].put(c,ranklist[i]); }
		}
	}
	
	public int rank(Candidate c, int cat, Boolean pd){ 
		int x = cat + 4*(pd?1:0);
		return meritlist[x].get(c);
	}
	
	public HashMap<Candidate,Integer>[] retmeritlist(){ return meritlist; }
	public ArrayList<Candidate> retfinlist(){ return finlist; }
	public void resetmerit(HashMap<String,int[]> ranklist){ 
		meritlist[1].clear(); meritlist[4].clear(); meritlist[5].clear();
		meritlist[6].clear(); meritlist[7].clear();
		for(int i=0;i<finlist.size();i++){
			Candidate x = finlist.get(i);
			meritlist[1].put(finlist.get(i),ranklist.get(x.retID())[0]);
			meritlist[4].put(finlist.get(i),ranklist.get(x.retID())[0]);
			meritlist[5].put(finlist.get(i), ranklist.get(x.retID())[0]);
			if(finlist.get(i).retcategory()==2) meritlist[6].put(finlist.get(i),ranklist.get(x.retID())[2]);
			if(finlist.get(i).retcategory()==3) meritlist[7].put(finlist.get(i),ranklist.get(x.retID())[3]);
		}
	}
	
	public void removefin(int i){ finlist.remove(i); } 
}

