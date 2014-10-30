package meritorder;

import java.util.*;
import common.*;
/**
 * This class used to represent Merit List
 * of Merit Order Algo
 * 
 * @author Group26:JynXD!
 */
public class MeritList {
	private ArrayList<Candidate> finlist;/**< list of candidates present in merit lists */
	private ArrayList<ArrayList<Candidate>> merit; /**< lists of merit list */
	private HashMap<String,int[]> rank; /**< hashmap required for sorting array lists */
	
	/**
	 * Constructor of class with no parameters <br>
	 * All lists, hashmaps initialised to new()
	 */
	public MeritList(){
		finlist = new ArrayList<Candidate>();
		merit = new ArrayList<ArrayList<Candidate>>();
		for(int i=0;i<8;i++){ merit.add(new ArrayList<Candidate>()) ; }
		rank = new HashMap<String,int[]>();
	}
	
	/**
	 * @return Returns list of candidates present in merit lists
	 */
	public ArrayList<Candidate> retfinlist(){
		return finlist;
	}
	
	/**
	 * @param i the index of merit list to return
	 * @return returns the ith merit list in the list of merit lists
	 */
	public ArrayList<Candidate> retmerit(int i){
		return merit.get(i);
	}
	
	/**
	 * Add candidate to the merit lists 
	 * based on his ranks
	 * @param c Candidate to be added
	 */
	public void addcandidate(Candidate c){
		finlist.add(c);
		for(int i=0;i<8;i++){ //< candidate added to merit lists for which his rank is non-zero
			if(rank.get(c.retID())[i]!=0) merit.get(i).add(c);
		}
	}
	
	/**
	 * Sets the hash map value
	 * @param a HashMap to which the ranklist is equated
	 */
	public void setrank(HashMap<String,int[]> a){ rank=a; }
	
	/**
	 * comparator for comparing candidates of GE merit list
	 */
	class ldige implements Comparator<Candidate>{ //< comparator for comparing two candidates from ge merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[0],rank.get(e2.retID())[0]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of OBC merit list
	 */
	class ldiobc implements Comparator<Candidate>{ //< comparator for comparing two candidates from obc merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[1],rank.get(e2.retID())[1]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of SC merit list
	 */
	class ldisc implements Comparator<Candidate>{ //< comparator for comparing two candidates from sc merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[2],rank.get(e2.retID())[2]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of GE-PD merit list
	 */
	class ldigepd implements Comparator<Candidate>{ //< comparator for comparing two candidates from ge_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[4],rank.get(e2.retID())[4]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of ST merit list
	 */
	class ldist implements Comparator<Candidate>{ //< comparator for comparing two candidates from st merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[3],rank.get(e2.retID())[3]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of OBC-PD merit list
	 */
	class ldiobcpd implements Comparator<Candidate>{ //< comparator for comparing two candidates from obc_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[5],rank.get(e2.retID())[5]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of SC-PD merit list
	 */
	class ldiscpd implements Comparator<Candidate>{ //< comparator for comparing two candidates from sc_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[6],rank.get(e2.retID())[6]);
	    }
	}
	
	/**
	 * comparator for comparing candidates of ST-PD merit list
	 */
	class ldistpd implements Comparator<Candidate>{ //< comparator for comparing two candidates from st_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(rank.get(e1.retID())[7],rank.get(e2.retID())[7]);
	    }
	}
	
	/**
	 * Function to sort all the merit lists as per their comparators
	 */
	public void sortbyrank(){ //< function to sort the merit lists
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
