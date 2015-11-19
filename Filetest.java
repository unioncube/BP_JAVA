package BP_1114;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Filetest {
	
	public static void main(String[] args) throws IOException
	{
		/*int inputNum, int hiddenNum, int outputNum, double rate_w, double rate_y*/
		BP bp = new BP(2,20,1,0.6,0.6);
		int p = 0;
		double error = 100;
		while(p<30000000 && error>0.011)
		{
			BufferedReader bufr = new BufferedReader(new FileReader("D:\\sample.txt"));
			String line = null;
			
			error = 0;
			while((line=bufr.readLine())!=null)
			{
				double[] in = new double[2];
				double[] out = new double[1];
				String[] s = line.split(",");
				in[0] = Double.parseDouble(s[0]);
				in[1] = Double.parseDouble(s[1]);
				out[0] = Double.parseDouble(s[2]);
				bp.train(in, out);
				p++;
				error += bp.sqr_err;
			}
			System.out.println("训练次数:"+p+"     error:"+error);
		}
		BufferedReader bufr1 = new BufferedReader(new FileReader("D:\\test.txt"));
		String line1 = null;
		while((line1=bufr1.readLine())!=null)
		{
			double[] in1 = new double[2];
			double[] out1 = new double[1];
			String[] s1 = line1.split(",");
			in1[0] = Double.parseDouble(s1[0]);
			in1[1] = Double.parseDouble(s1[1]);
			out1 = bp.test(in1);
			System.out.println("测试数据:"+in1[0]+"  "+in1[1]+"  "+out1[0]);
		}
	}
}
