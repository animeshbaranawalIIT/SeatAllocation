package galeshapley;
import java.util.*;
import java.io.*;
import common.*;
/** 
 * Main class for implementing the algorithm 
 * 
 * @author Group26:JynXD!
 */
public class GaleShapleyAdmission {
	
	private MeritList implist; /**< MeritList data member */
	private ArrayList<VirtualProgramme> proglist; /**< ArrayList of Virtual Programmes */
	private ArrayList<Candidate> studlist; /**< ArrayList of students */ 
	private ArrayList<Candidate> DSc; /**< ArrayList of DS students */
	private ArrayList<Candidate> Foreign; /**< ArrayList of Foreign students */ 
	private HashMap<String,Institute> DSquota; /**< HashMap of institute ( for distributing DS seats to DS students ) */
	private HashMap<String,int[]> ranklist; /**< Hashmap of ranklist of each student */
	/**
	 * The constructor of this class takes three filenames as inputs <br> 
	 * All the arraylists and hashmaps are initialised to new()
	 * @param f1 Filename1
	 * @param f2 Filename2
	 * @param f3 Filename2
	 * The entire algorithm runs in the constructor
	 */
	public GaleShapleyAdmission(String f1,String f2,String f3){
		implist = new MeritList();
		ranklist = new HashMap<String,int[]>();
		proglist = new ArrayList<VirtualProgramme>();
		DSquota = new HashMap<String,Institute>();
		studlist = new ArrayList<Candidate>();
		DSc = new ArrayList<Candidate>();
		Foreign = new ArrayList<Candidate>();
		this.fillprogs(f1); //< fills the data in the list of virtual programmes
		this.fillranklist(f3); //< fills the ranks of all the candidates
		this.fillcandidatedata(f2); //< fills the data in the candidate list
		this.GSDSc(); //< allots DS seats to DS candidates in the DS list
		for(int i=0;i<DSc.size();i++){
			//< if DS candidate in DS list does not get a DS seat
			//< fights for a seat now as a category student
			if(DSc.get(i).retwaitlisted().equals("-1")){
				DSc.get(i).setcurrent(DSc.get(i).retpreference().get(0)); //< current reset to initial
				int j=0;
				for(int k=7;k>0;k--){ if(ranklist.get(DSc.get(i).retID())[k]!=0){ j=k; break; } }
				//< category and PDstatus modified as per his ranks
				DSc.get(i).setPDstatus(j>3);
				j = (j>3)?(j-4):j; DSc.get(i).setcategory(j); 
				//< candidate added to merit list
				implist.addcandidate(DSc.get(i), ranklist.get(DSc.get(i).retID()));
			}
		} //< the final merit list has now been set
		this.setmeritprogs(); //< distribute the merit list to the programs as per their category and PDstatus
		this.phase1(); //< undertake phase1 of algorithm
		this.allotforeign(); //< allot dereserved and GE seats to Foreign candidates
		for(int i=0;i<studlist.size();i++){
			Candidate x = studlist.get(i);
			System.out.println(x.retID()+","+x.retwaitlisted()+"("+x.retcurrent().category+"/"+x.retcurrent().PDstatus+")");
		}
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
		    		proglist.add(new VirtualProgramme(temp1[1],i,false,Integer.parseInt(temp1[3+i])));
		    	}
		    	for(int i=4;i<8;i++){
		    		proglist.add(new VirtualProgramme(temp1[1],i-4,true,Integer.parseInt(temp1[3+i])));
		    	}
		    	String institute = temp1[1].substring(0, 1); //< Institute code determined by the first letter of the string code
		    	//< if institute not present in arraylist then add the institute
		    	if(DSquota.get(institute)==null){ DSquota.put(institute,new Institute()); }
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
	 * function to feed the rank of all candidates in the hashmap<br>
	 * @param f3 Filename3
	 * file is read line by line,<br>
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
	 public void fillcandidatedata(String f2){
		FileReader file2 = null;
		try{
			file2 = new FileReader(f2);  
			BufferedReader reader1 = new BufferedReader(file2);
			String line1=reader1.readLine(); 
			while((line1=reader1.readLine())!=null){
				String[] temp1; String[] temp2;
				String del1=","; String del2="_";
				temp1 = line1.split(del1); 
				temp2 = temp1[temp1.length-1].split(del2);
				Candidate x = new Candidate(temp1[0],temp1[2],temp1[1]); //< creating a candidate with default needed parameters
				for(int i=0;i<temp2.length;i++) x.addpreference(ranklist.get(temp1[0]), temp2[i]); //< initialise preference list of candidate
				x.setcurrent(x.retpreference().get(0)); //< set current data member of candidate to his first choice in preference list
				//< if candidate is of foreign category and has general rank, inserted into list of foreign candidates
				if(x.retcategory()==9 && ranklist.get(x.retID())[0]!=0){
					Foreign.add(x); 
				}
				//< if candidate is foreign with no GE rank, no seat is allotted to him
				else if(x.retcategory()==9){}
				
				//< if candidate is of DS with a GE rank, he is inserted in DS candidate list
				else if(x.retcategory()==8 && ranklist.get(x.retID())[0]!=0){ DSc.add(x); }
				
				//< if candidate is of DS with no GE rank, he cannot apply for DS
				//< he is like any other category student
				//< category and PDstatus of student modified as per his rank in ranklist
				//< student added to merit list
				else if(x.retcategory()==8 && ranklist.get(x.retID())[0]==0){
					int j=0;
					for(int i=7;i>0;i--){ if(ranklist.get(x.retID())[i]!=0){ j=i; break; } }
					j = (j>3)?(j-4):j; x.setcategory(j); x.setPDstatus(j>3);
					implist.addcandidate(x, ranklist.get(x.retID()));
				}
				
				//< else the student added to merit list
				else { implist.addcandidate(x, ranklist.get(x.retID()));  }
				
				//< every student added to the main list of candidates
				studlist.add(x);
			}
			reader1.close();
		}
		//< leftover security for reading input from file
		catch(Exception e){ throw new RuntimeException(e); }
		
		finally {
		    if (file2 != null) {
		      try {
		        file2.close();
		      } 
		      catch (IOException e) { }
		    }
		}
	}
	
	/** 
	 * this function allots DS seats to candidates which are in DS list<br>
	 * Candidate checked if he has been waitlisted for some program and 
	 * whether his preference list been exhausted<br>
	 * If none applicable for any candidate then candidate applies to next program on his
	 * preference list <br>
	 * if this is applicable for every candidate algorithm terminates
	 */
	public void GSDSc(){
		Boolean complete=false;
		//< main loop for algorithm of Gale Shapley allocation
		while(!complete){
			complete=true;
			for(int i=0;i<DSc.size();i++){
				//< if any candidate found which has not been allotted any prog
				//< and whose preference list has not exhausted
				if(DSc.get(i).retwaitlisted().equals("-1") && !DSc.get(i).retcurrent().code.equals("-1")){
					//< apply to the program
					DSquota.get(DSc.get(i).retcurrent().code.substring(0,1)).apply(DSc.get(i),ranklist.get(DSc.get(i).retID())[0]);
					complete = false;
				}
			}
			Collection<Institute> c = DSquota.values();
			Iterator<Institute> itr = c.iterator();
			while(itr.hasNext()){
				itr.next().filter(); //< filtering the applications by all the programs
			}
		}
	}
	
	/** 
	 * function to distribute merit list to the programmes
	 */
	public void setmeritprogs(){
		for(int i=0;i<proglist.size();i++){ proglist.get(i).setmerit(implist); }
	}
	
	/**
	 * function to get index of program to which candidate will apply
	 * in the list of programs based on the category,code,PDstatus of current of candidate<br>
	 * @param c Candidate for which index of program is to be found
	 * @return returns the index of program in program list  
	 */
	public int progindex(Candidate c){
		for(int i=0;i<proglist.size();i++){
			if(c.retcurrent().code.equals(proglist.get(i).retcourse())){
				if(c.retcurrent().category==proglist.get(i).retCategory()){
					if(c.retcurrent().PDstatus==proglist.get(i).retPdstatus()){ return i; } 
				}
			}
		}
		return -1;
	}
	
	/** 
	 * this function allots seats to candidates which are in merit list<br>
	 * Candidate checked if he has been waitlisted for some program and 
	 * whether his preference list been exhausted<br>
	 * If none applicable for any candidate then candidate applies to next program on his
	 * preference list <br>
	 * if this is applicable for every candidate algorithm terminates
	 */
	public void phase1(){
		Boolean complete=false; int j;
		//< main loop for Gale Shapley algorithm
		while(!complete){
			complete=true;
			for(int i=0;i<implist.retfinlist().size();i++){
				//< if any candidate found which has not been allotted any prog
				//< and whose preference list has not exhausted
				if(implist.retfinlist().get(i).retwaitlisted().equals("-1") && !implist.retfinlist().get(i).retcurrent().code.equals("-1")){
					//< find index of the program to which to apply
					j = progindex(implist.retfinlist().get(i));
					//< apply to the program
					proglist.get(j).apply(implist.retfinlist().get(i));
					complete = false;
				}
			}
			for(int i=0;i<proglist.size();i++){
				proglist.get(i).filter(); //< filtering applicants by each programme
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
			VirtualProgramme y = proglist.get(progindex(x));
			Boolean complete=true;
			while(!x.retcurrent().code.equals("-1") && complete){
				if(y.retCategory()==0 && y.retPdstatus()==false){ //< if program is of GE category
					if(y.retWaitlisted1().size()<y.retquota()){ x.setwaitlisted(y.retcourse()); complete=false; } //< check whether seats left
					else if(ranklist.get(x.retID())[0]<=ranklist.get(y.retWaitlisted1().get(y.retWaitlisted1().size()-1).retID())[0]){
						x.setwaitlisted(y.retcourse()); complete=false; //< if no seats left check whether rank of foreign candidate good enough to replace any candidate
					}
					else{ x.setcurrent(x.findnext()); } //< else go to next preference
				}
				else{
					if(y.retquotaleft()>0){ //< if program not of GE category and dereserved seats present
						if(y.retWaitlisted2().size()<y.retquotaleft()){ x.setwaitlisted(y.retcourse()); complete=false; } //< check whether dereserved seats left
						else if(ranklist.get(x.retID())[0]<=ranklist.get(y.retWaitlisted2().get(y.retWaitlisted2().size()-1).retID())[0]){
							x.setwaitlisted(y.retcourse()); complete=false; //< if no seats left check whether rank of foreign candidate good enough to replace any candidate
						}
						else{ x.setcurrent(x.findnext()); }
					}
					else{ x.setcurrent(x.findnext()); } //< else go to next preference
				}
				y = proglist.get(progindex(x));
			}
		}
	}
	
}
			
