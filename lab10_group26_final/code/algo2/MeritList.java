package algo2;

import java.io.*;
import java.util.*;


public class MeritList {
	ArrayList<Candidate> candidates; // list of candidates
	ArrayList<Candidate> DScandidate;
	
	public MeritList(String filename1, String filename2){
		candidates = new ArrayList<Candidate>();
		DScandidate = new ArrayList<Candidate>();
		FileReader file1 = null; FileReader file2 = null;
		try{
			int z=0;
			file1 = new FileReader(filename1); // creating objects for reading data from file
			file2 = new FileReader(filename2);
			BufferedReader reader1 = new BufferedReader(file1);
			BufferedReader reader2 = new BufferedReader(file2);
		    String line1 = ""; String line2 = ""; // reading input from the file line by line
		    line1 = reader1.readLine(); line2 = reader2.readLine();
		    while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
		    	String[] temp1; String[] temp2; String[] temp3;
		    	String delimiter1=","; String delimiter2="_"; // delimiters used are "," and "_"
		    	temp1 = line1.split(delimiter1); // split function to split string as per the delimiter
		    	temp2 = line2.split(delimiter1);
		    	temp3 = temp1[temp1.length-1].split(delimiter2); // storing preferences of a candidate
		    	boolean nat,pd,ds; // boolean data members of candidate
		    	int[] rank; rank = new int[8]; // ranks of a candidate
		    	for(int i=0;i<4;i++){  // converting string to int
		    		rank[i]=Integer.parseInt(temp2[3+i]);
		    		rank[4+i]=Integer.parseInt(temp2[8+i]);
		    	}
		    	if(temp1[2].equals("Y")) pd=true; else pd=false; // setting the boolean variables
		    	if(temp1[1].equals("F")) nat=false; else nat=true;
		    	if(temp1[1].equals("DS")) ds=true; else ds=false;
		    	Candidate newone = new Candidate(temp1[0],temp1[1],pd,ds,nat,rank); // creating a candidate with full details
		    	for(int i=0;i<temp3.length;i++){ newone.addpreference(temp3[i]); }
		    	//newone.appliedfor=newone.Preference.get(0);
		    	//newone.next=newone.findnext();
		    	if(newone.DS==true){ 
					for(int j=1;j<8;j++){ newone.rank[j]=0; }
					//for(int j=0;j<8;j++){ System.out.println(newone.rank[j]); }
					DScandidate.add(newone);
				}
		    	else{
					if(newone.Category.equals("F")){
						if(newone.rank[0]!=0){
							newone.waiting=newone.Preference.get(0);
						}
						for(int j=0;j<8;j++){ newone.rank[j]=0; }
					}
					candidates.add(newone);
				}
		    }
		    reader1.close(); reader2.close();
		  }
		// leftover security for reading input from file
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
