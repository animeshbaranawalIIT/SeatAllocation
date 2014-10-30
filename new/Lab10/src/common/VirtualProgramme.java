package common;
import java.util.*;
import galeshapley.*;
import meritorder.*;
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
	private int num;
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
	public void setmerit(galeshapley.MeritList meritlist){
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
	
	
	//---------------------------------------------------------------------------------------------//
	
	
	/*public Boolean extender(String cate, int i, Candidate c, int[] rank){                 // function for extending the quota if needed
		//System.out.println("Inside extender" + cate);
		switch(cate){   
			                  
					case "GE" : if(i == rank[0]){System.out.println("by extending quota ge");return true;} break;         // rank of the candidate is checked with the lastadded variable
					case "OBC" : if(i == rank[1]){System.out.println("by extending quota obc");return true;} break;   
					case "SC" : if(i == rank[2]){System.out.println("by extending quota sc");return true;} break;          // if found equal then this candidate is also shortlisted         
					case "ST" : if(i == rank[3]){System.out.println("by extending quota st");return true;} break;         
					case "GE_PD" : if(i == rank[4]){System.out.println("by extending quota gepd");return true;} break;
					case "OBC_PD" : if(i == rank[5]){System.out.println("by extending quota obcpd");return true;} break;
					case "SC_PD" : if(i == rank[6]){System.out.println("by extending quota scpd");return true;} break;	
					case "ST_PD" : if(i == rank[7]){System.out.println("by extending quota stpd");return true;} break;
					
				}
				//System.out.println("returning false");
		return false;

	}

	public int storer(String cate, Candidate c, int[] rank){  // stores the rank of latest candidate in  variable every time a candidate is waitlisted
		int i=0;
		switch(cate){									
					case "GE" : i = rank[0]; break;     	
					case "OBC" : i = rank[1]; break;
					case "SC" : i = rank[2]; break;    // different ranks are stored depending on the category of our programme
					case "ST" : i = rank[3]; break;
					case "GE_PD" : i = rank[4]; break;
					case "OBC_PD" : i = rank[5]; break;
					case "SC_PD" : i = rank[6]; break;
					case "ST_PD" : i = rank[7]; break;
				}
		return i;
	}*/

	public Boolean applyalgo2(Candidate c, int rank){	
		//System.out.println("application of "+ c.retID() + cat + " "  + "in" + course + " " + category); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num < Quota){  // check if quota empty
			/*System.out.println("Accepting the application");
			quotaextrank= storer(category,c,rankc);     // function is called which stores the rank of last added candidate
			//System.out.println("lastadded");
			//System.out.println(lastadded);
			return true;*/
			c.setwaitlisted(course);
			num++;
			if(num==Quota){ quotaextrank = rank; }
			return true;
		}
		else { 
			//if(cat.equals(category) && pd.equals(pdstatus)){ System.out.println(cat+ " " + category);  // when quota is actually full but needs to extend due to same rank
			if(rank==quotaextrank){ c.setwaitlisted(course); return true; }         // function is called to waitlist the candidate by extending the quota if needed
			return false;
		} 
	}


	public Boolean applyalgo2p2(Candidate c, int rank){	//System.out.println("application of "+ c.Id + " " + cat + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		//System.out.println("application of "+ c.Id + cat + " "  + "in" + code + " " + category+ " for dereservation"); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num < Quota){   
			//System.out.println("Accepting the application");
			// increase seats occupied if candidate waitlisted
			//p2lastadded= storer(cat,c);				// function is called which stores the rank of last added candidate
			//System.out.println("p2lastadded");
			//System.out.println(p2lastadded);
			c.setwaitlisted(course);
			num++; 
			if(num==Quota){ quotaextrank = rank; }
			return true; 
		}
		else { 
			//if(cat.equals(cat)){         // when quota is actually full but needs to extend due to same rank
			//if (extender(cat,p2lastadded,c)) return true;				 // function is called to waitlist the candidate by extending the quota if needed
			if(rank==quotaextrank){ c.setwaitlisted(course); return true; }
			return false;
		} 
	}
		
}
