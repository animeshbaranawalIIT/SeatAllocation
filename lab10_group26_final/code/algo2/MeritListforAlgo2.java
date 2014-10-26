package algo2;

import java.util.*;


public class MeritListforAlgo2 {
	ArrayList<Candidate> ge; // general merit list
	ArrayList<Candidate> obc; // obc merit list
	ArrayList<Candidate> sc; // sc merit list
	ArrayList<Candidate> st; // st merit list
	ArrayList<Candidate> ge_pd; // ge pd merit list
	ArrayList<Candidate> obc_pd; // obc pd merit list
	ArrayList<Candidate> sc_pd; // sc pd merit list
	ArrayList<Candidate> st_pd; // st pd merit list
	
	public MeritListforAlgo2(ArrayList<Candidate> temp){
		ge = new ArrayList<Candidate>(); // initialising lists
		obc = new ArrayList<Candidate>();
		sc = new ArrayList<Candidate>();
		st = new ArrayList<Candidate>();
		ge_pd = new ArrayList<Candidate>();
		obc_pd = new ArrayList<Candidate>();
		sc_pd = new ArrayList<Candidate>();
		st_pd = new ArrayList<Candidate>();
		
		for(int i=0;i<temp.size();i++){
			// input as a list of candidates
			if(temp.get(i).rank[0]!=0){ //append into general merit list if general rank !=0
				ge.add(temp.get(i));
			}
			
			if(temp.get(i).rank[1]!=0){ //append into obc merit list if obc rank !=0
				obc.add(temp.get(i));
			}
			
			if(temp.get(i).rank[2]!=0){ //append into sc merit list if sc rank !=0
				sc.add(temp.get(i));
			}
			
			if(temp.get(i).rank[3]!=0){ //append into st merit list if st rank !=0
				st.add(temp.get(i));
			}
			
			if(temp.get(i).rank[4]!=0){ //append into ge_pd merit list if ge pd rank !=0
				ge_pd.add(temp.get(i));
			}
			
			if(temp.get(i).rank[5]!=0){ //append into obc_pd merit list if obc pd rank !=0
				obc_pd.add(temp.get(i));
			}
			
			if(temp.get(i).rank[6]!=0){ //append into sc_pd merit list if sc_pd rank !=0
				sc_pd.add(temp.get(i));
			}
			
			if(temp.get(i).rank[7]!=0){ //append into st_pd merit list if st_pd rank !=0
				st_pd.add(temp.get(i));
			}
		}
	}
	// Sorting the merit lists
	class ldige implements Comparator<Candidate>{ // comparator for comparing two candidates from ge merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[0],e2.rank[0]);
	    }
	}
	
	class ldiobc implements Comparator<Candidate>{ // comparator for comparing two candidates from obc merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[1],e2.rank[1]);
	    }
	}
	
	class ldisc implements Comparator<Candidate>{ // comparator for comparing two candidates from sc merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[2],e2.rank[2]);
	    }
	}
	
	class ldigepd implements Comparator<Candidate>{ // comparator for comparing two candidates from ge_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[4],e2.rank[4]);
	    }
	}
	
	class ldist implements Comparator<Candidate>{ // comparator for comparing two candidates from st merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[3],e2.rank[3]);
	    }
	}
	
	class ldiobcpd implements Comparator<Candidate>{ // comparator for comparing two candidates from obc_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[5],e2.rank[5]);
	    }
	}
	
	class ldiscpd implements Comparator<Candidate>{ // comparator for comparing two candidates from sc_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[6],e2.rank[6]);
	    }
	}
	
	class ldistpd implements Comparator<Candidate>{ // comparator for comparing two candidates from st_pd merit list
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[7],e2.rank[7]);
	    }
	}
	
	public void sortbyrank(){ // function to sort the merit lists
		Collections.sort(ge,new ldige());
		Collections.sort(obc,new ldiobc());
		Collections.sort(sc,new ldisc());
		Collections.sort(st,new ldist());
		Collections.sort(ge_pd,new ldigepd());
		Collections.sort(obc_pd,new ldiobcpd());
		Collections.sort(sc_pd,new ldiscpd());
		Collections.sort(st_pd,new ldistpd());
	}
}
