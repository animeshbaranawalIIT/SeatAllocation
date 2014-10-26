import java.util.*;

public class Institute {
	private int seats;
	private int curr;
	private ArrayList<Candidate> applicants;
	private HashMap<String,Integer> gerank; 
	
	public Institute(){
		seats=2;
		curr=0;
		applicants = new ArrayList<Candidate>();
		gerank = new HashMap<String,Integer>(); 
	}
	
	public void apply(Candidate c, int t){ 
		applicants.add(c); 
		if(gerank.get(c.retID())!=null) gerank.put(c.retID(),t); 
	}
	
	public ArrayList <Candidate> filter(){
		Collections.sort(applicants,new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2){
					int i,j;
					i = gerank.get(c1.retID());
					j = gerank.get(c2.retID());
				   return Integer.compare(i,j);
			}
		});
		ArrayList <Candidate> Rejected = new ArrayList<Candidate>();
		for(int i=0; i<applicants.size();i++){
					if(curr+1 <= seats){ 
						if(seats-curr>1) applicants.get(i).setwaitlisted(applicants.get(i).retcurrent().code);
						else if(seats-curr==1){
							int k=1+i; ArrayList<Candidate> temp = new ArrayList<Candidate>();
							temp.add(applicants.get(i));
							while(gerank.get(applicants.get(k).retID())==gerank.get(applicants.get(i).retID()) && k<applicants.size()){
								temp.add(applicants.get(k)); k++;
							}
							for(int p=0;p<temp.size();p++){ 
								temp.get(p).setwaitlisted(temp.get(p).retcurrent().code);
							}
						}
						curr++;
					}
					else{ 
						applicants.get(i).setcurrent(applicants.get(i).findnext());
						Rejected.add(applicants.get(i));
					}
					applicants.remove(i);
		}
		
		return Rejected;
	}
}
