package algo2;

public class VirtualProgramme{
	String code;			// code of the parent programme
	String category;		// category of the programme
	Boolean pdstatus;		// pdstatus of the programme
	int quota;				// maximum quota of the programme
	int num;   				// stores the number of already shortlisted students
	int lastadded;
	//Candidate[] waitlist; 	// stores the shortlisted candidates
	
	public VirtualProgramme(String cod, String cat, Boolean pd, int q){ //Default Constructor 
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
	public Boolean applyalgo2(Candidate c){	
		//System.out.println("application of "+ c.Id + " " + cat + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		if(num+1 <= quota){  // check if quota empty
			//if( cat.equals(category) && pd == pdstatus){ //check category and pd status of student and programme 
				//System.out.println("Accepting the application");
				//waitlist[num] = c; 
				//num++; // increase seats occupied if candidate waitlisted
				switch(category){
					case GE : lastadded = c.rank[0]; break;
					case OBC : lastadded = c.rank[1]; break;
					case SC : lastadded = c.rank[2]; break;
					case ST : lastadded = c.rank[3]; break;
					case GE_PD : lastadded = c.rank[4]; break;
					case OBC_PD : lastadded = c.rank[5]; break;
					case SC_PD : lastadded = c.rank[6]; break;
					case ST_PD : lastadded = c.rank[7]; break;
					
				}
				return true; 
			}
			//else {/*System.out.println("not a valid application");*/ return false;}
		
		
		else { switch(category){                  // when quota is actually full but needs to extend due to same rank
					case GE : if(lastadded == c.rank[0]){lastadded = c.rank[0];return true;}
					case OBC : if(lastadded == c.rank[1]){lastadded = c.rank[1];return true;}
					case SC : if(lastadded == c.rank[2]){lastadded = c.rank[2];return true;}
					case ST : if(lastadded == c.rank[3]){lastadded = c.rank[3];return true;}
					case GE_PD : if(lastadded == c.rank[4]){lastadded = c.rank[4];return true;}
					case OBC_PD : if(lastadded == c.rank[5]){lastadded = c.rank[5];return true;}
					case SC_PD : if(lastadded == c.rank[6]){lastadded = c.rank[6];return true;}
					case ST_PD : if(lastadded == c.rank[7]){lastadded = c.rank[7];return true;}
					
				} 
			  }
		return false; 
	}

	public Boolean apply2(/*Candidate c*/){	//System.out.println("application of "+ c.Id + " " + cat + "in" + code + " " + category); System.out.println(num); System.out.println(quota);					// for new applicant
		//System.out.println(code+" "+" "+quota+" "+num+" "+(quota-num)+" "+category);
		if(num+1 <= quota){ 
			//if( cat.equals(category) && pd == pdstatus){ System.out.println("Accepting the application");
				//waitlist[num] = c; 
				num++; 
				return true; 
			
			//else {System.out.println("not a valid application"); return false;}
		}
		else { /*System.out.println("Quota full");*/ return false; }
	}

}
