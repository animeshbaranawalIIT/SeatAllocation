import galeshapley.*;
import meritorder.*;

public class Main {
	
	public static void main(String[] args){
		GaleShapleyAdmission test = new GaleShapleyAdmission("programs.csv","choices.csv","ranklist.csv");
		System.out.println();
		MeritOrderAdmission testing = new MeritOrderAdmission("programs.csv","choices.csv","ranklist.csv");
		
	}
}
