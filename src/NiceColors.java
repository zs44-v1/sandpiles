import java.awt.Color;
import java.util.Random;
public class NiceColors
{
  public static int colorPos = 0;
    //use golden ratio to create pleasant colors
  	//http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/
  	public static Color betterNiceColor()
  	{
  		double goldenRatioConj = (1.0 + Math.sqrt(5.0)) / 2.0;
  		float hue = new Random().nextFloat();
  		
  		hue += goldenRatioConj * (colorPos / (5 * Math.random()));
  		hue = hue % 1;
  		
  		return Color.getHSBColor(hue, 0.5f, 0.95f);
  	}
}
