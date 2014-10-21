import java.util.*;
import java.io.*;

class VirtualProgramme{
	String code;			// code of the parent programme
	String category;		// category of the programme
	Boolean pdstatus;		// pdstatus of the programme
	int quota;				// maximum quota of the programme
	int num;   				// stores the number of already shortlisted students
	//Candidate[] waitlist; 	// stores the shortlisted candidates
	
	public VirtualProgramme(String cod, String cat, Boolean pd, int q){ 
		this.code= cod; 
		this.category=cat; 
		this.pdstatus=pd; this.quota=q; this.num=0;
		//this.waitlist = new Candidate[q];  
	}

	/*public Boolean apply(Candidate c){	System.out.println("application of "+ c.Id + " " + c.Category + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){ 
			if( c.Category.equals(category) && c.PD == pdstatus){ System.out.println("Accepting the application");
				//waitlist[num] = c; 
				num++; 
				return true; 
			}
			else {System.out.println("not a valid application"); return false;}
		}
		else { System.out.println("Quota full"); return false; }
	}*/
	public Boolean apply(Candidate c, String cat, Boolean pd){	System.out.println("application of "+ c.Id + " " + cat + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){ 
			if( cat.equals(category) && pd == pdstatus){ System.out.println("Accepting the application");
				//waitlist[num] = c; 
				num++; 
				return true; 
			}
			else {System.out.println("not a valid application"); return false;}
		}
		else { System.out.println("Quota full"); return false; }
	}
}