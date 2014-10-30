package common;
import java.util.*;
import galeshapley.*;

/** 
 * 
 * Class for a Virtual Programme 
 * 
 * @author Group26:JynXD!
 */
public class VirtualProgramme{
	private String course; /**< course code of a program */
	private int category; /**< category of a program */
	private Boolean PDstatus; /**< pdstatus of a program */
	private int Quota; /**< quota of the program */
	private int Quotaleft; /**< quotaleft after phase1 i.e. quota left for dereservation */
	private int quotaextrank; /**< deciding rank for extending quota in program */
	private ArrayList <Candidate> Waitlisted1; /**< storing category waitlisted candidates */
	private ArrayList <Candidate> Waitlisted2; /**< storing other waitlisted candidates */
	private ArrayList <Candidate> Applications; /**< storing applications received */
	private HashMap<Candidate,Integer> merit1; /**< category meritlist of a program */
	private HashMap<Candidate,Integer> merit2; /**< extra appended meritlist of a program */
	private int num; /**< number of seats already taken (used by Merit Order Algo ) */
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
		Quota = q; Quotaleft=q;
		Waitlisted1 = new ArrayList <Candidate>();
		Waitlisted2 = new ArrayList <Candidate>();
		Applications = new ArrayList <Candidate>();
		merit1 = new HashMap<Candidate,Integer>();
		merit2 = new HashMap<Candidate,Integer>();
		num=0;
	}
	
	/** @param a Set quota extenstion deciding rank to int a*/ 
	public void setquotaextrank(int a){ quotaextrank=a; } 
	
	/** @return returns course code */
	public String retcourse(){ return course; }
	
	/** @return returns category code */
	public int retCategory(){return category;} 
	
	/** @return returns pd status code */
	public Boolean retPdstatus(){return PDstatus;} 
	
	/** @return returns array of category waitlisted students */
	public ArrayList <Candidate> retWaitlisted1(){return Waitlisted1;} 
	
	/** @return returns array of other waitlisted students */
	public ArrayList <Candidate> retWaitlisted2(){return Waitlisted2;}
	
	/** @return returns array of applications */
	public ArrayList <Candidate> retApplications(){return Applications;}
	
	/** @return returns quota of program */
	public int retquota(){return Quota;} 
	
	/** @return returns leftover quota of program */
	public int retquotaleft(){return Quotaleft;}
	
	/** @return returns  category meritlist of program */
	public HashMap<Candidate,Integer> retmerit1(){ return merit1; } 
	
	/** @return returns appended meritlist of program */
	public HashMap<Candidate,Integer> retmerit2(){ return merit2; }
	
	/**
	 * set both merit lists of the program given the entire list of meritlists
	 * based on category of the program
	 * @param meritlist Arraylist of meritlists
	 */
	public void setmerit(MeritList meritlist){
		int x = category + 4*(PDstatus?1:0);
		merit1 = meritlist.retmeritlist().get(x);
		if(category==1){ merit2 = meritlist.retmeritlist().get(0); }
		if(category==0 && PDstatus==true){ merit2 = meritlist.retmeritlist().get(0); }
		if((category==2 || category==3) && PDstatus==true){ merit2 = meritlist.retmeritlist().get(3); }
	}
	
	/** add application of a candidate 
	 * @param c Candidate which applies
	 */
	public void apply(Candidate c){ 
		Applications.add(c); }
	
	
	/**
	 * filters the applicants <br>
	 * Adds all the applications to waitlisted,
	 * Sorts the waitlisted as per their rank in merit list<br>
	 * Takes the top applications<br>
	 * Used for Gale Shapley Algo
	 * @return Returns the rejected list of candidates
	 */
	public ArrayList <Candidate> filter(){
		ArrayList <Candidate> Rejected1 = new ArrayList<Candidate>(); /**< Arraylist for Rejected candidates */
		if((category==0 || category==2 || category==3) && PDstatus==false){ //< if program of GE/SC/ST then applications are
			for(int i=0; i<Applications.size();i++){                        //< not separated into category and otherwise
					Waitlisted1.add(Applications.get(i)); //< Add the applications to waitlisted 
			}
		}
		else{ //< otherwise
			for(int i=0; i<Applications.size();i++){ 
				if(Applications.get(i).retcategory()==category && Applications.get(i).retPDstatus()==PDstatus){
					Waitlisted1.add(Applications.get(i));//< Add the applications to category waitlist 
				}
				else{ //< add the applications to the otherwise waitlist
					Waitlisted2.add(Applications.get(i));
				}
			}
		}
		Applications.clear(); //< clear the applications list
		/**
		 * Sorts the waitlisted as per the rank in merit
		 */
		Collections.sort(Waitlisted1,new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2){
					int i,j;
					i = merit1.get(c1);
					j = merit1.get(c2);
				   return Integer.compare(i,j);
			}
		});
		
		/**
		 * Sorts the waitlisted as per the rank in merit
		 */
		if(!((category==0 || category==2 || category==3) && PDstatus==false)){
		Collections.sort(Waitlisted2,new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2){
					int i,j;
					i = merit2.get(c1);
					j = merit2.get(c2);
				   return Integer.compare(i,j);
			}
		}); }
		
		/**
		 * Taking the top applications from waitlisted
		 * and removing the other applications
		 */
		
		for(int i=0;i<Waitlisted1.size();i++){
			//< quotaextrank required to determine when to increase the quota
			//< equal to the rank of the last candidate eligible for the seats
			if(i < Quota){
				Waitlisted1.get(i).setwaitlisted(Waitlisted1.get(i).retcurrent().code);
				if(i+1==Quota) quotaextrank = merit1.get(Waitlisted1.get(i)); 
			}
			//< if the extra incoming students rank equal to quotaextrank
			//< quota extended to accommodate him
			//< else he is rejected
			else{
				if(merit1.get(Waitlisted1.get(i))==quotaextrank){
					Waitlisted1.get(i).setwaitlisted(Waitlisted1.get(i).retcurrent().code);
				}
				else{
					Waitlisted1.get(i).setwaitlisted("-1");
					Waitlisted1.get(i).setcurrent(Waitlisted1.get(i).findnext()); //< find the next program which to apply
					Rejected1.add(Waitlisted1.get(i)); //< add candidate to rejected category
				}
			}
		}
		for(int i=0;i<Rejected1.size();i++){ Waitlisted1.remove(Rejected1.get(i)); } //< Remove rejected students from waitlisted list
		
		
		ArrayList<Candidate> Rejected2 = new ArrayList<Candidate>(); //< Reject list for the otherwise waitlist
		Quotaleft = Quota - Waitlisted1.size(); //< waitlist analysed only if seats left after analysing category waitlist
		if(Quotaleft>0){
			
			for(int i=0;i<Waitlisted2.size();i++){
				//< quotaextrank required to determine when to increase the quota
				//< equal to the rank of the last candidate eligible for the seats
				if(i < Quotaleft){
					Waitlisted2.get(i).setwaitlisted(Waitlisted2.get(i).retcurrent().code);
					if(i+1==Quota) quotaextrank = merit2.get(Waitlisted2.get(i)); 
				}
				//< if the extra incoming students rank equal to quotaextrank
				//< quota extended to accommodate him
				//< else he is rejected
				else{
					if(merit2.get(Waitlisted2.get(i))==quotaextrank){
						Waitlisted2.get(i).setwaitlisted(Waitlisted2.get(i).retcurrent().code);
					}
					else{
						Waitlisted2.get(i).setwaitlisted("-1"); //< find the next program which to apply
						Waitlisted2.get(i).setcurrent(Waitlisted2.get(i).findnext()); //< add candidate to rejected category
						Rejected2.add(Waitlisted2.get(i));
					}
				}
			}
			for(int i=0;i<Rejected2.size();i++){ Waitlisted2.remove(Rejected2.get(i)); }//< Remove rejected students from otherwise wait list
		}
		else{ //< if no seats left then whole waitlist rejected
			for(int i=0;i<Waitlisted2.size();i++){
				Waitlisted2.get(i).setwaitlisted("-1"); //< find the next program which to apply
				Waitlisted2.get(i).setcurrent(Waitlisted2.get(i).findnext()); //< add candidate to rejected category
				Rejected2.add(Waitlisted2.get(i));
			}
			for(int i=0;i<Rejected2.size();i++){ Waitlisted2.remove(Rejected2.get(i)); }//< Remove rejected students from wphase2 list
		}
		
		for(int i=0;i<Rejected2.size();i++){ Rejected1.add(Rejected2.get(i)); } //< merge the two rejected lists
		return Rejected1;
	}
	
	
	//---------------------------------------------------------------------------------------------//
	
	/**
	 * @return Returns number of seats covered
	 */
	public int retnum(){ return num; }
	
	/**
	 * Increase number of seats covered by one
	 */
	public void incnum(){ num++; }
	
	/**
	 * Determine whether application of candidate to be waitlisted <br>
	 * USed for Merit Order Algo
	 * @param c Applying Candidate
	 * @param rank Rank of candidate
	 * @return returns whether candidate accepted i.e. waitlisted
	 */
	public Boolean applymerit(Candidate c,int rank){
		if(num<Quota){
			c.setwaitlisted(course);
			num++;
			if(num==Quota){ quotaextrank = rank ; }
			return true;
		}
		else{
			if(rank == quotaextrank){ c.setwaitlisted(course); return true; }
			else{ c.setcurrent(c.findnextmerit()); return false; }
		}
	}
	
	/**
	 * Increases quota by a certain amount <br>
	 * Used while dereservation in Merit Order Algo
	 * @param i Amount by which quota to be increased
	 */
	public void incquota(int i){ Quota = Quota + i ; }
	
	/**
	 * @return Returns leftover seats in the algo
	 */
	public int leftquota(){ return Quota-num; }
	
	/**
	 * Decreases quota to zero <br>
	 * Used to move leftover seats from OBC,OBC-PD,GE-PD to GE during de-reservation
	 */
	public void quotanull(){ Quota = 0 ; }
	
	/**
	 * reset quota extending rank 
	 */
	public void resetextrank(){ quotaextrank = 0; }
	
	/**
	 * @return Returns quota extending rank
	 */
	public int retdecrank(){ return quotaextrank; }
		
}
