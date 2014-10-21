import java.util.*;
import java.io.File;
import java.io.IOException;

class MeritOrderAdmission{
	
	ArrayList <VirtualProgramme> progs; 
	MeritList candidatelist;
	MeritListforAlgo2 mylist;

	public MeritOrderAdmission(String f1, String f2, String f3) {
		progs = new ArrayList<VirtualProgramme>();
		candidatelist = new MeritList(f1,f2);
		mylist = new MeritListforAlgo2(candidatelist.candidates);
		mylist.sortbyrank();
		this.fillprogramme(f3);
		for(int j=0;j<mylist.ge.size();j++) phase1(mylist.ge.get(j),"GE",false);
		for(int j=0;j<mylist.obc.size();j++) phase1(mylist.obc.get(j),"OBC",false);
		for(int j=0;j<mylist.sc.size();j++) phase1(mylist.sc.get(j),"SC",false);
		for(int j=0;j<mylist.st.size();j++) phase1(mylist.st.get(j),"ST",false);
		for(int j=0;j<mylist.ge_pd.size();j++) phase1(mylist.ge_pd.get(j),"GE_PD",true);
		for(int j=0;j<mylist.obc_pd.size();j++) phase1(mylist.obc_pd.get(j),"OBC_PD",true);
		for(int j=0;j<mylist.sc_pd.size();j++) phase1(mylist.sc_pd.get(j),"SC_PD",true);
		for(int j=0;j<mylist.st_pd.size();j++) phase1(mylist.st_pd.get(j),"ST_PD",true);
		
	}
	
	public int find(String cd, String cat, Boolean pd){
		for(int j=0;j<progs.size();j++){
			if(progs.get(j).code.equals(cd)){
				if(progs.get(j).category.equals(cat)){
					if(progs.get(j).pdstatus.equals(pd)){ return j; }
				}
			}
		}
		return -1;
	}
	
	public void fillprogramme(String f){
		File file = new File(f);
		
		try{	
			Scanner inp = new Scanner(file);
			String strLine="";
			strLine= inp.nextLine();
			String[] temp2;		// array to store the name of columns to be used in virtual programmes
			String del =",";
			temp2 = strLine.split(del);
			while (inp.hasNext()){   
				strLine = inp.nextLine(); 
				String[] temp;				// array for storing for rest of the rows
				temp = strLine.split(del);  
				for(int i=3; i<11; i++){ 
					VirtualProgramme v;
					if(i<=6){
						v = new VirtualProgramme(temp[1],temp2[i],false,Integer.parseInt(temp[i]));
					}  // cons for non PD virtual progs 
					else{v= new VirtualProgramme(temp[1],temp2[i],true,Integer.parseInt(temp[i]));}		// cons for PD virtual progs
					progs.add(v);  // virtual progs added to the array of 
				}
			}
			inp.close();
		}
		catch (IOException ex){System.out.println("sada"); }
	}
	
	public void phase1(Candidate c,String cat,Boolean pd){
		int i; String temp;
		for(int j=0;j<c.Preference.size();j++){
			i=find(c.Preference.get(j),cat,pd);
			System.out.println(i);
			if(progs.get(i).apply(c, cat, pd)){
				System.out.println("App accepted");
				temp = c.Preference.get(j);
				System.out.println(temp);
				if(c.waiting.equals("")){ System.out.println("adding to empty"); c.waiting=temp; c.previouswaitlist=i; }
				else{ 
					if(c.Preference.indexOf(temp)<c.Preference.indexOf(c.waiting)){ 
						progs.get(c.previouswaitlist).num--;
						c.previouswaitlist=i;
						c.waiting=temp; 
					}
					else {System.out.println("Found but not gud enough");} 
				}
				break;
			}
		}
	}
	
}