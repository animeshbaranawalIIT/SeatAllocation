import java.util.*;
import java.io.*;

public class MeritListforAlgo2 {
	ArrayList<Candidate> ge;
	ArrayList<Candidate> obc;
	ArrayList<Candidate> sc;
	ArrayList<Candidate> st;
	ArrayList<Candidate> ge_pd;
	ArrayList<Candidate> obc_pd;
	ArrayList<Candidate> sc_pd;
	ArrayList<Candidate> st_pd;
	
	public MeritListforAlgo2(ArrayList<Candidate> temp){
		ge = new ArrayList<Candidate>();
		obc = new ArrayList<Candidate>();
		sc = new ArrayList<Candidate>();
		st = new ArrayList<Candidate>();
		ge_pd = new ArrayList<Candidate>();
		obc_pd = new ArrayList<Candidate>();
		sc_pd = new ArrayList<Candidate>();
		st_pd = new ArrayList<Candidate>();
		
		for(int i=0;i<temp.size();i++){
			
			if(temp.get(i).rank[0]!=0){
				ge.add(temp.get(i));
			}
			
			if(temp.get(i).rank[1]!=0){
				obc.add(temp.get(i));
			}
			
			if(temp.get(i).rank[2]!=0){
				sc.add(temp.get(i));
			}
			
			if(temp.get(i).rank[3]!=0){
				st.add(temp.get(i));
			}
			
			if(temp.get(i).rank[4]!=0){
				ge_pd.add(temp.get(i));
			}
			
			if(temp.get(i).rank[5]!=0){
				obc_pd.add(temp.get(i));
			}
			
			if(temp.get(i).rank[6]!=0){
				sc_pd.add(temp.get(i));
			}
			
			if(temp.get(i).rank[7]!=0){
				st_pd.add(temp.get(i));
			}
		}
	}
	
	class ldige implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[0],e2.rank[0]);
	    }
	}
	
	class ldiobc implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[1],e2.rank[1]);
	    }
	}
	
	class ldisc implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[2],e2.rank[2]);
	    }
	}
	
	class ldigepd implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[4],e2.rank[4]);
	    }
	}
	
	class ldist implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[3],e2.rank[3]);
	    }
	}
	
	class ldiobcpd implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[5],e2.rank[5]);
	    }
	}
	
	class ldiscpd implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[6],e2.rank[6]);
	    }
	}
	
	class ldistpd implements Comparator<Candidate>{
	    public int compare(Candidate e1, Candidate e2) {
	        return Integer.compare(e1.rank[7],e2.rank[7]);
	    }
	}
	
	public void sortbyrank(){
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