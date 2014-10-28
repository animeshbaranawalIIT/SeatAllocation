package algo1;
import java.util.*;
/** Class for a Virtual Programme */
public class VirtualProgramme{
	private String course; /**< course code of a program */
	private int category; /**< category of a program */
	private Boolean PDstatus; /**< pdstatus of a program */
	private int Quota; /**< quota of the program */
	private int Quotaleft; /**< quotaleft after phase1 i.e. quota left for dereservation */
	private int quotaextrank; /**< deciding rank for extending quota in program */
	private ArrayList <Candidate> Waitlisted; /**< storing waitlisted candidates */
	private ArrayList <Candidate> Wphase2; /**< storing waitlisted candidates when dereservation taking place */
	private ArrayList <Candidate> Applications; /**< storing applications received */
	private HashMap<Candidate,Integer> merit; /**< meritlist of a program */
	/**
	 * Constructor takes only code, category, pd status and quota 
	 * as the parameters.
	 * All arraylists initialised to new()
	 * @param code Course-code for Virtual Program
	 * @param Cat Category of Virtual Program
	 * @param pd PD status of Virtual Program
	 * @param q Quota of the Virtual Program 
	 */
	public VirtualProgramme(String code, int Cat, Boolean pd, int q){
		course = code;
		category = Cat;  PDstatus = pd;
		Quota = q;
		Waitlisted = new ArrayList <Candidate>();
		Applications = new ArrayList <Candidate>();
		merit = new HashMap<Candidate,Integer>();
	}
	
	/** @param a Set quota extenstion deciding rank to int a*/ 
	public void setquotaextrank(int a){ quotaextrank=a; } 
	
	/** @return returns course code */
	public String retcourse(){ return course; }
	
	/** @return returns category code */
	public int retCategory(){return category;} 
	
	/** @return returns pd status code */
	public Boolean retPdstatus(){return PDstatus;} 
	
	/** @return returns array of waitlisted students */
	public ArrayList <Candidate> retWaitlisted(){return Waitlisted;} 
	
	/** @return returns array of applications */
	public ArrayList <Candidate> retApplications(){return Applications;}
	
	/** @return returns quota of program */
	public int retquota(){return Quota;} 
	
	/** @return returns meritlist of program */
	public HashMap<Candidate,Integer> retmerit(){ return merit; } 
	
	/**
	 * set merit list of the program given the entire list of meritlists
	 * based on category of the program
	 * @param meritlist Arraylist of meritlists
	 */
	public void setmerit(MeritList meritlist){
		int x = category + 4*(PDstatus?1:0);
		merit = meritlist.retmeritlist().get(x);
	}
	
	/** add application of a candidate 
	 * @param c Candidate which applies
	 */
	public void apply(Candidate c){ 
		Applications.add(c); }
	
	
	/**
	 * filters the applicants
	 * Adds all the applications to waitlisted
	 * Sorts the waitlisted as per their rank in merit list
	 * Takes the top applications
	 * @return Returns the rejected list of candidates
	 */
	public ArrayList <Candidate> filter(){
		ArrayList <Candidate> Rejected = new ArrayList<Candidate>(); /**< Arraylist for Rejected candidates */
		for(int i=0; i<Applications.size();i++){ 
			Waitlisted.add(Applications.get(i));/// Add the applications to waitlisted 
		}
		Applications.clear(); /// clear the applications list
		/**
		 * Sorts the waitlisted as per the rank in merit
		 */
		Collections.sort(Waitlisted,new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2){
					int i,j;
					i = merit.get(c1);
					j = merit.get(c2);
				   return Integer.compare(i,j);
			}
		});
		/**
		 * Taking the top applications from waitlisted
		 * and removing the other applications
		 */
		for(int i=0;i<Waitlisted.size();i++){
			/// quotaextrank required to determine when to increase the quota
			/// equal to the rank of the last candidate eligible for the seats
			if(i < Quota){
				Waitlisted.get(i).setwaitlisted(Waitlisted.get(i).retcurrent().code);
				if(i+1==Quota) quotaextrank = merit.get(Waitlisted.get(i)); 
			}
			/// if the extra incoming students rank equal to quotaextrank
			/// quota extended to accommodate him
			/// else he is rejected
			else{
				if(merit.get(Waitlisted.get(i))==quotaextrank){
					Waitlisted.get(i).setwaitlisted(Waitlisted.get(i).retcurrent().code);
				}
				else{
					Waitlisted.get(i).setwaitlisted("-1");
					Waitlisted.get(i).setcurrent(Waitlisted.get(i).findnext()); /// find the next program which to apply
					Rejected.add(Waitlisted.get(i)); /// add candidate to rejected category
				}
			}
		}
		for(int i=0;i<Rejected.size();i++){ Waitlisted.remove(Rejected.get(i)); } // Remove rejected students from waitlisted list
		return Rejected;
	}
	
	/** preparation of program for phase 2 */
	public void prepphase2(){ 
		Quotaleft = Quota - Waitlisted.size(); // left quota for dereservation
		Wphase2 = new ArrayList<Candidate>(); // initialise new waitlist array for candidates of phase2
		quotaextrank=0; // reset the quota extending rank
	}
	
	/**
	 * filters the applicants for phase2
	 * Adds all the applications to wphase2
	 * Sorts the wphase2 as per their rank in modified meritlist
	 * Takes the top applications
	 * @return Returns the rejected list of candidates
	 */
	public ArrayList <Candidate> filter2(){
		ArrayList <Candidate> Rejected = new ArrayList<Candidate>(); /**< Arraylist for Rejected candidates */
		/// all this takes place only if seats left for dereservation
		if(Quotaleft>0){
			for(int i=0; i<Applications.size();i++){ 
				Wphase2.add(Applications.get(i)); /// Add the applications to waitlisted  
			}
			Applications.clear(); /// clear the applications list
			/**
			 * Sorts the waitlisted as per the rank in modified merit
			 */
			Collections.sort(Wphase2,new Comparator<Candidate>() {
				public int compare(Candidate c1, Candidate c2){
						int i,j;
						i = merit.get(c1);
						j = merit.get(c2);
						return Integer.compare(i,j);
				}
			});
			/**
			 * Taking the top applications from waitlisted
			 * and removing the other applications
			 */
			for(int i=0;i<Wphase2.size();i++){
				/// quotaextrank required to determine when to increase the quota
				/// equal to the rank of the last candidate eligible for the seats
				if(i < Quota){
					Wphase2.get(i).setwaitlisted(Wphase2.get(i).retcurrent().code);
					if(i+1==Quota) quotaextrank = merit.get(Wphase2.get(i)); 
				}
				/// if the extra incoming students rank equal to quotaextrank
				/// quota extended to accommodate him
				/// else he is rejected
				else{
					if(merit.get(Wphase2.get(i))==quotaextrank){
						Wphase2.get(i).setwaitlisted(Wphase2.get(i).retcurrent().code);
					}
					else{
						Wphase2.get(i).setwaitlisted("-1"); /// find the next program which to apply
						Wphase2.get(i).setcurrent(Wphase2.get(i).findnext()); /// add candidate to rejected category
						Rejected.add(Wphase2.get(i));
					}
				}
			}
			for(int i=0;i<Rejected.size();i++){ Wphase2.remove(Rejected.get(i)); }// Remove rejected students from wphase2 list
			return Rejected;
		}
		
		/// if no seats left for dereservation all applications are rejected
		else{ 
			for(int i=0;i<Applications.size();i++){
				Applications.get(i).setcurrent(Applications.get(i).findnext());
				Rejected.add(Applications.get(i));
			}
			Applications.clear();
			return Rejected;
		}
	}
	
}
