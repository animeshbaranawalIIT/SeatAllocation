
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
}

