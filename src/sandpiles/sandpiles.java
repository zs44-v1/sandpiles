package sandpiles;

import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class sandpiles {
    public static void main(String[] args) throws IOException {
        Color[] colorNames = {Color.DARK_GRAY, Color.LIGHT_GRAY, Color.PINK, Color.GREEN, Color.RED};
        Color[][] colorList = {	colorNames, 
            {Color.BLACK, Color.DARK_GRAY, Color.LIGHT_GRAY,Color.WHITE, Color.RED},
            {Color.BLACK, new Color(215,188,200), new Color(152,182,177),new Color(98,150,119), new Color(73, 93, 99)},
            {Color.BLACK, new Color(45, 138, 37), new Color(244,129,3), new Color(251,174,181),new Color(103,153,208)},
            {Color.BLACK, new Color(215,188,200), new Color(98,150,119), new Color(152,182,177),new Color(73, 93, 99)},
            {Color.BLACK,   new Color(244,129,3), new Color(45, 138, 37), new Color(251,174,181), new Color(103,153,208)}
            };

        int colorscheme;
        int loopCounter=0;
        int rows;
        int columns;
        int cellSize;
        int sandgrains;
        int sandStart;

    	if( args.length< 4 ) {
    		Scanner in = new Scanner(System.in);
    		System.out.println("Enter number of rows (integer value): ");
    		rows = in.nextInt();
    		System.out.println("Enter number of columns (integer value): ");
    		columns = in.nextInt();
    		System.out.println("Enter cell size in pixels (integer value): ");
    		cellSize = in.nextInt();
    		System.out.println("Enter number of sand grains (integer value): ");
    		sandgrains = in.nextInt();
    		System.out.println("Enter sand shape at start: ");
    		System.out.println("Single cell: 0 ");
    		System.out.println("Cylindrical distribution: 1");
    		System.out.println("Gaussian distribution: 2");
    		System.out.println("Hemispherical distribution: 3");
    		System.out.println("Pyramidal distribution: 4");
    		sandStart = in.nextInt();
    		System.out.println("Color scheme: 0 - "+(colorList.length-1));
    		colorscheme = in.nextInt();
    		
    		in.close();
    	}
    	else {
    		rows = Integer.parseInt(args[0]);
    		columns =Integer.parseInt(args[1]);
    		cellSize = Integer.parseInt(args[2]);
    		sandgrains = Integer.parseInt(args[3]);
    		sandStart = Integer.parseInt(args[4]);
    		colorscheme = Integer.parseInt(args[5]);
    	}

        sandgrid testgrid = new sandgrid(rows, columns, sandgrains,sandgrid.sandDistribution.values()[sandStart]);
        loopCounter = testgrid.fallSand();
        if(colorscheme >= 0) {
        	BufferedImage bufferedImage = new BufferedImage(cellSize*columns, cellSize*rows, BufferedImage.TYPE_INT_RGB);
        	Graphics2D g2d = bufferedImage.createGraphics();
        	int cl = colorscheme;
            for(int i = 0; i < testgrid.getRows(); i++){
                for(int j=0; j < testgrid.getColumns(); j++){
                    int origx = j*cellSize;
                    int origy = i*cellSize;
                    int num = testgrid.getGrid()[i][j];
                    g2d.setColor(colorList[cl][num]);
                    g2d.fillRect(origx, origy, cellSize, cellSize);
                }
            }
            g2d.dispose();

            File file = new File(testgrid.getColumns()+"x"+ testgrid.getRows() +"x"+ sandgrains +"_"+testgrid.geteSand().toString()+"_"+ testgrid.getTotalSand() +"_"+colorscheme+"_"+ loopCounter +".png");
            boolean png = ImageIO.write(bufferedImage, "png", file);
            if(png) {
            	System.out.println("Image "+file+" writen succesfully");
            }
        }
        else {
        	for( int cl = 0; cl < colorList.length; cl++) {
            	BufferedImage bufferedImage = new BufferedImage(cellSize*columns, cellSize*rows, BufferedImage.TYPE_INT_RGB);
            	Graphics2D g2d = bufferedImage.createGraphics();
        		for(int i = 0; i < testgrid.getRows(); i++){
                    for(int j=0; j < testgrid.getColumns(); j++){
                        int origx = j*cellSize;
                        int origy = i*cellSize;
                        int num = testgrid.getGrid()[i][j];
                        g2d.setColor(colorList[cl][num]);
                        g2d.fillRect(origx, origy, cellSize, cellSize);
                    }
                }
                g2d.dispose();
                File file = new File(testgrid.getColumns()+"x"+ testgrid.getRows() +"x"+ sandgrains +"_"+testgrid.geteSand().toString()+"_"+ testgrid.getTotalSand() +"_"+cl+"_"+ loopCounter +".png");
                if(ImageIO.write(bufferedImage, "png", file)) {
                	System.out.println("Image "+file+" writen succesfully");
                }
        	}
        }
    }    
}
