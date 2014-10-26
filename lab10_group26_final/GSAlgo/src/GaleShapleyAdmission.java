import java.util.*;
import java.io.*;

public class GaleShapleyAdmission {
	
	private MeritList implist;
	private ArrayList<VirtualProgramme> proglist;
	private ArrayList<Candidate> studlist;
	private ArrayList<Candidate> DSc;
	private HashMap<String,Institute> DSquota;
	private HashMap<String,int[]> ranklist;
	
	public GaleShapleyAdmission(String f1,String f2,String f3){
		proglist = new ArrayList<VirtualProgramme>();
		DSquota = new HashMap<String,Institute>();
		studlist = new ArrayList<Candidate>();
		DSc = new ArrayList<Candidate>();
		this.fillprogs(f1);
		this.fillranklist(f3);
		this.fillcandidatedata(f2);
		this.GSDSc();
		for(int i=0;i<DSc.size();i++){
			if(DSc.get(i).retwaitlisted().equals("-1")){
				DSc.get(i).setcurrent(DSc.get(i).retpreference().get(0));
				int j=0;
				for(int k=7;k>0;k--){ if(ranklist.get(DSc.get(i).retID())[k]!=0){ j=k; break; } }
				j = (j>4)?(j-4):j; DSc.get(i).setcategory(j); DSc.get(i).setPDstatus(j>4);
				implist.addcandidate(DSc.get(i), ranklist.get(DSc.get(i).retID()));
			}
		}
		this.setmeritprogs();
		this.phase1();
		for(int i=0;i<implist.retfinlist().size();i++){
			Candidate x = implist.retfinlist().get(i);
			if(x.retwaitlisted().equals("-1") && ranklist.get(x.retID())[0]!=0 ){ 
				x.setp2pref();
				x.setcurrent(x.retp2pref().get(0));
				x.setphase2(true);
			}
			else { implist.removefin(i); }
		}
		this.implist.resetmerit(ranklist);
		this.setmeritprogs();
		this.phase2();
	}
	
	public void fillprogs(String f1){
		FileReader file1 = null;
		try{
			file1 = new FileReader(f1); // creating objects for reading data from file
			BufferedReader reader1 = new BufferedReader(file1);
		    String line1 = "";  // reading input from the file line by line
		    line1 = reader1.readLine(); 
		    while ((line1 = reader1.readLine()) != null) {
		    	String[] temp1; 
		    	String delimiter1=",";  // delimiters used are "," and "_"
		    	temp1 = line1.split(delimiter1); // split function to split string as per the delimiter
		    	for(int i=0;i<4;i++){ 
		    		proglist.add(new VirtualProgramme(temp1[1],i,false,Integer.parseInt(temp1[3+i])));
		    	}
		    	for(int i=4;i<8;i++){
		    		proglist.add(new VirtualProgramme(temp1[1],i-4,true,Integer.parseInt(temp1[3+i])));
		    	}
		    	String institute = temp1[1].substring(0, 1);
		    	if(DSquota.get(institute)==null){ DSquota.put(institute,new Institute()); }
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
				String id = temp1[0];
				int[] t = new int[8]; 
				for(int i=0;i<8;i++){ 
					t[i]=(i<4)?Integer.parseInt(temp1[3+i]):Integer.parseInt(temp1[4+i]);  
				}
				ranklist.put(id, t);
			}
			reader.close();
		}
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
				Candidate x = new Candidate(temp1[0],temp1[2],temp1[1]);
				for(int i=0;i<temp2.length;i++) x.add_preference(ranklist.get(temp1[0]), temp2[i]);
				x.setcurrent(x.retpreference().get(0));
				if(x.retcategory()==9 && ranklist.get(x.retID())[0]!=0){
					x.setwaitlisted(x.retpreference().get(0).code);
				}
				else if(x.retcategory()==8 && ranklist.get(x.retID())[0]!=0){ DSc.add(x); }
				else if(x.retcategory()==8 && ranklist.get(x.retID())[0]==0){
					int j=0;
					for(int i=7;i>0;i--){ if(ranklist.get(x.retID())[i]!=0){ j=i; break; } }
					j = (j>4)?(j-4):j; x.setcategory(j); x.setPDstatus(j>4);
					implist.addcandidate(x, ranklist.get(x.retID()));
				}
				else { implist.addcandidate(x, ranklist.get(x.retID())); }
				studlist.add(x);
			}
			reader1.close();
		}
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
	
	public void GSDSc(){
		Boolean complete=false;
		while(!complete){
			complete=true;
			for(int i=0;i<DSc.size();i++){
				if(DSc.get(i).retwaitlisted().equals("-1") && !DSc.get(i).retcurrent().code.equals("-1")){
					DSquota.get(DSc.get(i).retcurrent().code.substring(0,1)).apply(DSc.get(i),ranklist.get(DSc.get(i).retID())[0]);
					complete = false;
				}
			}
			Collection<Institute> c = DSquota.values();
			Iterator<Institute> itr = c.iterator();
			while(itr.hasNext()){
				itr.next().filter();
				/*for(int k=0;k<temp.size();k++){
					int x = DSc.indexOf(temp.get(k));
					DSc.get(x).setcurrent(DSc.get(x).findnext());
				}*/
			}
		}
	}
	
	public void setmeritprogs(){
		for(int i=0;i<proglist.size();i++){ proglist.get(i).setmerit(implist); }
	}
	
	public int progindex(Candidate c){
		for(int i=0;i<8;i++){
			if(c.retcurrent().code.equals(proglist.get(i).retcourse())){
				if(c.retcurrent().category==proglist.get(i).retCategory()){
					if(c.retcurrent().PDstatus==proglist.get(i).retPdstatus()){ return i; } 
				}
			}
		}
		return -1;
	}
	
	public void phase1(){
		Boolean complete=false; int j;
		while(!complete){
			complete=true;
			for(int i=0;i<implist.retfinlist().size();i++){
				if(implist.retfinlist().get(i).retwaitlisted().equals("-1") && !implist.retfinlist().get(i).retcurrent().code.equals("-1")){
					j = progindex(implist.retfinlist().get(i));
					proglist.get(j).apply(implist.retfinlist().get(i));
					complete = false;
				}
			}
			for(int i=0;i<proglist.size();i++){
				proglist.get(i).filter();
			}
		}
	}
	
	public void phase2(){
		Boolean complete=false; int j;
		while(!complete){
			complete=true;
			for(int i=0;i<implist.retfinlist().size();i++){
				if(implist.retfinlist().get(i).retwaitlisted().equals("-1") && !implist.retfinlist().get(i).retcurrent().code.equals("-1")){
					j = progindex(implist.retfinlist().get(i));
					proglist.get(j).apply(implist.retfinlist().get(i));
					complete = false;
				}
			}
			for(int i=0;i<proglist.size();i++){
				proglist.get(i).filter();
			}
		}
	}
	
}
			
