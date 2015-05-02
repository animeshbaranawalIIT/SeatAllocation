package meritorder;

import java.util.*;
import java.io.*;
import common.*;

/**
 * Class for implementing MeritOrderAdmission
 * 
 * @author Group26:JynXD!
 */
public class MeritOrderAdmission{
	
	private ArrayList <VirtualProgramme> progs; /**< List of Virtual Programmes */ 
	private ArrayList <Candidate>candidatelist; /**< List of candidates */
	private ArrayList <Candidate> DSc; /**< List of DS candidates */
	private ArrayList <Candidate> Foreign; /**< List of Foreign candidates */
	private MeritList mylist; /**< Appended Merit List */
	private HashMap<String,Institute> DSQuota; /**< HashMap of Institutes */
	private HashMap<String,int[]> ranklist; /**< Rank of candidates */
	
	/**
	 * Constructor of the class taking three filenames as parameters
	 * @param f1 Filename 1
	 * @param f2 Filename 2
	 * @param f3 Filename 3
	 * All arraylists, hashmaps intitialised to empty
	 */
	public MeritOrderAdmission(String f1, String f2, String f3) { 
		progs = new ArrayList<VirtualProgramme>(); //< initialising data members
		candidatelist = new ArrayList<Candidate>();
		DSc = new ArrayList<Candidate>() ;
		Foreign = new ArrayList<Candidate>();
		ranklist = new HashMap<String,int[]>();
		mylist = new MeritList();
		DSQuota = new HashMap<String,Institute>();
		this.fillprogs(f1); //< filling program details
		this.fillranklist(f3); //< filling ranklist details 
		mylist.setrank(ranklist); 
		this.fillcandidate(f2); //< filling candidate details
		Collections.sort(DSc,new ldids()); //< sorting DS candidate list  as per GE rank
		this.checkfds(); //< Distributing seats to DS candidates in DS list
		for(int i=0;i<DSc.size();i++){
			//< if DS candidate in DS list does not get a DS seat
			//< fights for a seat now as a category student
			if(DSc.get(i).retwaitlisted().equals("-1")){
				int j=0;
				for(int k=7;k>0;k--){ if(ranklist.get(DSc.get(i).retID())[k]!=0){ j=k; break; } }
				//< category and PDstatus modified as per his ranks
				DSc.get(i).setPDstatus(j>3);
				j = (j>3)?(j-4):j; DSc.get(i).setcategory(j); 
				//< candidate added to merit list
				mylist.addcandidate(DSc.get(i));
			}
		} //< the final merit list has now been set
		mylist.sortbyrank(); //< sorting the final merit lists as per category ranks
		this.phase1(); //< undertake phase1 
		for(int i=0;i<progs.size();i=i+8){  //< moving all the leftover seats during dereservation
			progs.get(i).incquota(progs.get(i+1).leftquota()+progs.get(i+4).leftquota()+progs.get(i+5).leftquota()); //< move seats from OBC-PD,GE-PD,OBC to GE
			progs.get(i+2).incquota(progs.get(i+6).leftquota()); //< move seats from SC-PD to SC
			progs.get(i+3).incquota(progs.get(i+7).leftquota()); //< move seats from ST-PD to ST
			progs.get(i+1).quotanull(); progs.get(i+4).quotanull(); progs.get(i+5).quotanull(); //< no seats left in OBC-PD,OBC,GE-PD
			progs.get(i+3).quotanull(); progs.get(i+7).quotanull(); //< no seats left in SC-PD,ST-PD
			for(int j=0;j<8;j++) progs.get(i+j).resetextrank(); //< reset quota extending rank of program
		}
		for(int i=0;i<mylist.retfinlist().size();i++){ //< Candidate with no allotted seat undergoes dereservation
			Candidate x = mylist.retfinlist().get(i);
			if(x.retwaitlisted().equals("-1")){ x.setcurrent(x.retprefmerit().get(0)); }
		}
		this.phase1(); //< Undertaking phase 2
		Collections.sort(Foreign,new ldids()); //< Sorting foreign candidates as per their GE rank
		this.allotforeign(); //< distribute seats to foreign candidates
	}
	
	/** 
	 * function for filling data in list of virtual programmes and hashmap of Institute<br>
	 * @param f1 Filename 1
	 * file is read line by line,
	 * delimiter used to split line into components<br>
	 * Virtual Programme called with its constructor and
	 * added to prog list <br>
	 * DS qouta also initialised via this function <br>
	 */
	public void fillprogs(String f1){
		FileReader file1 = null;
		try{
			file1 = new FileReader(f1); //< creating objects for reading data from file
			BufferedReader reader1 = new BufferedReader(file1);
		    String line1 = "";  //< reading input from the file line by line
		    line1 = reader1.readLine(); 
		    while ((line1 = reader1.readLine()) != null) {
		    	String[] temp1; 
		    	String delimiter1=",";  //< delimiters used are "," 
		    	temp1 = line1.split(delimiter1); //< split function to split string as per the delimiter
		    	for(int i=0;i<4;i++){ 
		    		progs.add(new VirtualProgramme(temp1[1],i,false,Integer.parseInt(temp1[3+i])));
		    	}
		    	for(int i=4;i<8;i++){
		    		progs.add(new VirtualProgramme(temp1[1],i-4,true,Integer.parseInt(temp1[3+i])));
		    	}
		    	String institute = temp1[1].substring(0, 1); //< Institute code determined by the first letter of the string code
		    	//< if institute not present in arraylist then add the institute
		    	if(DSQuota.get(institute)==null){ DSQuota.put(institute,new Institute()); }
		    }
		    reader1.close(); 
		  }
		//< leftover security for reading input from file
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
				String id = temp1[0]; //< unique ID of candidate
				int[] t = new int[8]; 
				for(int i=0;i<8;i++){ //< ranks of a candidate
					t[i]=(i<4)?Integer.parseInt(temp1[3+i]):Integer.parseInt(temp1[4+i]);
				}
				ranklist.put(id, t); //< insert in the hashmap
			}
			reader.close();
		}
		//<. leftover security for reading input from file
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
	
	
	/**
	 * function for filling data in the candidate list
	 * @param f2 Filename2
	 * file is read line by line,
	 * delimiter used to split line into components<br>
	 * Candidate called with its constructor and
	 * added to studlist <br>
	 * Depending on category and rank also added to meritlist and DS list <br>
	 */
	 public void fillcandidate(String filename1){
		FileReader file1 = null; 
		try{
			file1 = new FileReader(filename1); //< creating objects for reading data from file
			BufferedReader reader1 = new BufferedReader(file1);
			String line1 = ""; //< reading input from the file line by line
			line1 = reader1.readLine(); 
			while ((line1 = reader1.readLine()) != null ) {
				String[] temp1; String[] temp3;
				String delimiter1=","; String delimiter2="_"; //< delimiters used are "," and "_"
				temp1 = line1.split(delimiter1); //< split function to split string as per the delimiter
				temp3 = temp1[temp1.length-1].split(delimiter2); //< storing preferences of a candidate
				Candidate newone = new Candidate(temp1[0],temp1[2],temp1[1]); //< creating a candidate with full details
				for(int i=0;i<temp3.length;i++){ newone.addprefmerit(ranklist.get(temp1[0]),temp3[i]); }
				newone.setcurrent(newone.retprefmerit().get(0)); //< set current data member of candidate
				if(newone.retDSstatus()==true){ 
					if(ranklist.get(newone.retID())[0]!=0) DSc.add(newone); //< if DS candidate with non-zero GE rank, added to DS list
					else{ //< else like any other category candidate and added to merit list
						int j=0;
						for(int i=7;i>0;i--){ if(ranklist.get(newone.retID())[i]!=0){ j=i; break; } }
						j = (j>3)?(j-4):j; newone.setcategory(j); newone.setPDstatus(j>3);
						mylist.addcandidate(newone);
					}
				}
				else if(newone.retcategory()==9){ //< if foreign candidate with non-zero GE rank, added to foreign list 
						if(ranklist.get(newone.retID())[0]!=0){
							Foreign.add(newone);
						} //< otherwise no program allotted to foreign candidate
				}
				else{ mylist.addcandidate(newone); } //< else added to merit list
				candidatelist.add(newone);
			}
			reader1.close(); 
		}
		//< leftover security for reading input from file
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
	  * Function to find a program in the list of programs 
	  * @param c Candidate whose program is to be found
	  * @return returns index of program in the list
	  */
	public int progindex(Candidate c){
		for(int i=0;i<progs.size();i++){
			if(c.retcurrent().code.equals(progs.get(i).retcourse())){
				if(c.retcurrent().category==progs.get(i).retCategory()){
					if(c.retcurrent().PDstatus==progs.get(i).retPdstatus()){ return i; } 
				}
			}
		}
		return -1;
	}
	
	class ldids implements Comparator<Candidate>{ //< comparator for comparing two candidates from ge merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(ranklist.get(e1.retID())[0],ranklist.get(e2.retID())[0]);
	    }
	}
	
	/**
	 * Allot seats to DS candidates based on their GE rank
	 */
	public void checkfds(){
		for(int i=0;i<DSc.size();i++){
				Candidate x = DSc.get(i);
				while(!x.retcurrent().code.equals("-1")){
					Institute y = DSQuota.get(x.retcurrent().code.substring(0,1));
					if(y.applymerit(x , ranklist.get(x.retID())[0])){ break; }
				}
		}
	}
	
	/**
	 * Distribute seats traversing up to down in the merit lists
	 */
	public void phase1(){
		for(int i=0;i<8;i++){
			ArrayList<Candidate> ml = mylist.retmerit(i);
			for(int j=0;j<ml.size();j++){
				Candidate x = ml.get(j); //< Candidate a of merit list
				while(!x.retcurrent().code.equals("-1") && x.retwaitlisted().equals("-1")){ //< if preference list not exhausted and no waitlisted program
					VirtualProgramme y = progs.get(progindex(x));
					int k = y.retCategory() + 4*( (y.retPdstatus())?1:0 ) ;
					if(y.applymerit(x,ranklist.get(x.retID())[k])){ break; } //< apply with rank as per category of the program  
				}
			}
		}
	}
	
	
	/**
	 * Allots seats to foreign candidates <br>
	 * If rank of foreign candidate good enough to substitute another candidate
	 * then he gets a seat <br>
	 * No actual substitution takes place <br>
	 */
	public void allotforeign(){
		for(int i=0;i<Foreign.size();i++){
			Candidate x = Foreign.get(i);
			while(!x.retcurrent().code.equals("-1")){ //< if preference list not exhausted
				VirtualProgramme y = progs.get(progindex(x)); 
				if(y.leftquota()>0){  //< if seats left then foreign candidate gets a seat
					x.setwaitlisted(y.retcourse()); 
					break;
				}
				else{ //< rank of foreign candidate good enough then he gets a seat
					if(ranklist.get(x.retID())[0]<=y.retdecrank()){ x.setwaitlisted(y.retcourse()); break; }
					else{ x.setcurrent(x.findnextmerit()); }
				}
			}
		}
	}
	
	public void display(){
		System.out.println("CandidateUniqueID,ProgrammeID");
	for(int i=0;i<candidatelist.size();i++){
		
			Candidate x = candidatelist.get(i);
			System.out.println(x.retID()+","+x.retwaitlisted());
		}  }
}
