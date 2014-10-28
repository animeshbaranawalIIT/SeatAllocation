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
	
	
	public VirtualProgramme(String cod, String cat, Boolean pd, int q){ //Default Constructor 
		this.code= cod; 
		this.category=cat; 
		this.pdstatus=pd; this.quota=q; this.num=0; 
		  
	}

	public Boolean applyalgo2(Candidate c, String cat , Boolean pd){	
		System.out.println("application of "+ c.Id + cat + " "  + "in" + code + " " + category); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){  // check if quota empty
			//if( cat.equals(category) && pd == pdstatus){ //check category and pd status of student and programme 
				System.out.println("Accepting the application");
				//waitlist[num] = c; 
				//num++; // increase seats occupied if candidate waitlisted
				switch(category){
					case "GE" : lastadded = c.rank[0]; break;     
					case "OBC" : lastadded = c.rank[1]; break;
					case "SC" : lastadded = c.rank[2]; break;
					case "ST" : lastadded = c.rank[3]; break;
					case "GE_PD" : lastadded = c.rank[4]; break;
					case "OBC_PD" : lastadded = c.rank[5]; break;
					case "SC_PD" : lastadded = c.rank[6]; break;
					case "ST_PD" : lastadded = c.rank[7]; break;
					
				}
				return true; 
			}
			
		
		
		else { if(cat.equals(category) && pd.equals(pdstatus)){ System.out.println(cat+ " " + category);
				switch(category){   
			                  // when quota is actually full but needs to extend due to same rank
					case "GE" : if(lastadded == c.rank[0]){System.out.println("by extending quota ge");System.out.println(c.rank[0]);lastadded = c.rank[0];return true;} break;
					case "OBC" : if(lastadded == c.rank[1]){System.out.println("by extending quota obc");System.out.println(c.rank[1]);lastadded = c.rank[1];return true;} break;
					case "SC" : if(lastadded == c.rank[2]){System.out.println("by extending quota sc");System.out.println(c.rank[2]);lastadded = c.rank[2];return true;} break;
					case "ST" : if(lastadded == c.rank[3]){System.out.println("by extending quota st");System.out.println(c.rank[3]);lastadded = c.rank[3];return true;} break;
					case "GE_PD" : if(lastadded == c.rank[4]){System.out.println("by extending quota gepd");System.out.println(c.rank[4]);lastadded = c.rank[4];return true;} break;
					case "OBC_PD" : if(lastadded == c.rank[5]){System.out.println("by extending quota obcpd");System.out.println(c.rank[5]);lastadded = c.rank[5];return true;} break;
					case "SC_PD" : if(lastadded == c.rank[6]){System.out.println("by extending quota scpd");System.out.println(c.rank[6]);lastadded = c.rank[6];return true;} break;
					case "ST_PD" : if(lastadded == c.rank[7]){System.out.println("by extending quota stpd");System.out.println(c.rank[7]);lastadded = c.rank[7];return true;} break;
					
				}
			  } 
		}
		System.out.println("quota full");
		return false; 
	}

	public Boolean applyalgo(Candidate c, String cat){	//System.out.println("application of "+ c.Id + " " + cat + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		System.out.println("application of "+ c.Id + cat + " "  + "in" + code + " " + category+ " for dereservation"); System.out.println(pdstatus);System.out.println(lastadded);System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){  // check if quota empty
			//if( cat.equals(category) && pd == pdstatus){ //check category and pd status of student and programme 
				System.out.println("Accepting the application");
				//waitlist[num] = c; 
				num++; // increase seats occupied if candidate waitlisted
				switch(cat){
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
		
		
		else { if(cat.equals(category)){ System.out.println(cat+ " " + category);
				switch(category){   
			                  // when quota is actually full but needs to extend due to same rank
					case "GE" : if(lastadded == c.rank[0]){System.out.println("dr by extending quota ge");System.out.println(c.rank[0]);lastadded = c.rank[0];return true;} break;
					case "OBC" : if(lastadded == c.rank[1]){System.out.println("dr by extending quota obc");System.out.println(c.rank[1]);lastadded = c.rank[1];return true;} break;
					case "SC" : if(lastadded == c.rank[2]){System.out.println("dr by extending quota sc");System.out.println(c.rank[2]);lastadded = c.rank[2];return true;} break;
					case "ST" : if(lastadded == c.rank[3]){System.out.println("dr by extending quota st");System.out.println(c.rank[3]);lastadded = c.rank[3];return true;} break;
					case "GE_PD" : if(lastadded == c.rank[4]){System.out.println("dr by extending quota gepd");System.out.println(c.rank[4]);lastadded = c.rank[4];return true;} break;
					case "OBC_PD" : if(lastadded == c.rank[5]){System.out.println("dr by extending quota obcpd");System.out.println(c.rank[5]);lastadded = c.rank[5];return true;} break;
					case "SC_PD" : if(lastadded == c.rank[6]){System.out.println("dr by extending quota scpd");System.out.println(c.rank[6]);lastadded = c.rank[6];return true;} break;
					case "ST_PD" : if(lastadded == c.rank[7]){System.out.println("dr by extending quota stpd");System.out.println(c.rank[7]);lastadded = c.rank[7];return true;} break;
					
				}
			  } 
		}
		System.out.println("quota full");
		return false;
		}
	
	}


