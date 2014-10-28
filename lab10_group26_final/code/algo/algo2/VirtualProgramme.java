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

	public Boolean applyalgo2(Candidate c, String cat , Boolean pd){	
		System.out.println("application of "+ c.Id + cat + " "  + "in" + code + " " + category); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){  // check if quota empty
			 
				System.out.println("Accepting the application");
				
				switch(category){									// stores the rank of latest candidate in lastadded variable every time a candidate is waitlisted
					case "GE" : lastadded = c.rank[0]; break;     	
					case "OBC" : lastadded = c.rank[1]; break;
					case "SC" : lastadded = c.rank[2]; break;    // different ranks are stored depending on the category of our programme
					case "ST" : lastadded = c.rank[3]; break;
					case "GE_PD" : lastadded = c.rank[4]; break;
					case "OBC_PD" : lastadded = c.rank[5]; break;
					case "SC_PD" : lastadded = c.rank[6]; break;
					case "ST_PD" : lastadded = c.rank[7]; break;
					
				}
				return true; 
			}
			
		
		
		else { if(cat.equals(category) && pd.equals(pdstatus)){ System.out.println(cat+ " " + category);  // when quota is actually full but needs to extend due to same rank
				switch(category){   
			                  
					case "GE" : if(lastadded == c.rank[0]){System.out.println("by extending quota ge");return true;} break;         // rank of the candidate is checked with the lastadded variable
					case "OBC" : if(lastadded == c.rank[1]){System.out.println("by extending quota obc");return true;} break;   
					case "SC" : if(lastadded == c.rank[2]){System.out.println("by extending quota sc");return true;} break;          // if found equal then this candidate is also shortlisted         
					case "ST" : if(lastadded == c.rank[3]){System.out.println("by extending quota st");return true;} break;         
					case "GE_PD" : if(lastadded == c.rank[4]){System.out.println("by extending quota gepd");return true;} break;
					case "OBC_PD" : if(lastadded == c.rank[5]){System.out.println("by extending quota obcpd");return true;} break;
					case "SC_PD" : if(lastadded == c.rank[6]){System.out.println("by extending quota scpd");return true;} break;	
					case "ST_PD" : if(lastadded == c.rank[7]){System.out.println("by extending quota stpd");return true;} break;
					
				}
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
				switch(cat){                                       // stores the rank of latest candidate added during de-reservation in p2lastadded variable
					case "GE" : p2lastadded = c.rank[0]; break;
					case "OBC" : p2lastadded = c.rank[1]; break; 
					case "SC" : p2lastadded = c.rank[2]; break;
					case "ST" : p2lastadded = c.rank[3]; break;
					case "GE_PD" : p2lastadded = c.rank[4]; break;
					case "OBC_PD" : p2lastadded = c.rank[5]; break;
					case "SC_PD" : p2lastadded = c.rank[6]; break;
					case "ST_PD" : p2lastadded = c.rank[7]; break;
					
				}
				return true; 
			}
		
		
		else { if(cat.equals(cat)){         // when quota is actually full but needs to extend due to same rank
				switch(cat){   
			                 
					case "GE" : if(p2lastadded == c.rank[0]){System.out.println("dr by extending quota ge");return true;} break;                // rank of the candidate is checked with the lastadded variable
					case "OBC" : if(p2lastadded == c.rank[1]){System.out.println("dr by extending quota obc");return true;} break;
					case "SC" : if(p2lastadded == c.rank[2]){System.out.println("dr by extending quota sc");return true;} break;
					case "ST" : if(p2lastadded == c.rank[3]){System.out.println("dr by extending quota st");return true;} break;				// if found equal then this candidate is also shortlisted 
					case "GE_PD" : if(p2lastadded == c.rank[4]){System.out.println("dr by extending quota gepd");return true;} break;
					case "OBC_PD" : if(p2lastadded == c.rank[5]){System.out.println("dr by extending quota obcpd");return true;} break;
					case "SC_PD" : if(p2lastadded == c.rank[6]){System.out.println("dr by extending quota scpd");return true;} break;
					case "ST_PD" : if(p2lastadded == c.rank[7]){System.out.println("dr by extending quota stpd");return true;} break;
					
				}
			  } 
		}
		System.out.println("quota full");
		return false;
		}
	
	}


