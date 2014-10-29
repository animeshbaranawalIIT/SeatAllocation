package galeshapley;
import java.util.*;
import common.*;
/** 
 * Class for Implementing MeritList
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
	 * reset merit list ( for the case of dereservation ) <br>
	 * @param ranklist entire hashmap of ranklist of all candidates
	 * candidates with GE rank added to GE,OBC,OBC-PD,GE-PD meritlist <br>
	 * candidates with GE rank of category SC rank also appended to SC,SC-PD (but with SC rank) <br>
	 * candidates with GE rank of category ST rank also appended to ST,ST-PD (but with ST rank) <br>
	 * candidates of SC category with no GE but SC rank appended to SC,SC-PD <br>
	 * candidates of ST category with no GE but ST rank appended to ST,ST-PD <br>
	 * SC/ST/SC-PD/ST-PD candidates with GE rank added to all merit lists
	 *  
	 */
	public void resetmerit(HashMap<String,int[]> ranklist){ 
		/// clear all the merit lists
		meritlist.get(0).clear(); meritlist.get(2).clear(); meritlist.get(3).clear();
		meritlist.get(1).clear(); meritlist.get(4).clear(); meritlist.get(5).clear();
		meritlist.get(6).clear(); meritlist.get(7).clear();
		for(int i=0;i<finlist.size();i++){
			Candidate x = finlist.get(i);
			/// if candidate has non zero general rank
			/// gets appended to GE,OBC,GE-PD,OBC-PD
			if(ranklist.get(x.retID())[0]!=0){
				meritlist.get(0).put(finlist.get(i),ranklist.get(x.retID())[0]);
				meritlist.get(1).put(finlist.get(i),ranklist.get(x.retID())[0]);
				meritlist.get(4).put(finlist.get(i),ranklist.get(x.retID())[0]);
				meritlist.get(5).put(finlist.get(i), ranklist.get(x.retID())[0]);
				// if candidate of category SC
				if(finlist.get(i).retcategory()==2){
					meritlist.get(2).put(finlist.get(i),ranklist.get(x.retID())[2]);
					meritlist.get(6).put(finlist.get(i),ranklist.get(x.retID())[2]);
				}
				// if candidate of category ST
				if(finlist.get(i).retcategory()==3){
					meritlist.get(3).put(finlist.get(i),ranklist.get(x.retID())[3]);
					meritlist.get(7).put(finlist.get(i),ranklist.get(x.retID())[3]);
				}
			}
			
			/// if candidate has zero GE but non zero category(SC/ST) rank
			else if(ranklist.get(x.retID())[2]!=0){ 
				if(finlist.get(i).retcategory()==2){
					meritlist.get(2).put(finlist.get(i),ranklist.get(x.retID())[2]);
					meritlist.get(6).put(finlist.get(i),ranklist.get(x.retID())[2]);
				}
			}
			else if(ranklist.get(x.retID())[3]!=0){
				if(finlist.get(i).retcategory()==3){
					meritlist.get(3).put(finlist.get(i),ranklist.get(x.retID())[3]);
					meritlist.get(7).put(finlist.get(i),ranklist.get(x.retID())[3]);
				}
			}
		}
	}
	
	/** 
	 * remove a candidate from the array list of candidates
	 * @param c Candidate to be removed from list of candidates in merit list
	 */
	public void removefin(Candidate c){ finlist.remove(c); } 
}

