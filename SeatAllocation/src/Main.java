import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
	   /*MeritList test = new MeritList("choices.csv","ranklist.csv");
	   MeritListforAlgo2 testing = new MeritListforAlgo2(test.candidates);
	   testing.sortbyrank();
	   for(int j=0;j<testing.obc.size();j++){
		   Candidate c = testing.obc.get(j); String r="",p="";
		   System.out.println(c.Id+" "+c.Category+" "+c.DS+" "+c.Nationality+" "+c.PD+" ");
		   for(int i=0;i<8;i++) r=r+c.rank[i]+" ";
		   System.out.println(r+","+c.previouswaitlist+c.waiting);
		   for(int i=0;i<c.Preference.size();i++) p=p+c.Preference.get(i)+" ";
		   System.out.println(p);
	   }*/
	   MeritOrderAdmission test = new MeritOrderAdmission("choices.csv","ranklist.csv","programs.csv");
	   for(int i=0;i<test.candidatelist.candidates.size();i++){
		   System.out.println(test.candidatelist.candidates.get(i).Id+" " +test.candidatelist.candidates.get(i).Category+" "+ test.candidatelist.candidates.get(i).waiting+test.progs.get(test.candidatelist.candidates.get(i).previouswaitlist).category);
	   }
	 } 
}
