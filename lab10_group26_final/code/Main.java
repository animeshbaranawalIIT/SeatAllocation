import algo2.*;

public class Main {
	public static void main(String[] args) {
	   
	   MeritOrderAdmission test = new MeritOrderAdmission("choices.csv","ranklist.csv","programs.csv");
	   System.out.println("CandidateUniqueID,ProgrammeID");
	   /*for(int i=0;i<test.mylist.ge.size();i++){ System.out.println(test.mylist.ge.get(i).Id); }
	   System.out.println();
	   for(int i=0;i<test.mylist.obc.size();i++){ System.out.println(test.mylist.obc.get(i).Id); }
	   System.out.println();
	   for(int i=0;i<test.mylist.sc.size();i++){ System.out.println(test.mylist.sc.get(i).Id); } System.out.println();
	   for(int i=0;i<test.mylist.st.size();i++){ System.out.println(test.mylist.st.get(i).Id); } System.out.println();
	   for(int i=0;i<test.mylist.ge_pd.size();i++){ System.out.println(test.mylist.ge_pd.get(i).Id); } System.out.println();
	   for(int i=0;i<test.mylist.obc_pd.size();i++){ System.out.println(test.mylist.obc_pd.get(i).Id); } System.out.println();
	   for(int i=0;i<test.mylist.sc_pd.size();i++){ System.out.println(test.mylist.sc_pd.get(i).Id); } System.out.println();
	   for(int i=0;i<test.mylist.st_pd.size();i++){ System.out.println(test.mylist.st_pd.get(i).Id); } System.out.println();
	   */
	   test.display();
	 } 
}
