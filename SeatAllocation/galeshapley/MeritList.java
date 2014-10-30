package galeshapley;
import java.util.*;
import common.*;
/** 
 * Class for Implementing MeritList
 * 
 * @author Group26:JynXD!
 */
public class MeritList {
	
	private ArrayList<Candidate> finlist; /**< arraylist of candidates in any merit list */
	private ArrayList<HashMap<Candidate,Integer>> meritlist; /**< arraylist of 8 meritlists - GE,OBC,SC,ST,GE-PD,OBC-PD,SC-PD,ST-PD */
	/**
	 * Constructor takes no arguments
	 * Arraylists initialised to new()
	 */
	public MeritList(){
		finlist = new ArrayList<Candidate>();
		meritlist = new ArrayList<HashMap<Candidate,Integer>>();
		for(int i=0;i<8;i++){ meritlist.add(new HashMap<Candidate,Integer>()); }
	}
	
	/** 
	 * adding a candidate to finlist and
	 * meritlist( based on his ranks he can get into any of the merit lists )
	 * @param c Candidate to be added
	 * @param ranklist Ranks of the candidate which will decide which meritlists it will go
	 */ 
	public void addcandidate(Candidate c,int[] ranklist){
		finlist.add(c);
		for(int i=0;i<8;i++){
			if(ranklist[i]!=0){ meritlist.get(i).put(c,ranklist[i]); }
		}
	}
	
	/**
	 * function to return rank of candidate given the category and PDstatus of meritlist
	 * @param c candidate whose rank to be found
	 * @param cat category of merit list in which rank to be found
	 * @param pd pd status of merit list in which rank to be found
	 * @return returns the rank of candidate in merit list
	 */
	public int rank(Candidate c, int cat, Boolean pd){ 
		int x = cat + 4*(pd?1:0);
		return meritlist.get(x).get(c);
	}
	
	/** @return Returns the entire list of merit lists */
	public ArrayList<HashMap<Candidate,Integer>> retmeritlist(){ return meritlist; } 
	
	/** @return Returns the list of candidates in the merit list */
	public ArrayList<Candidate> retfinlist(){ return finlist; }
	
	/** 
	 * remove a candidate from the array list of candidates
	 * @param c Candidate to be removed from list of candidates in merit list
	 */
	public void removefin(Candidate c){ finlist.remove(c); } 
}

