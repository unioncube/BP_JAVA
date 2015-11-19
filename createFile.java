package BP_1114;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class createFile {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	
		BufferedWriter bufw = new BufferedWriter(new FileWriter("D:\\sample.txt"));
	
		bufw.write("0,0,1");
		bufw.newLine();
		bufw.write("0,1,1");
		bufw.newLine();
		bufw.write("1,0,1");
		bufw.newLine();
		bufw.write("1,1,0");
		bufw.close();
		
		BufferedWriter bufw1 = new BufferedWriter(new FileWriter("D:\\test.txt"));
		
		bufw1.write("0,0");
		bufw1.newLine();
		bufw1.write("0,1");
		bufw1.newLine();
		bufw1.write("1,0");
		bufw1.newLine();
		bufw1.write("1,1");
		bufw1.close();
	}

}
