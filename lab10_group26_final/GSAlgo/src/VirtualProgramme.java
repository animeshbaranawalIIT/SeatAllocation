
import java.util.*;

public class VirtualProgramme{
	private String course;
	private int category;
	private Boolean PDstatus;
	private int Quota;
	private int Curr;
	private ArrayList <Candidate> Waitlisted;
	private ArrayList <Candidate> Applications;
	private HashMap<Candidate,Integer> merit;
	
	public VirtualProgramme(String code, int Cat, Boolean pd, int q){
		course = code;
		category = Cat;  PDstatus = pd;
		Quota = q;
		Curr =0;
		Waitlisted = new ArrayList <Candidate>();
		Applications = new ArrayList <Candidate>();
		merit = new HashMap<Candidate,Integer>();
	}

	public String retcourse(){ return course; }
	public int retCategory(){return category;}
	public Boolean retPdstatus(){return PDstatus;}
	public ArrayList <Candidate> retWaitlisted(){return Waitlisted;}
	public ArrayList <Candidate> retApplications(){return Applications;}
	public void setmerit(MeritList meritlist){
		int x = category + 4*(PDstatus?1:0);
		merit = meritlist.retmeritlist()[x];
	}
	
	public void apply(Candidate c){ Applications.add(c); }

	public ArrayList <Candidate> filter(){
		Collections.sort(Applications,new Comparator<Candidate>() {
			public int compare(Candidate c1, Candidate c2){
					int i,j;
					i = merit.get(this);
					j = merit.get(this);
				   return Integer.compare(i,j);
			}
		});
		ArrayList <Candidate> Rejected = new ArrayList<Candidate>();
		for(int i=0; i<Applications.size();i++){
					if(Curr+1 <= Quota){
						Waitlisted.add(Applications.get(i)); 
						Applications.get(i).setwaitlisted(Applications.get(i).retcurrent().code);
						Curr++; 
					}
					else{
						Applications.get(i).setcurrent(Applications.get(i).findnext());
						Rejected.add(Applications.get(i));
					}
					Applications.remove(i);
		}
		return Rejected;
	}
}