package BP_1114;

public class BP {
	
	//样本输入输出in&out
	private double[] in;
	private double[] out;
	//隐藏层输入输出hidden_in*hidden_out
	private double[] hidden_in;
	private double[] hidden_out;
	//输出层输入与输出out_in&out_out;
	private double[] out_in;
	private double[] out_out;
	//各节点之间的权值w[i-h]&v[h-o]
	private double[][] w;
	private double[][] v;
	//隐藏层和输出层的阈值hidden_y,out_y
	private double[] hidden_y;
	private double[] out_y;
	//输入层隐藏层输出层节点数inputNum&hiddenNum&outputNum
	private int inputNum;
	private int hiddenNum;
	private int outputNum;
	//隐藏层输出层的一般误差
	private double[] delta_hidden;
	private double[] delta_out;
	//总误差error
	public double error;
	//用于执行速率
	private double rate_w;
	private double rate_y;
	public double sqr_err;
	
	
	public BP(int inputNum, int hiddenNum, int outputNum, double rate_w,
			double rate_y) {
		super();
		this.inputNum = inputNum;
		this.hiddenNum = hiddenNum;
		this.outputNum = outputNum;
		this.rate_w = rate_w;
		this.rate_y = rate_y;
		in = new double[inputNum];
		out = new double[outputNum];
		hidden_in = new double[hiddenNum];
		hidden_out = new double[hiddenNum];
		out_in = new double[outputNum];
		out_out = new double[outputNum];
		w = new double[inputNum][hiddenNum];
		v = new double[hiddenNum][outputNum];
		hidden_y = new double[hiddenNum+1];
		out_y = new double[outputNum+1];
		delta_hidden = new double[hiddenNum];
		delta_out = new double[outputNum];
		RandomWeight();
	}
	
	private void RandomWeight() {
		RandomWeight(inputNum,hiddenNum,w,hidden_y);
		RandomWeight(hiddenNum,outputNum,v,out_y);
	}

	private void RandomWeight(int start, int end, double[][] weight, double[] yuzhi) {
		for(int n=0;n<end;n++)
		{
			for(int m=0;m<start;m++)
			{
				weight[m][n] = (Math.random()/32767.0)*2-1;
			}
			yuzhi[n] = (Math.random()/32767.0)*2-1;
		}
	}

	public void train(double[] in, double[] out)
	{
		this.in = in;
		this.out = out;
		forward();
		Calculate_err();
		UpData();
	}

	private void forward() {
		forward(inputNum,hiddenNum,w,hidden_y,in,hidden_in,hidden_out);
		forward(hiddenNum,outputNum,v,out_y,hidden_out,out_in,out_out);
		error = 0;
		for(int k=0;k<outputNum;k++)
		{
			//System.out.println("real:"+out[k]+"    test:"+out_out[k]);
		}
		
	}
	private void forward(int start, int end, double[][] weight, double[] yuzhi, double[] setIn,double[] begin, double[] after) {
		//inputNum,hiddenNum,w,hidden_y,in,hidden_in,hidden_out
		for(int n=0;n<end;n++)
		{
			double sum = 0;
			for(int m=0;m<start;m++)
				sum += setIn[m]*weight[m][n];
			begin[n] = sum - yuzhi[n];
			after[n] = Sigmoid(begin[n]);
		}
		
	}

	private double Sigmoid(double d) {
		// TODO Auto-generated method stub
		return 1/(1+Math.exp(-d));
	}

	private void Calculate_err() {
		Calculate_err_out();
		Calculate_err_hidden();
		
	}
	private void Calculate_err_out() {
		sqr_err = 0;
		for(int k=0;k<outputNum;k++)
		{
			delta_out[k] = (out[k]-out_out[k]) * out_out[k] * (1-out_out[k]);
			sqr_err += (out[k]-out_out[k])*(out[k]-out_out[k]);
		}
		sqr_err = sqr_err/2;
	}

	private void Calculate_err_hidden() {
		for(int n=0;n<hiddenNum;n++)
		{
			double sum = 0;
			for(int k=0;k<outputNum;k++)
				sum += delta_out[k] * v[n][k];
			delta_hidden[n] = sum * hidden_out[n] * (1-hidden_out[n]);
		}
	}

	private void UpData() {
		UpData_v();
		UpData_w();
	}

	private void UpData_v() {
		for(int k=0;k<outputNum;k++)
		{
			for(int n=0;n<hiddenNum;n++)
				v[n][k] = v[n][k] + rate_w * delta_out[k] * hidden_out[n];
			out_y[k] = out_y[k] + rate_w * delta_out[k]; 
		}
	}
	private void UpData_w() {
		for(int n=0;n<hiddenNum;n++)
		{
			for(int m=0;m<inputNum;m++)
				w[m][n] = w[m][n] + rate_y * delta_hidden[n] * in[m];
			hidden_y[n] = hidden_y[n] + rate_y * delta_hidden[n];
		}
	}
	
	
	public double[] test(double[] in)
	{
		this.in = in;
		forward();
		return out_out;
	}

}
