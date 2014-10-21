
public class Main {
	public static void main(String[] args) {
	   
	   MeritOrderAdmission test = new MeritOrderAdmission("choices.csv","ranklist.csv","programs.csv");
	   System.out.println("CandidateUniqueID,ProgrammeID");
	   for(int i=0;i<test.candidatelist.candidates.size();i++){
		   //System.out.println(test.candidatelist.candidates.get(i).Id+" " +test.candidatelist.candidates.get(i).Category+" "+ test.candidatelist.candidates.get(i).waiting+test.progs.get(test.candidatelist.candidates.get(i).previouswaitlist).category);
		   Candidate c = test.candidatelist.candidates.get(i);
		   if(c.waiting.equals("")) System.out.println(c.Id+",-1");
		   else System.out.println(c.Id+","+c.waiting);
	   }
	 } 
}
