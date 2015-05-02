package common;
import java.util.*;
import galeshapley.*;
/**
 * This class is required to fill the seats of DS candidates
 * 
 * @author Group26:JynXD!
 */
public class Institute {
	private int seats; /**< seats in the institute by default = 2 */
	private int quotaextrank; /**< quota extending rank for Institute */
	private ArrayList<Candidate> applicants; /**< arraylist of applications */
	private ArrayList<Candidate> waitlisted; /**< arraylist of waitlisted DS students */
	private HashMap<String,Integer> gerank; /**< Hashmap storing general rank of DS students */
	/**
	 * Constructor takes no arguments
	 * All arraylists initialised to new().
	 */
	public Institute(){
		seats=2;
		applicants = new ArrayList<Candidate>();
		gerank = new HashMap<String,Integer>();
		waitlisted = new ArrayList<Candidate>();
	}
	
	/** @return return number of seats */
	public int retseats(){ return seats; }
	
	/** decrease number of available seats by 1 */
	public void decrease(){ seats--; }
	
	/** 
	 * Adds application of student <br>
	 * t = general rank of applied student. <br> 
	 * This is added to the hashmap of institute <br> 
	 * Applicable for Gale Shapley algo
	 * @param c DS Candidate to be added
	 * @param t General rank of DS candidate
	 */
	public void apply(Candidate c, int t){ 
		applicants.add(c);
		if(gerank.get(c.retID())==null) gerank.put(c.retID(),t); 
	}
	
	/**
	 * filters the applicants <br>
	 * Adds all the applications to waitlisted, 
	 * Sorts the waitlisted as per their GE rank <br>
	 * Takes the top applications <br>
	 * @return Returns the rejected list of candidates
	 */
	public ArrayList <Candidate> filter(){
		ArrayList <Candidate> Rejected = new ArrayList<Candidate>(); /**< Arraylist for Rejected candidates */
		for(int i=0; i<applicants.size();i++){ 
			waitlisted.add(applicants.get(i)); //< Add the applications to waitlisted 
		}
		applicants.clear(); //< clear the applications list
		/**
		 * Sorts the waitlisted as per the GE rank
		 */
		Collections.sort(waitlisted,new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2){
					int i,j;
					i = gerank.get(c1.retID());
					j = gerank.get(c2.retID());
				   return Integer.compare(i,j);
			}
		});
		/**
		 * Taking the top applications from waitlisted
		 * and removing the other applications
		 */
		for(int i=0;i<waitlisted.size();i++){
			//< quotaextrank required to determine when to increase the quota
			//< equal to the rank of the last candidate eligible for the seats
			if(i < seats){
				waitlisted.get(i).setwaitlisted(waitlisted.get(i).retcurrent().code);
				if(i+1==seats) quotaextrank = gerank.get(waitlisted.get(i).retID()); 
			}
			
			//< if the extra incoming students rank equal to quotaextrank
			//< quota extended to accommodate him
			//< else he is rejected
			else{
				if(gerank.get(waitlisted.get(i).retID())==quotaextrank){
					waitlisted.get(i).setwaitlisted(waitlisted.get(i).retcurrent().code);
				}
				else{
					waitlisted.get(i).setwaitlisted("-1");
					waitlisted.get(i).setcurrent(waitlisted.get(i).findnext()); //< find the next program which to apply
					Rejected.add(waitlisted.get(i)); //< add candidate to rejected category
				}
			}
		}
		for(int i=0;i<Rejected.size();i++){ waitlisted.remove(Rejected.get(i)); }//< Remove rejected students from waitlisted list
		return Rejected;
	}

//------------------------------------------------------------------------------------------------------//
	/**
	 * This function distributes seats between DS candidates. <br>
	 * Used for Merit Order Algo
	 * @param x applying candidate
	 * @param rank rank of the candidate used to compare with quotaextrank
	 * @return returns whether student waitlisted for program
	 */
	public Boolean applymerit(Candidate x, int rank){
		if(seats>0){ //< if seats > 0 , student waitlisted
			x.setwaitlisted(x.retcurrent().code);
			seats--;
			if(seats==0){ quotaextrank = rank; }
			return true;
		}
		else{ //< rank compared with quotaextrank for allocationg seat
			if(rank==quotaextrank){ x.setwaitlisted(x.retcurrent().code); return true; }
			else{ x.setcurrent(x.findnextmerit()); return false; }
		}
	}


}
