import java.io.*;
import java.util.*;

public class MeritList {
	ArrayList<Candidate> candidates;
	
	public MeritList(String filename1, String filename2){
		candidates = new ArrayList<Candidate>();
		FileReader file1 = null; FileReader file2 = null;
		try{
			file1 = new FileReader(filename1);
			file2 = new FileReader(filename2);
			BufferedReader reader1 = new BufferedReader(file1);
			BufferedReader reader2 = new BufferedReader(file2);
		    String line1 = ""; String line2 = "";
		    line1 = reader1.readLine(); line2 = reader2.readLine();
		    while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
		    	String[] temp1; String[] temp2; String[] temp3;
		    	String delimiter1=","; String delimiter2="_";
		    	temp1 = line1.split(delimiter1); 
		    	temp2 = line2.split(delimiter1);
		    	temp3 = temp1[temp1.length-1].split(delimiter2);
		    	boolean nat,pd,ds;
		    	int[] rank; rank = new int[8];
		    	for(int i=0;i<4;i++){ 
		    		rank[i]=Integer.parseInt(temp2[3+i]);
		    		rank[4+i]=Integer.parseInt(temp2[8+i]);
		    	}
		    	if(temp1[2]=="Y") pd=true; else pd=false;
		    	if(temp1[1]=="F") nat=false; else nat=true;
		    	if(temp1[1]=="DS") ds=true; else ds=false;
		    	Candidate newone = new Candidate(temp1[0],temp1[1],pd,ds,nat,rank);
		    	for(int i=0;i<temp3.length;i++){ newone.addpreference(temp3[i]); }
		    	//newone.appliedfor=newone.Preference.get(0);
		    	//newone.next=newone.findnext();
		    	candidates.add(newone);
		    }
		  }
		
		catch(Exception e){ throw new RuntimeException(e); }
		
		finally {
		    if (file1 != null && file2 !=null ) {
		      try {
		        file1.close(); file2.close();
		      } 
		      catch (IOException e) { }
		    }
		}
	}
}
