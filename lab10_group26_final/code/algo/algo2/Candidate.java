package algo2;

import java.util.*;


public class Candidate {
	String Id; // ID of the student
	String Category; // Category of the student
	Boolean PD; // whether the student is physically disabled
	Boolean DS; // Is the student of DS category ?
	Boolean Nationality; // Is the student a foreigner?
	ArrayList<String> Preference; // Preference list of the student
	//String appliedfor; // Programme upto which applied;
	//String next; // Next programme for application
	String waiting; // Programme for which waitlisted
	int previouswaitlist;
	int[] rank; // ranks of the candidate
	
	public void changeID(String ID){ this.Id=ID; } //Manipulating Data Members
	public void changeCategory(String cat){ this.Category=cat; } //Manipulating Data Members
	public void changePD(Boolean pd){ this.PD=pd; } //Manipulating Data Members
	public void changeDS(Boolean ds){ this.DS=ds; } //Manipulating Data Members
	public void changenationality(Boolean n){ this.Nationality=n; } //Manipulating Data Members
	
	public Candidate(String name,String cat,Boolean a,Boolean b,Boolean c,int[] values){ //Initialising Constructor
		Id = name;
		Category = cat;
		PD = a; DS = b; Nationality = c;
		Preference = new ArrayList<String>();
		rank = new int[8];
		for(int i=0;i<8;i++){ rank[i] = values[i]; }
		waiting=""; //next=""; appliedfor="";
		previouswaitlist=-1;
	}
	
	public void addpreference(String ProgCODE){ // Adding Programmes to Preference List
		Preference.add(ProgCODE);
	}
	//public void setapplied(String ProgCODE){ this.appliedfor=ProgCODE; } //Manipulating Data Members
	//public void setnext(String ProgCODE){ this.next=ProgCODE; } //Manipulating Data Members
	public void setwaitlisted(String ProgCODE){ this.waiting=ProgCODE; } //Manipulating Data Members
	/*public String findnext(){ //Find next programme to which candidate should apply
		int ind = this.Preference.indexOf(this.appliedfor)+1;
		if(ind == this.Preference.size()) return "NULL"; //Preference list exhausted
		else return this.Preference.get(ind);
	}*/

	public void waitchanger(/*Candidate c,*/ int q, int i){ this.waiting=this.Preference.get(q); this.previouswaitlist=i;}

	public void display(){ 
		if(waiting.equals("")) System.out.println(Id+",-1");
		else System.out.println(Id+","+waiting/*+","+r*/);
	}
}
