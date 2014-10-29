package meritorder;

import java.util.*;
import common.*;

public class MeritList {
	private ArrayList<Candidate> finlist;
	private ArrayList<ArrayList<Candidate>> merit;
	private HashMap<String,int[]> rank;
	
	public MeritList(){
		finlist = new ArrayList<Candidate>();
		merit = new ArrayList<ArrayList<Candidate>>();
		for(int i=0;i<8;i++){ merit.add(new ArrayList<Candidate>()) ; }
		rank = new HashMap<String,int[]>();
	}
	
	public void addcandidate(Candidate c){
		finlist.add(c);
		for(int i=0;i<8;i++){
			if(rank.get(c.retID())[i]!=0) merit.get(i).add(c);
		}
	}
	
	public void setrank(HashMap<String,int[]> a){ rank=a; }
	
	// Sorting the merit lists
	class ldige implements Comparator<Candidate>{ // comparator for comparing two candidates from ge merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[0],rank.get(e2.retID())[0]);
	    }
	}
	
	class ldiobc implements Comparator<Candidate>{ // comparator for comparing two candidates from obc merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[1],rank.get(e2.retID())[1]);
	    }
	}
	
	class ldisc implements Comparator<Candidate>{ // comparator for comparing two candidates from sc merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[2],rank.get(e2.retID())[2]);
	    }
	}
	
	class ldigepd implements Comparator<Candidate>{ // comparator for comparing two candidates from ge_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[4],rank.get(e2.retID())[4]);
	    }
	}
	
	class ldist implements Comparator<Candidate>{ // comparator for comparing two candidates from st merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[3],rank.get(e2.retID())[3]);
	    }
	}
	
	class ldiobcpd implements Comparator<Candidate>{ // comparator for comparing two candidates from obc_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[5],rank.get(e2.retID())[5]);
	    }
	}
	
	class ldiscpd implements Comparator<Candidate>{ // comparator for comparing two candidates from sc_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[6],rank.get(e2.retID())[6]);
	    }
	}
	
	class ldistpd implements Comparator<Candidate>{ // comparator for comparing two candidates from st_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[7],rank.get(e2.retID())[7]);
	    }
	}
	
	public void sortbyrank(){ // function to sort the merit lists
		Collections.sort(merit.get(0),new ldige());
		Collections.sort(merit.get(1),new ldiobc());
		Collections.sort(merit.get(2),new ldisc());
		Collections.sort(merit.get(3),new ldist());
		Collections.sort(merit.get(4),new ldigepd());
		Collections.sort(merit.get(5),new ldiobcpd());
		Collections.sort(merit.get(6),new ldiscpd());
		Collections.sort(merit.get(7),new ldistpd());
	}
}
