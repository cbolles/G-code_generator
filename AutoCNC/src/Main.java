import com.bolles.AutoCNC.gCodeWriter.CNCDraw;

public class Main 
{
	public static void main(String[] args)
	{
		CNCDraw test = new CNCDraw(0.125, 0, "C:/Users/collin/Documents/1 School/11th grade/CIM/test.NC");
		test.enterDefaults(1, 3000);
		test.safeSpace();
		test.drawRectangleFill(0.125,1.75,1,1,-0.125);
		test.closeCode();
		System.out.println("done");
	}
}
