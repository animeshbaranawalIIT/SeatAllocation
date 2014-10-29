package meritorder;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import common.*;

public class MeritOrderAdmission{
	
	class DSq{
		String Insti_code;
		int seats;
		
		public DSq(String code){ Insti_code=code; seats=2; }
	}
	
	private ArrayList <VirtualProgramme> progs; // Virtual Programmes 
	private ArrayList <Candidate>candidatelist; // List of candidates
	private ArrayList <Candidate>DSc; // List of DS candidates
	private MeritList mylist; // Appended Merit List for Algo 2
	private HashMap<String,Institute> DSQuota; 
	private HashMap<String,int[]> ranklist;
	
	public void fillprogs(String f1){
		FileReader file1 = null;
		try{
			file1 = new FileReader(f1); /// creating objects for reading data from file
			BufferedReader reader1 = new BufferedReader(file1);
		    String line1 = "";  /// reading input from the file line by line
		    line1 = reader1.readLine(); 
		    while ((line1 = reader1.readLine()) != null) {
		    	String[] temp1; 
		    	String delimiter1=",";  /// delimiters used are "," 
		    	temp1 = line1.split(delimiter1); /// split function to split string as per the delimiter
		    	for(int i=0;i<4;i++){ 
		    		progs.add(new VirtualProgramme(temp1[1],i,false,Integer.parseInt(temp1[3+i])));
		    	}
		    	for(int i=4;i<8;i++){
		    		progs.add(new VirtualProgramme(temp1[1],i-4,true,Integer.parseInt(temp1[3+i])));
		    	}
		    	String institute = temp1[1].substring(0, 1); /// Institute code determined by the first letter of the string code
		    	/// if institute not present in arraylist then add the institute
		    	if(DSQuota.get(institute)==null){ DSQuota.put(institute,new Institute()); }
		    }
		    reader1.close(); 
		  }
		/// leftover security for reading input from file
		catch(Exception e){ throw new RuntimeException(e); }
		
		finally {
		    if (file1 != null) {
		      try {
		        file1.close();
		      } 
		      catch (IOException e) { }
		    }
		}
	}
	
	/**
	 * function to feed the rank of all candidates in the hashmap
	 * @param f3 Filename3
	 * file is read line by line,
	 * delimiter used to split line into components<br>
	 * Candidate ID, ranks added to ranklist
	 */
	public void fillranklist(String f3){
		FileReader file = null;
		try{
			file = new FileReader(f3);  
			BufferedReader reader = new BufferedReader(file);
			String line=reader.readLine(); 
			while((line=reader.readLine())!=null){
				String[] temp1; 
				String del1=","; 
				temp1 = line.split(del1);
				String id = temp1[0]; // unique ID of candidate
				int[] t = new int[8]; 
				for(int i=0;i<8;i++){ // ranks of a candidate
					t[i]=(i<4)?Integer.parseInt(temp1[3+i]):Integer.parseInt(temp1[4+i]);
				}
				ranklist.put(id, t); // insert in the hashmap
			}
			reader.close();
		}
		//. leftover security for reading input from file
		catch(Exception e){ throw new RuntimeException(e); }
		
		finally {
		    if (file != null) {
		      try {
		        file.close();
		      } 
		      catch (IOException e) { }
		    }
		}
	}
	
	public int find(String cd, int cat, Boolean pd){ // function to find the index of programme in prog list given its code, category and pd satus
		for(int j=0;j<progs.size();j++){
			if(progs.get(j).retcourse().equals(cd)){
				if(progs.get(j).retCategory()==cat){
					if(progs.get(j).retPdstatus().equals(pd)){ return j; }
				}
			}
		}
		return -1;
	}
	
	class ldids implements Comparator<Candidate>{ // comparator for comparing two candidates from ge merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(ranklist.get(e1.retID())[0],ranklist.get(e2.retID())[0]);
	    }
	}
	
	class ldisort implements Comparator<Candidate>{
		public int compare(Candidate e1, Candidate e2) {
			return (e1.retID()).compareTo(e2.retID());
		}
	}
	
	public void checkfds(){
		int k;
		for(int i=0;i<DSc.size();i++){
				for(int j=0;j<DSc.get(i).retcodepref().size();j++){
					Institute x = DSQuota.get(DSc.get(i).retcodepref().get(j).substring(0,1));
					if(x.retseats()>0){
						DSc.get(i).setwaitlisted(DSc.get(i).retcodepref().get(j)); 
						x.decrease(); 
						//DSc.get(i).rank[0]=0;
						break;
					}
				}
				//candidatelist.DScandidate.get(i).Category="GE";
				//for(int l=0;l<8;l++){ System.out.println(candidatelist.DScandidate.get(i).rank[l]); } 
				//Scandidatelist.candidates.add(candidatelist.DScandidate.get(i));
		}
	}
				
	
	public MeritOrderAdmission(String f1, String f2, String f3) { // file names as constructor parameters
		progs = new ArrayList<VirtualProgramme>(); // initialising data members
		candidatelist = new ArrayList<Candidate>();
		ranklist = new HashMap<String,int[]>();
		mylist = new MeritList();
		DSQuota = new HashMap<String,Institute>();
		this.fillprogs(f1);
		this.fillranklist(f3);
		mylist.setrank(ranklist);
		this.fillcaddate(f2);
		Collections.sort(DSc,new ldids());
		this.checkfds();
		for(int i=0;i<DSc.size();i++){
			// if DS candidate in DS list does not get a DS seat
			// fights for a seat now as a category student
			if(DSc.get(i).retwaitlisted().equals("-1")){
				int j=0;
				for(int k=7;k>0;k--){ if(ranklist.get(DSc.get(i).retID())[k]!=0){ j=k; break; } }
				// category and PDstatus modified as per his ranks
				DSc.get(i).setPDstatus(j>3);
				j = (j>3)?(j-4):j; DSc.get(i).setcategory(j); 
				// candidate added to merit list
				mylist.addcandidate(DSc.get(i));
			}
		} // the final merit list has now been set
		mylist.sortbyrank();
		for(int j=0;j<mylist.ge.size();j++) System.out.println(mylist.ge.get(j).Id);
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
		for(int j=0;j<mylist.ge.size();j++) phase2(mylist.ge.get(j),"GE",false);
		//for(int j=0;j<mylist.obc.size();j++) phase2(mylist.obc.get(j));
		for(int j=0;j<mylist.sc.size();j++) phase2(mylist.sc.get(j),"SC",false);
		for(int j=0;j<mylist.st.size();j++) phase2(mylist.st.get(j),"ST",false);
		//for(int j=0;j<mylist.ge_pd.size();j++) phase2(mylist.ge_pd.get(j));
		//for(int j=0;j<mylist.obc_pd.size();j++) phase2(mylist.obc_pd.get(j));
		//for(int j=0;j<mylist.sc_pd.size();j++) phase2(mylist.sc_pd.get(j));
		//for(int j=0;j<mylist.st_pd.size();j++) phase2(mylist.st_pd.get(j));
		Collections.sort(candidatelist.candidates,new ldisort());
	}
	
	public void fillcaddate(String filename1){
		FileReader file1 = null; 
		try{
			file1 = new FileReader(filename1); // creating objects for reading data from file
			BufferedReader reader1 = new BufferedReader(file1);
			String line1 = ""; // reading input from the file line by line
			line1 = reader1.readLine(); 
			while ((line1 = reader1.readLine()) != null ) {
				String[] temp1; String[] temp3;
				String delimiter1=","; String delimiter2="_"; // delimiters used are "," and "_"
				temp1 = line1.split(delimiter1); // split function to split string as per the delimiter
				temp3 = temp1[temp1.length-1].split(delimiter2); // storing preferences of a candidate
				Candidate newone = new Candidate(temp1[0],temp1[2],temp1[1]); // creating a candidate with full details
				for(int i=0;i<temp3.length;i++){ newone.add_preference(ranklist.get(temp1[0]),temp3[i]); }
				//newone.appliedfor=newone.Preference.get(0);
	    		//newone.next=newone.findnext();
				if(newone.retDSstatus()==true){ 
					//for(int j=0;j<8;j++){ System.out.println(newone.rank[j]); }
					if(ranklist.get(newone.retID())[0]!=0) DSc.add(newone);
					else{
						int j=0;
						for(int i=7;i>0;i--){ if(ranklist.get(newone.retID())[i]!=0){ j=i; break; } }
						j = (j>3)?(j-4):j; newone.setcategory(j); newone.setPDstatus(j>3);
						mylist.addcandidate(newone);
					}
				}
				else if(newone.retcategory()==9){
						if(ranklist.get(newone.retID())[0]!=0){
							newone.setwaitlisted(newone.retcodepref().get(0));
						}
						//for(int j=0;j<8;j++){ newone.rank[j]=0; }
					}
					//candidates.add(newone);
				else{ mylist.addcandidate(newone); }
				candidatelist.add(newone);
			}
			reader1.close(); 
		}
		// leftover security for reading input from file
		catch(Exception e){ throw new RuntimeException(e); }
	
		finally {
			if (file1 != null) {
				try {
					file1.close();
				} 
				catch (IOException e) { }
			}
		}
	}
	
	public Boolean apple(int i, Candidate c, String cat, Boolean pd, int j){
		String temp;
		if(c.retwaitlisted().equals("-1")){
			if(progs.get(i).applyalgo2(c,cat,pd)){ 
					System.out.println("App accepted");
					temp = c.Preference.get(j); // waitlisted program
					//System.out.println(temp);
					//if(c.waiting.equals("")){
					 progs.get(i).num++; c.waiting=temp; c.previouswaitlist=i;// } // assign waitlisted program if waitlist initially empty
					/*else{ 
						if(c.Preference.indexOf(temp)<c.Preference.indexOf(c.waiting)){ //update waitlist if waitlist initially not empty 
							progs.get(i).num++;
							progs.get(c.previouswaitlist).num--;
							c.previouswaitlist=i;
							c.waiting=temp; 
						}
						else {System.out.println("Found but not gud enough");} 
					}*/
				return true;
			}
			else{return false;}
		}
		else {return false;}
	}
	
	
	public void phase1(Candidate c,String cat,Boolean pd){ //implement phase 1 for a candidate
		int i; 
		for(int j=0;j<c.Preference.size();j++){ // searching over preference list of candidate
			Boolean got=false;
			if(cat.equals("GE")){                                  // when the GE merit list is running
				i=find(c.Preference.get(j),"GE",false); // finding index of GE virtual program of the students preference in the list

				switch(c.Category){
					case "GE": if(apple(i,c,cat,pd,j)){got = true;}break;

					case "OBC" : if(apple(i,c,cat,pd,j)){got = true;break;}       // first tries to get the programme through GE category
								 if(c.rank[1] != 0){if(apple(i+1,c,"OBC",pd,j)){got = true;}}break;     // makes sure that an OBC guy gets a seat through category if not by GE rank

					case "SC": if(apple(i,c,cat,pd,j)){got = true;break;}
								if(c.rank[2] != 0){if(apple(i+2,c,"SC",pd,j)){got = true;}}break;

					case "ST": if(apple(i,c,cat,pd,j)){got = true;break;}
								if(c.rank[3] != 0){if(apple(i+3,c,"SC",pd,j)){got = true;}}break;

					case "GE_PD" : if(apple(i,c,cat,pd,j)){got = true;break;}
								   if(c.rank[4] != 0){if(apple(i+4,c,"GE_PD",pd,j)){got = true;}}break;

					case "OBC_PD" : if(apple(i,c,cat,pd,j)){got = true;break;}
									if(c.rank[1] != 0){if(apple(i+1,c,"OBC",pd,j)){got = true;break;}}
									if(c.rank[5] != 0){if(apple(i+5,c,"OBC_PD",pd,j)){got = true;}}break;

					case "SC_PD":   if(apple(i,c,cat,pd,j)){got = true;break;}
									if(c.rank[2] != 0){if(apple(i+2,c,"SC",pd,j)){got = true;break;}}
									if(c.rank[6] != 0){if(apple(i+6,c,"SC_PD",pd,j)){got = true;}}break;

					case "ST_PD" : if(apple(i,c,cat,pd,j)){got = true;break;}
									if(c.rank[3] != 0){if(apple(i+3,c,"ST",pd,j)){got = true;break;}}
									if(c.rank[7] != 0){if(apple(i+7,c,"ST_PD",pd,j)){got = true;}}break;

				}
				
			}
			else{
				if(cat.equals("OBC") || cat.equals("ST") || cat.equals("SC")){           // when it is a OBC or SC or ST meritlist
					i=find(c.Preference.get(j),cat,false); 
					//System.out.println(i);
					int x=1;
					if(cat.equals("SC")){ x++;}
					if(cat.equals("ST")){ x += 2;}
					
					if(apple(i,c,cat,pd,j)){ got =true;}
					else{if(c.PD){if(c.rank[x+4] != 0){if(apple(i+4,c,cat+"_PD",pd,j)){ got =true;}}}
					}
					
				}

				else{											// for all the PD meritlists
					i=find(c.Preference.get(j),cat,pd);
					if(apple(i,c,cat,pd,j)){ got =true;}

				}
			}
			if(got){break;}
		}

	}
	  

	public void phase2(Candidate c, String cat, Boolean pd){                      // De- reservation
		int i,j; Boolean comp=false;
		if(c.waiting.equals("")){ j = c.Preference.size(); 
		//else { j = c.Preference.size(); } //String r="";
		//for(int q=0;q<j;q++){ r=r+c.Preference.get(j); }
		//System.out.println(j+" "+c.Preference.size()+" "+c.Id+" "+c.Category+" "+c.PD);
			for(int q=0; q<j ; q++){
				i = find(c.Preference.get(q),"GE",false);
				//System.out.println(progs.get(i).code+" "+progs.get(i).num+" "+(progs.get(i).quota-progs.get(i).num));
								switch(c.Category){
												case "GE" : if(!c.PD){ 	    if(progs.get(i).applyalgo(c,"GE")) {  c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+1).applyalgo(c,"GE")){ c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+4).applyalgo(c,"GE")){ c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+5).applyalgo(c,"GE")){ c.waitchanger(q,i+5);comp=true; break;}
														  				}
														  else { 			if(progs.get(i).applyalgo(c,"GE")) { c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+4).applyalgo(c,"GE")){ c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+1).applyalgo(c,"GE")){ c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+5).applyalgo(c,"GE")){ c.waitchanger(q,i+5);comp=true; break;}
																}
																break;

												case "OBC" :	if(!c.PD){ if(progs.get(i).applyalgo(c,"GE")) { c.waitchanger(q,i);comp=true; break;}
														  					 if(progs.get(i+1).applyalgo(c,"GE")){ c.waitchanger(q,i+1);comp=true; break;}
														  				     if(progs.get(i+4).applyalgo(c,"GE")){ c.waitchanger(q,i+4);comp=true; break;}
														  					 if(progs.get(i+5).applyalgo(c,"GE")){ c.waitchanger(q,i+5);comp=true; break;}
														  				}
														  else { 			  if(progs.get(i).applyalgo(c,"GE")) { c.waitchanger(q,i);comp=true; break;}
														  					  if(progs.get(i+4).applyalgo(c,"GE")){ c.waitchanger(q,i+4);comp=true; break;}
														  					  if(progs.get(i+1).applyalgo(c,"GE")){ c.waitchanger(q,i+1);comp=true; break;}
														  					  if(progs.get(i+5).applyalgo(c,"GE")){ c.waitchanger(q,i+5);comp=true; break;}
																}
																break;
																
												case "SC" : if(!c.PD){
																			if(progs.get(i).applyalgo(c,"SC")) { c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+2).applyalgo(c,"SC")){ c.waitchanger(q,i+2);comp=true; break;}
														  					if(progs.get(i+1).applyalgo(c,"SC")){ c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+4).applyalgo(c,"SC")){ c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+5).applyalgo(c,"SC")){ c.waitchanger(q,i+5);comp=true; break;}
														  					if(progs.get(i+6).applyalgo(c,"SC")){ c.waitchanger(q,i+6);comp=true; break;}	
																		}

															else{  			if(progs.get(i).applyalgo(c,"SC")) { c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+2).applyalgo(c,"SC")){ c.waitchanger(q,i+2);comp=true; break;}
														  					if(progs.get(i+4).applyalgo(c,"SC")){ c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+6).applyalgo(c,"SC")){ c.waitchanger(q,i+6);comp=true; break;}
														  					if(progs.get(i+1).applyalgo(c,"SC")){ c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+5).applyalgo(c,"SC")){ c.waitchanger(q,i+5);comp=true; break;}


															}
															break;
															
												case "ST" : if(!c.PD){		if(progs.get(i).applyalgo(c,"ST")) { c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+3).applyalgo(c,"ST")){ c.waitchanger(q,i+3);comp=true; break;}
														  					if(progs.get(i+1).applyalgo(c,"ST")){ c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+4).applyalgo(c,"ST")){ c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+5).applyalgo(c,"ST")){ c.waitchanger(q,i+5);comp=true; break;}
														  					if(progs.get(i+7).applyalgo(c,"ST")){ c.waitchanger(q,i+7);comp=true; break;}	
																		}

															else{  			if(progs.get(i).applyalgo(c,"ST")) { c.waitchanger(q,i);comp=true; break;}
														  					if(progs.get(i+3).applyalgo(c,"ST")){ c.waitchanger(q,i+3);comp=true; break;}
														  					if(progs.get(i+4).applyalgo(c,"ST")){ c.waitchanger(q,i+4);comp=true; break;}
														  					if(progs.get(i+7).applyalgo(c,"ST")){ c.waitchanger(q,i+7);comp=true; break;}
														  					if(progs.get(i+1).applyalgo(c,"ST")){ c.waitchanger(q,i+1);comp=true; break;}
														  					if(progs.get(i+5).applyalgo(c,"ST")){ c.waitchanger(q,i+5);comp=true; break;}


																}
															break;
											}
										if(comp==true) break;				 	
									}
		}
	}
	
		public void display(){
			for(int i=0;i<candidatelist.candidates.size();i++){
				candidatelist.candidates.get(i).display();
			}
		}
}
