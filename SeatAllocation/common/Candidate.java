package common;
import java.util.*;
/**
 * This class is used to represent a candidate.
 * 
 * @author Group26:JynXD!
 */

/**
 * There are many data members in the Candidate class.
 */
public class Candidate {
	
	/** 
	 * This class is used to represent the preferences of a student.<br>
	 * This format is then used to search the index of the preference
	 * in the Virtual Programme list. <br>
	*/
	public class Prog_rep{
		
		public int category; /**< Category of the program */ 
		public String code;  /**< Code of the program */
		public Boolean PDstatus; /**< PD status of the program */
 
		/** Constructor takes three parameters to initialize category, code and PDstatus 
		 * @param cat category of the preference
		 * @param c Code of the preference
		 * @param pd PD status of the preference 
		 */
		public Prog_rep(String c, int cat, Boolean pd){ 
			category=cat; code=c; PDstatus=pd; }
		
		/** 
		 * By default the code of the preference is set to -1 <br>
		 * This is in accordance to the output required if no seat given to the student
		 */
		public Prog_rep(){ 
			code="-1"; }
	}
	
	private String ID; /**< ID stores Unique ID of student */
	private Boolean PDstatus; /**< PDstatus stores PDstatus of student */
	private int category; /**< category stores Category of student */
	private Boolean Nationality; /**< Nationality stores Nationality of student : false if foreign */
   	private Boolean DSstatus; /**< DSstatus stores DS status of student : false if not of DS category */
    private ArrayList<String> codepref; /**< codepref stores ArrayList of preference ( by code only ) */
	private ArrayList<Prog_rep> preference; /**< preference stores Arraylist of preference ( in form of Prog_rep class ) */ 
	private String waitlisted; /**< waitlisted stores course code for which waitlisted */
	private Prog_rep current; /**< current stores the preference to which to apply */ 
	
	private ArrayList<Prog_rep> prefmerit; /**< preference list of candidate in case of merit order algo */
	
	/**
	 * This is the constructor of the class. <br>
	 * Takes only ID, Category and PD status as parameter. <br>
	 * Nationality and DSstatus are configured accordingly. <br>
	 * Note: Every category has been allotted an integer code. <br>
	 * 1 - GE, 2 - OBC, 3 - SC, 4 - ST, 8 - DS , 9 - F (foreign) <br>
	 * All the arrays have been initialised to new(). <br>
	 * By the default, the waitlisted is set to "-1" in accordance to the output if 
	 * the student gets no seat. <br>
	 * By default, no candidate is eligible for phase2.
	 * @param a Unique ID of candidate
	 * @param pd PD status of candidate
	 * @param categ category of student
	 */
	public Candidate(String a, String pd, String categ){
		ID=a;
		if(pd.equals("Y")) PDstatus=true; else PDstatus=false;
		if(categ.equals("GE")) category=0;
		else if(categ.equals("OBC")) category=1;
		else if(categ.equals("SC")) category=2;
		else if(categ.equals("ST")) category=3;
		else if(categ.equals("F")) category=9;
		else if(categ.equals("DS")) category=8;
		if(category==9) Nationality=false; else Nationality=true;
		if(category==8) DSstatus=true; else DSstatus=false;
		codepref = new ArrayList<String>();
		preference = new ArrayList<Prog_rep>();
		waitlisted = "-1";
		current = new Prog_rep();
		
		prefmerit = new ArrayList<Prog_rep>();
	}
	
	/**
	 * This function sets the preference list as per the input and rank of candidate
	 * <br>
	 *  if GE/OBC candidate with no pwd <br>
	 *  Modified Preference : GE > OBC > GE-PD > OBC-PD <br><br>
	 *  if GE/OBC candidate with pwd <br>
	 *  Modified Preference : GE > GE-PD > OBC > OBC-PD <br><br>
	 * if SC/ST with GE rank with no pwd<br>
	 * Modified preference for SC : GE > SC > OBC > GE-PD > OBC-PD > SC-PD<br> 
	 * Modified preference for ST : GE > ST > OBC > GE-PD > OBC-PD > ST-PD<br><br>
	 * if SC/ST with GE rank with pwd<br>
	 * Modified preference for SC : GE > SC > GE-PD > SC-PD > OBC > OBC-PD <br> 
	 * Modified preference for ST : GE > ST > GE-PD > ST-PD > OBC > OBC-PD <br><br>
	 * if SC with no GE rank <br>
	 * Modified preference for SC : SC > SC-PD <br><br> 
	 * if ST with no GE rank<br>
	 * Modified preference for ST : ST > ST-PD <br><br> 
	 * @param rank ranklist of candidate for correct order of preference
	 * @param course course-code of preference
	 */
	public void addpreference(int[] rank, String course){
			codepref.add(course);
			if(rank[0]!=0){ //< ge rank non zero
				if(rank[2]==0 && rank[3]==0){ //< student of ge/obc category
				if((rank[0]!=0 || rank[1]!=0) && PDstatus==false){ //<student with no disability
					preference.add(new Prog_rep(course,0,false));
					preference.add(new Prog_rep(course,1,false));
					preference.add(new Prog_rep(course,0,true));
					preference.add(new Prog_rep(course,1,true));
				}
				
				if((rank[0]!=0 || rank[1]!=0) && PDstatus==true){ //<student with disability
					preference.add(new Prog_rep(course,0,false));
					preference.add(new Prog_rep(course,0,true));
					preference.add(new Prog_rep(course,1,false));
					preference.add(new Prog_rep(course,1,true));
				}}
				
				if((rank[2]!=0 || rank[3]!=0) && PDstatus==false){ //< student of SC/ST with no disability
					preference.add(new Prog_rep(course,0,false));
					preference.add(new Prog_rep(course,(rank[2]!=0)?2:3,false));
					preference.add(new Prog_rep(course,1,false));
					preference.add(new Prog_rep(course,0,true));
					preference.add(new Prog_rep(course,1,true));
					preference.add(new Prog_rep(course,(rank[2]!=0)?2:3,true));
				}
				
				if ((rank[2]!=0 || rank[3]!=0) && PDstatus==true){ //< student of SC/ST with disability
					preference.add(new Prog_rep(course,0,false));
					preference.add(new Prog_rep(course,(rank[2]!=0)?2:3,false));
					preference.add(new Prog_rep(course,0,true));
					preference.add(new Prog_rep(course,(rank[2]!=0)?2:3,true));
					preference.add(new Prog_rep(course,1,false));
					preference.add(new Prog_rep(course,1,true));
				}
			}
			else if(rank[1]!=0){ //< OBC student with no GE rank
				
				if(PDstatus==false){ preference.add(new Prog_rep(course,1,false)); }
				if(PDstatus==true){
					preference.add(new Prog_rep(course,1,false));
					preference.add(new Prog_rep(course,1,true));
				}
			}
			else if(rank[2]!=0){ //< SC student with no GE rank
				
					preference.add(new Prog_rep(course,2,false));
					preference.add(new Prog_rep(course,2,true));
			}
			else if(rank[3]!=0){ //< ST student with no GE rank
					preference.add(new Prog_rep(course,3,false));
					preference.add(new Prog_rep(course,3,true));
			}
			else if(rank[4]!=0){ //< GE-PD student with no GE rank
				preference.add(new Prog_rep(course,0,true)); 
			}
			else if(rank[5]!=0){ //< OBC-PD student with no GE & OBC rank
				preference.add(new Prog_rep(course,1,true)); 
			}
			else if(rank[6]!=0){ //< SC-PD student with no GE & SC rank
				preference.add(new Prog_rep(course,2,true)); 
			}
			else if(rank[7]!=0){ //< ST-PD student with no GE & ST rank
				preference.add(new Prog_rep(course,3,true)); 
			}
		}
	
	/**
	 * This function finds the next Virtual Programme to which to apply
	 * based on the value of the program to which he has applied i.e.
	 * based on the value of 'current' data member
	 * @return Returns the next preference to which to apply
	 */
	public Prog_rep findnext(){
			int i = preference.indexOf(current);
			if(i+1<preference.size()) return preference.get(i+1);
			else return new Prog_rep();
	}
	
	/** @return returns ID */
	public String retID(){ return ID;  } 
	/** @return returns category */
	public int retcategory(){ return category;  }
	 /** @return return PDstatus */
	public Boolean retPDstatus(){ return PDstatus; }  
	/** @return return Nationality */
	public Boolean retNationality(){ return Nationality;  }
	/** @return return DSstatus */
	public Boolean retDSstatus(){ return DSstatus;  }
	/** @return return current program to which applied */
	public Prog_rep retcurrent(){ return current; } 
	/** @return return program for which waitlisted */
	public String retwaitlisted(){ return waitlisted;  }
	/** @return return preference arraylist (by code ) */
	public ArrayList<String> retcodepref(){ return codepref; } 
	/** @return return preference arraylist ( by prog_rep ) of Gale Shapley Algorithm */
	public ArrayList<Prog_rep> retpreference(){ return preference; }
	/** @param a sets ID of student to string a */
	public void setID(String a){ 
 		ID=a; } /// set ID
	/** @param a set category of student to int a */
	public void setcategory(int a){ 
		category=a; }
	/** @param a set PDstatus of student to boolean a */
	public void setPDstatus(Boolean a){
		PDstatus=a; } 
	/** @param a set Nationality of student to boolean a */
	public void setNationality(Boolean a){ 
		Nationality=a; } 
	/** @param a set DSstatus of student to boolean a */
	public void setDSstatus(Boolean a){ 
		DSstatus=a; }
	/** @param a set current program of student to Prog_rep a */
	public void setcurrent(Prog_rep a){
		current = a; }  
	/** @param a set waitlisted program code of student to String a */
	public void setwaitlisted(String a){ 
		waitlisted = a; } 
	/** @param a set preference arraylist of student to arraylist a for gale shapley algo */
	public void setpreference(ArrayList<Prog_rep> a){ 
		preference = a; }  

//-------------------------------------------------------------------------------------------------------//
	/**
	 * This function sets the preference list as per the input and rank of candidate for merit order algo
	 * <br>
	 * The preferences of a virtual program are simply in order of the non zero ranks of the candidate
	 * @param rank ranklist of candidate for correct order of preference
	 * @param course course-code of preference
	 */
	public void addprefmerit(int[] ranks, String course){
		for(int i=0;i<8;i++){
			if(ranks[i]!=0 && i<4) prefmerit.add(new Prog_rep(course,i,false));
			if(ranks[i]!=0 && i>=4) prefmerit.add(new Prog_rep(course,i-4,true));
		}
	}

	/** @return return preference arraylist ( by prog_rep ) for merit order algo */
	public ArrayList<Prog_rep> retprefmerit(){ return prefmerit; }
	
	/** @return returns next program preference in case of merit order algo */
	public Prog_rep findnextmerit(){
		int i = prefmerit.indexOf(current);
		if(i+1<prefmerit.size()) return prefmerit.get(i+1);
		else return new Prog_rep();
	}
}
