package algo2;
import java.io.*;

public class VirtualProgramme{
	String code;			// code of the parent programme
	String category;		// category of the programme
	Boolean pdstatus;		// pdstatus of the programme
	int quota;				// maximum quota of the programme
	int num;   				// stores the number of already shortlisted students
	int lastadded;			// stores the rank of last added candidate in phase1
	int p2lastadded;		// stores the rank of last added candidate in phase2
	
	
	public VirtualProgramme(String cod, String cat, Boolean pd, int q){ // Constructor 
		this.code= cod; 
		this.category=cat; 
		this.pdstatus=pd; this.quota=q; this.num=0; 
		  
	}

	public Boolean extender(String cate, int i, Candidate c){                 // function for extending the quota if needed
		//System.out.println("Inside extender" + cate);
		switch(cate){   
			                  
					case "GE" : if(i == c.rank[0]){System.out.println("by extending quota ge");return true;} break;         // rank of the candidate is checked with the lastadded variable
					case "OBC" : if(i == c.rank[1]){System.out.println("by extending quota obc");return true;} break;   
					case "SC" : if(i == c.rank[2]){System.out.println("by extending quota sc");return true;} break;          // if found equal then this candidate is also shortlisted         
					case "ST" : if(i == c.rank[3]){System.out.println("by extending quota st");return true;} break;         
					case "GE_PD" : if(i == c.rank[4]){System.out.println("by extending quota gepd");return true;} break;
					case "OBC_PD" : if(i == c.rank[5]){System.out.println("by extending quota obcpd");return true;} break;
					case "SC_PD" : if(i == c.rank[6]){System.out.println("by extending quota scpd");return true;} break;	
					case "ST_PD" : if(i == c.rank[7]){System.out.println("by extending quota stpd");return true;} break;
					
				}
				//System.out.println("returning false");
		return false;

	}

	public int storer(String cate, Candidate c){  // stores the rank of latest candidate in  variable every time a candidate is waitlisted
		int i=0;
		switch(cate){									
					case "GE" : i = c.rank[0]; break;     	
					case "OBC" : i = c.rank[1]; break;
					case "SC" : i = c.rank[2]; break;    // different ranks are stored depending on the category of our programme
					case "ST" : i = c.rank[3]; break;
					case "GE_PD" : i = c.rank[4]; break;
					case "OBC_PD" : i = c.rank[5]; break;
					case "SC_PD" : i = c.rank[6]; break;
					case "ST_PD" : i = c.rank[7]; break;
					
				}
		return i;
	}

	public Boolean applyalgo2(Candidate c, String cat , Boolean pd){	
		System.out.println("application of "+ c.Id + cat + " "  + "in" + code + " " + category); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){  // check if quota empty
			 
				System.out.println("Accepting the application");
				lastadded=storer(category,c);     // function is called which stores the rank of last added candidate
				//System.out.println("lastadded");
				//System.out.println(lastadded);
				return true; 
			}
			
		else { if(cat.equals(category) && pd.equals(pdstatus)){ System.out.println(cat+ " " + category);  // when quota is actually full but needs to extend due to same rank
				if(extender(category,lastadded,c)) return true;         // function is called to waitlist the candidate by extending the quota if needed
			  } 
		}
		System.out.println("quota full");
		return false; 
	}


	public Boolean applyalgo(Candidate c, String cat){	//System.out.println("application of "+ c.Id + " " + cat + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		System.out.println("application of "+ c.Id + cat + " "  + "in" + code + " " + category+ " for dereservation"); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){   
				System.out.println("Accepting the application");
				num++; // increase seats occupied if candidate waitlisted
				p2lastadded= storer(cat,c);				// function is called which stores the rank of last added candidate
				System.out.println("p2lastadded");
				System.out.println(p2lastadded);
				return true; 
			}
		else { if(cat.equals(cat)){         // when quota is actually full but needs to extend due to same rank
				if (extender(cat,p2lastadded,c)) return true;				 // function is called to waitlist the candidate by extending the quota if needed
			   } 
		}
		System.out.println("quota full");
		return false;
		}
	
	}


