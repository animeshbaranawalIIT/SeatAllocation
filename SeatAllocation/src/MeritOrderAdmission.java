import java.util.*;
import java.io.File;
import java.io.IOException;

class MeritOrderAdmission{
	
	ArrayList <VirtualProgramme> progs; // Virtual Programmes 
	MeritList candidatelist; // List of candidates
	MeritListforAlgo2 mylist; // Appended Merit List for Algo 2

	public MeritOrderAdmission(String f1, String f2, String f3) { // file names as constructor parameters
		progs = new ArrayList<VirtualProgramme>(); // initialising data members
		candidatelist = new MeritList(f1,f2);
		mylist = new MeritListforAlgo2(candidatelist.candidates);
		mylist.sortbyrank();
		this.fillprogramme(f3); // filling the virtual programme details
		for(int j=0;j<mylist.ge.size();j++) phase1(mylist.ge.get(j),"GE",false);
		for(int j=0;j<mylist.obc.size();j++) phase1(mylist.obc.get(j),"OBC",false);
		for(int j=0;j<mylist.sc.size();j++) phase1(mylist.sc.get(j),"SC",false);
		for(int j=0;j<mylist.st.size();j++) phase1(mylist.st.get(j),"ST",false);
		for(int j=0;j<mylist.ge_pd.size();j++) phase1(mylist.ge_pd.get(j),"GE_PD",true);
		for(int j=0;j<mylist.obc_pd.size();j++) phase1(mylist.obc_pd.get(j),"OBC_PD",true);
		for(int j=0;j<mylist.sc_pd.size();j++) phase1(mylist.sc_pd.get(j),"SC_PD",true);
		for(int j=0;j<mylist.st_pd.size();j++) phase1(mylist.st_pd.get(j),"ST_PD",true);
		System.out.println("Phase 1 completed successfully....");
		System.out.println("Phase 2 initialising....");
		for(int j=0;j<candidatelist.candidates.size();j++){ phase2(candidatelist.candidates.get(j)); } 
	}
	
	public int find(String cd, String cat, Boolean pd){ // function to find the index of programme in prog list given its code, category and pd satus
		for(int j=0;j<progs.size();j++){
			if(progs.get(j).code.equals(cd)){
				if(progs.get(j).category.equals(cat)){
					if(progs.get(j).pdstatus.equals(pd)){ return j; }
				}
			}
		}
		return -1;
	}
	
	public void fillprogramme(String f){ // function to fill virtual programme details
		File file = new File(f);
		
		try{	
			Scanner inp = new Scanner(file);
			String strLine="";
			strLine= inp.nextLine();
			String[] temp2;		// array to store the name of columns to be used in virtual programmes
			String del =",";
			temp2 = strLine.split(del);
			while (inp.hasNext()){   
				strLine = inp.nextLine(); 
				String[] temp;				// array for storing for rest of the rows
				temp = strLine.split(del);  
				for(int i=3; i<11; i++){ 
					VirtualProgramme v;
					if(i<=6){
						v = new VirtualProgramme(temp[1],temp2[i],false,Integer.parseInt(temp[i]));
					}  // cons for non PD virtual progs 
					else{v= new VirtualProgramme(temp[1],temp2[i],true,Integer.parseInt(temp[i]));}		// cons for PD virtual progs
					progs.add(v);  // virtual progs added to the array of 
				}
			}
			inp.close();
		}
		catch (IOException ex){System.out.println("sada"); }
	}
	
	public void phase1(Candidate c,String cat,Boolean pd){ //implement phase 1 for a candidate
		int i; String temp;
		for(int j=0;j<c.Preference.size();j++){ // searching over preference list of candidate
			i=find(c.Preference.get(j),cat,pd); // finding index of program in the list
			System.out.println(i);
			if(progs.get(i).applyalgo2(c, cat, pd)){ 
				System.out.println("App accepted");
				temp = c.Preference.get(j); // waitlisted program
				System.out.println(temp);
				if(c.waiting.equals("")){ System.out.println("adding to empty"); c.waiting=temp; c.previouswaitlist=i; } // assign waitlisted program if waitlist initially empty
				else{ 
					if(c.Preference.indexOf(temp)<c.Preference.indexOf(c.waiting)){ //update waitlist if waitlist initially not empty 
						progs.get(c.previouswaitlist).num--;
						c.previouswaitlist=i;
						c.waiting=temp; 
					}
					else {System.out.println("Found but not gud enough");} 
				}
				break;
			}
		}
	}


	public void phase2(Candidate c){
		int i,j; 
		if(!c.waiting.equals("")){ j = c.Preference.indexOf(c.waiting); }
		else { j = c.Preference.size(); }
			for(int q=0; q<j ; q++){
				i = find(c.Preference.get(q),"GE",false);

								switch(c.Category){
												case "GE" : if(!c.PD){ 	if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;}; c.waitchanger(c,q,i); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}
														  				}
														  else { 			if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}
																}

												case "OBC" :	if(!c.PD){ if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					 if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  				     if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					 if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}
														  				}
														  else { 			 if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					 if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					 if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					 if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}
																}

												case "SC" : if(!c.PD){
																			if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+2); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+6); break;}	
																		}

															else{  			if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					if(progs.get(i+2).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+2); break;}
														  					if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					if(progs.get(i+6).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+6); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}


															}
												case "ST" : if(!c.PD){
																			if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+3); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+7); break;}	
																		}

															else{  			if(progs.get(i).apply2(c)) {if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i); break;}
														  					if(progs.get(i+2).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+3); break;}
														  					if(progs.get(i+4).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+4); break;}
														  					if(progs.get(i+6).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+7); break;}
														  					if(progs.get(i+1).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+1); break;}
														  					if(progs.get(i+5).apply2(c)){if(c.waiting!=""){progs.get(c.previouswaitlist).num--;};c.waitchanger(c,q,i+5); break;}


																}

											}
														 	
									}
		}
}
