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
	private ArrayList<Prog_rep> p2pref; /**< p2pref stores Arraylist of preference ( in case of dereservation ) */
	private String waitlisted; /**< waitlisted stores course code for which waitlisted */
	private Prog_rep current; /**< current stores the preference to which to apply */ 
	Boolean phase2; /**< phase2 stores boolean whether eligible for phase 2 ( dereservation ) */ 
	
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
		p2pref = new ArrayList<Prog_rep>();
		waitlisted = "-1";
		current = new Prog_rep();
		phase2=false;
	}
	
	/**
	 *  This function sets the preference list as per the input and rank of candidate <br>
	 *	The preference list is for phase1 only <br>
	 *	GE category : GE <br>
	 *  OBC category : GE > OBC <br>
	 *  GE-PD category : GE > GE-PD <br>
	 *  OBC-PD category : GE > GE-PD > OBC > OBC-PD <br>
	 *  SC category : GE > SC <br>
	 *  ST category : GE > ST <br>
	 *  SC-PD category : GE > SC > GE-PD > SC-PD <br>
	 *  ST-PD category : GE > ST > GE-PD > ST-PD <br>
	 */
	public void add_preference(int[] ranklist, String course){ 
		codepref.add(course);
		if(category!=3){
		for(int i=0;i<4;i++){
			if(ranklist[i]!=0){ preference.add(new Prog_rep(course,i,false)); }
		}
		for(int i=4;i<8;i++){
			if(ranklist[i]!=0){ preference.add(new Prog_rep(course,i-4,true)); }
		}
		}
		else{
			if(ranklist[0]!=0){ preference.add(new Prog_rep(course,0,false)); }
			if(ranklist[4]!=0){ preference.add(new Prog_rep(course,0,true)); }
			if(ranklist[1]!=0){ preference.add(new Prog_rep(course,1,false)); }
			if(ranklist[5]!=0){ preference.add(new Prog_rep(course,1,true)); }
		}
	}
	
	/**
	 * This function sets the preference list as per the input and rank of candidate
	 * for dereservation. 
	 * <br>
	 * <b>Note:</b> Only GE,SC,ST students who have not got a seat eligible for dereservation. <br>
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
	 */
	public void setp2pref(int[] rank){
		for(int i=0;i<codepref.size();i++){
			if(rank[0]!=0){
				
				if((category==0 || category==1) && PDstatus==false){
					p2pref.add(new Prog_rep(codepref.get(i),0,false));
					p2pref.add(new Prog_rep(codepref.get(i),1,false));
					p2pref.add(new Prog_rep(codepref.get(i),0,true));
					p2pref.add(new Prog_rep(codepref.get(i),1,true));
				}
				
				if((category==0 || category==1) && PDstatus==true){
					p2pref.add(new Prog_rep(codepref.get(i),0,false));
					p2pref.add(new Prog_rep(codepref.get(i),0,true));
					p2pref.add(new Prog_rep(codepref.get(i),1,false));
					p2pref.add(new Prog_rep(codepref.get(i),1,true));
				}
				
				if((category==2 || category==3) && PDstatus==false){
					p2pref.add(new Prog_rep(codepref.get(i),0,false));
					p2pref.add(new Prog_rep(codepref.get(i),category,false));
					p2pref.add(new Prog_rep(codepref.get(i),1,false));
					p2pref.add(new Prog_rep(codepref.get(i),0,true));
					p2pref.add(new Prog_rep(codepref.get(i),1,true));
					p2pref.add(new Prog_rep(codepref.get(i),category,true));
				}
				
				if((category==2 || category==3) && PDstatus==true){
					p2pref.add(new Prog_rep(codepref.get(i),0,false));
					p2pref.add(new Prog_rep(codepref.get(i),category,false));
					p2pref.add(new Prog_rep(codepref.get(i),0,true));
					p2pref.add(new Prog_rep(codepref.get(i),category,true));
					p2pref.add(new Prog_rep(codepref.get(i),1,false));
					p2pref.add(new Prog_rep(codepref.get(i),1,true));
				}
			}
			else if(rank[2]!=0){
				
				if(category==2){
					p2pref.add(new Prog_rep(codepref.get(i),2,false));
					p2pref.add(new Prog_rep(codepref.get(i),2,true));
				}
			}
			else if(rank[3]!=0){
				
				if(category==3){
					p2pref.add(new Prog_rep(codepref.get(i),3,false));
					p2pref.add(new Prog_rep(codepref.get(i),3,true));
				}
			}
		}
	}
	
	/**
	 * This function finds the next Virtual Programme to which to apply
	 * based on the value of the program to which he has applied i.e.
	 * based on the value of 'current' data member
	 * @return Returns the next preference to which to apply
	 */
	public Prog_rep findnext(){
		if(phase2==false){
			int i = preference.indexOf(current);
			if(i+1<preference.size()) return preference.get(i+1);
			else return new Prog_rep();
		}
		
		else{
			/**
			 * If phase2==true the preference list modifies
			 * and the output of findnext should change accordingly
			 */
			int i = p2pref.indexOf(current);
			if(i+1<p2pref.size()) return p2pref.get(i+1);
			else return new Prog_rep();
		}
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
	/** @return return preference arraylist ( by prog_rep ) */
	public ArrayList<Prog_rep> retpreference(){ return preference; }
	/** @return return preference arraylist for phase2 */
	public ArrayList<Prog_rep> retp2pref(){ return p2pref; } 
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
	/** @param a set preference arraylist of student to arraylist a */
	public void setpreference(ArrayList<Prog_rep> a){ 
		preference = a; } 
	/** @param a set phase2 status to boolean a */
	public void setphase2(Boolean a){ phase2=a; } 
	/** @return return phase2 status */
	public Boolean retphase2(){ 
		return phase2; } 
}
