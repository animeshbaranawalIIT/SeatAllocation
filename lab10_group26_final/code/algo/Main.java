import java.io.*;
import java.util.*;
import algo2.*;

public class Main {
	public static void main(String[] args) {
	   MeritOrderAdmission test = new MeritOrderAdmission("choices.csv","ranklist.csv","programs.csv");
	   for(int i=0;i<test.candidatelist.candidates.size();i++){
		   test.candidatelist.candidates.get(i).display();//+test.candidatelist.candidates.get(i).waiting);+test.progs.get(test.candidatelist.candidates.get(i).previouswaitlist).category);
	   }
	 } 
}
