import common.*;
import galeshapley.*;
import meritorder.*;
import java.io.*;

public class Main {
   public static void main(String args[]) throws IOException
   {
      PrintStream out1 = null;
      PrintStream out2 = null;
      try {
         out1 = new PrintStream("gs.csv");
         out2 = new PrintStream("mo.csv");
	System.setOut(out1);
	GaleShapleyAdmission obj1 = new GaleShapleyAdmission("programs.csv","choices.csv","ranklist.csv");
	obj1.display();

	System.setOut(out2);	
	MeritOrderAdmission obj2 = new MeritOrderAdmission("programs.csv","choices.csv","ranklist.csv");
	obj2.display();

      }finally {
         if (out1 != null && out2 != null) {
            out1.close(); out2.close();
         }
      }
   }

}
