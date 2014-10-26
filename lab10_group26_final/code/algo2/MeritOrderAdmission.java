package algo2;

import java.util.*;
import java.io.File;
import java.io.IOException;


public class MeritOrderAdmission{
	
	class DSq{
		String Insti_code;
		int seats;
		
		public DSq(String code){ Insti_code=code; seats=2; }
	}
	
	ArrayList <VirtualProgramme> progs; // Virtual Programmes 
	MeritList candidatelist; // List of candidates
	MeritListforAlgo2 mylist; // Appended Merit List for Algo 2
	ArrayList <DSq>Special; 
	
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
	
	public void fillDSquota(){
		String comparison="";
		for(int i=0;i<progs.size();i=i+8){
			String temp = progs.get(i).code.substring(0,1);
			if(!temp.equals(comparison)){ Special.add(new DSq(temp)); }
			else {}
		}
	}
	
	public int findds(String cd){ // function to find the index of programme in prog list given its code, category and pd satus
		for(int j=0;j<Special.size();j++){
			if(Special.get(j).Insti_code.equals(cd)){ return j; }
		}
		return -1;
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
	
	class ldids implements Comparator<Candidate>{ // comparator for comparing two candidates from ge merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[0],e2.rank[0]);
	    }
	}
	
	class ldisort implements Comparator<Candidate>{
		public int compare(Candidate e1, Candidate e2) {
			return (e1.Id).compareTo(e2.Id);
		}
	}
	
	public void checkfds(){
		int k;
		for(int i=0;i<candidatelist.DScandidate.size();i++){
				for(int j=0;j<candidatelist.DScandidate.get(i).Preference.size();j++){
					k=findds(candidatelist.DScandidate.get(i).Preference.get(j).substring(0,1));
					if(Special.get(k).seats>0){
						candidatelist.DScandidate.get(i).waiting=candidatelist.DScandidate.get(i).Preference.get(j); 
						Special.get(k).seats--; 
						candidatelist.DScandidate.get(i).rank[0]=0;
						break;
					}
				}
				candidatelist.DScandidate.get(i).Category="GE";
				//for(int l=0;l<8;l++){ System.out.println(candidatelist.DScandidate.get(i).rank[l]); } 
				candidatelist.candidates.add(candidatelist.DScandidate.get(i));
		}
	}
				
	
	public MeritOrderAdmission(String f1, String f2, String f3) { // file names as constructor parameters
		progs = new ArrayList<VirtualProgramme>(); // initialising data members
		candidatelist = new MeritList(f1,f2);
		Collections.sort(candidatelist.DScandidate,new ldids());
		Special = new ArrayList<DSq>();
		this.fillprogramme(f3); // filling the virtual programme details
		this.fillDSquota();
		this.checkfds();
		mylist = new MeritListforAlgo2(candidatelist.candidates);
		mylist.sortbyrank();
		for(int j=0;j<mylist.ge.size();j++) phase1(mylist.ge.get(j),"GE",false);
		for(int j=0;j<mylist.obc.size();j++) phase1(mylist.obc.get(j),"OBC",false);
		for(int j=0;j<mylist.sc.size();j++) phase1(mylist.sc.get(j),"SC",false);
		for(int j=0;j<mylist.st.size();j++) phase1(mylist.st.get(j),"ST",false);
		for(int j=0;j<mylist.ge_pd.size();j++) phase1(mylist.ge_pd.get(j),"GE_PD",true);
		for(int j=0;j<mylist.obc_pd.size();j++) phase1(mylist.obc_pd.get(j),"OBC_PD",true);
		for(int j=0;j<mylist.sc_pd.size();j++) phase1(mylist.sc_pd.get(j),"SC_PD",true);
		for(int j=0;j<mylist.st_pd.size();j++) phase1(mylist.st_pd.get(j),"ST_PD",true);
		//System.out.println("Phase 1 completed successfully....");
		//System.out.println("Phase 2 initialising....");
		for(int j=0;j<mylist.ge.size();j++) phase2(mylist.ge.get(j));
		for(int j=0;j<mylist.obc.size();j++) phase2(mylist.obc.get(j));
		for(int j=0;j<mylist.sc.size();j++) phase2(mylist.sc.get(j));
		for(int j=0;j<mylist.st.size();j++) phase2(mylist.st.get(j));
		for(int j=0;j<mylist.ge_pd.size();j++) phase2(mylist.ge_pd.get(j));
		for(int j=0;j<mylist.obc_pd.size();j++) phase2(mylist.obc_pd.get(j));
		for(int j=0;j<mylist.sc_pd.size();j++) phase2(mylist.sc_pd.get(j));
		for(int j=0;j<mylist.st_pd.size();j++) phase2(mylist.st_pd.get(j));
		Collections.sort(candidatelist.candidates,new ldisort());
	}
	
	
	
	public void phase1(Candidate c,String cat,Boolean pd){ //implement phase 1 for a candidate
		int i; String temp;
		for(int j=0;j<c.Preference.size();j++){ // searching over preference list of candidate
			i=find(c.Preference.get(j),cat,pd); // finding index of program in the list
			//System.out.println(i);
			if(progs.get(i).applyalgo2()){ 
				//System.out.println("App accepted");
				temp = c.Preference.get(j); // waitlisted program
				//System.out.println(temp);
				if(c.waiting.equals("")){ /*System.out.println("adding to empty");*/ progs.get(i).num++; c.waiting=temp; c.previouswaitlist=i; } // assign waitlisted program if waitlist initially empty
				else{ 
					if(c.Preference.indexOf(temp)<c.Preference.indexOf(c.waiting)){ //update waitlist if waitlist initially not empty 
						progs.get(i).num++;
						progs.get(c.previouswaitlist).num--;
						c.previouswaitlist=i;
						c.waiting=temp; 
					}
					else {/*System.out.println("Found but not gud enough");*/} 
				}
				break;
			}
		  }
	  }

	public void phase2(Candidate c){
		int i,j; Boolean comp=false;
		if(!c.waiting.equals("")){ j = c.Preference.indexOf(c.waiting); }
		else { j = c.Preference.size(); } //String r="";
		//for(int q=0;q<j;q++){ r=r+c.Preference.get(j); }
		//System.out.println(j+" "+c.Preference.size()+" "+c.Id+" "+c.Category+" "+c.PD);
			for(int q=0; q<j ; q++){
				i = find(c.Preference.get(q),"GE",false);
				//System.out.println(progs.get(i).code+" "+progs.get(i).num+" "+(progs.get(i).quota-progs.get(i).num));
								switch(c.Category){
												case "GE" : if(!c.PD){ 	if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;}; c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}
														  				}
														  else { 			if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}
																}
																break;

												case "OBC" :	if(!c.PD){ if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					 if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  				     if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					 if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}
														  				}
														  else { 			  if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					  if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					  if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					  if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}
																}
																break;
																
												case "SC" : if(!c.PD){
																			if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+2).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+2);comp=true; break;}
														  					if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}
														  					if(progs.get(i+6).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+6);comp=true; break;}	
																		}

															else{  			if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+2).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+2);comp=true; break;}
														  					if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+6).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+6);comp=true; break;}
														  					if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}


															}
															break;
															
												case "ST" : if(!c.PD){		//System.out.println("YO"); System.out.println(i);
																			if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+3).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+3);comp=true; break;}
														  					if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}
														  					if(progs.get(i+7).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+7);comp=true; break;}	
																		}

															else{  			if(progs.get(i).apply2()) {if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+3).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+3);comp=true; break;}
														  					if(progs.get(i+4).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+7).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+7);comp=true; break;}
														  					if(progs.get(i+1).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+5).apply2()){if(!c.waiting.equals("")){progs.get(c.previouswaitlist).num--;};c.waitchanger(q,i+5);comp=true; break;}


																}
															break;
											}
										if(comp==true) break;				 	
									}
		}
		
		public void display(){
			for(int i=0;i<candidatelist.candidates.size();i++){
				candidatelist.candidates.get(i).display();
			}
		}
}
