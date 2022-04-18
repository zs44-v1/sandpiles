package sandpiles;

import static java.lang.System.*;

public class sandgrid {
    private int [][] grid;
    private int [][] newgrid;
    private int rows;
    private int columns;
	private int totalSand=0;
	private int cellsCovered=0;
	private int cellsTopple=0;
	private int cellsToppled=0;
    private sandDistribution eSand; 

    public enum sandDistribution { 
		single, cylindrical, gauss, spherical, pyramid 
		}
        
    public int[][] getGrid() { return grid; }
    public void setGrid(int[][] grid) { this.grid = grid; }
          
    public int getRows() { return rows; }
    public void setRows(int rows) { this.rows = rows; }
    
    
    public int getColumns() { return columns; }
    public void setColumns(int columns) { this.columns = columns; }
        
    public int getTotalSand() { return totalSand; }
    
    public int getCellsCovered() { return cellsCovered; }
        
    public int getCellsToppled() { return cellsToppled; }
        
    public sandDistribution geteSand() {
        return eSand;
    }
	public sandgrid(int rows, int columns, int sandgrains, sandDistribution sDistrib ) {
		
    	if((rows%2)==0 )
    		rows++;
    	if((columns%2)==0 )
    		columns++;
		this.grid = new int [rows][columns];
		this.newgrid = new int [rows][columns];;
		this.rows = rows;
		this.columns = columns;
		this.eSand = sDistrib;
		this.totalSand = buildInitialGrid(this.grid, this.rows, this.columns, sandgrains, this.eSand);
	}
    private void topple( )
    {
        this.totalSand = 0;
        this.cellsCovered = 0;
        this.cellsTopple = 0;
        this.cellsToppled = 0;
        for(int i = 0; i < rows;i++){
            for(int j = 0; j < columns; j++){
                int num = grid[i][j];
                if(num >= 4){
                    cellsToppled++;
                    newgrid[i][j] -=4;
                    if(j+1<columns) {
                        newgrid[i][j+1]++;
                    }
                    if(j-1 >= 0){
                        newgrid[i][j-1]++;
                    }
                    if(i+1 < rows){
                        newgrid[i+1][j]++;
                    }
                    if(i-1 >= 0){
                        newgrid[i-1][j]++;
                    }
                }
            }
        }
        CopyGrid(newgrid,grid);
        for(int i=0; i < rows; i++){
            for(int j=0; j < columns; j++){
                if(newgrid[i][j] > cellsTopple){
                    cellsTopple = newgrid[i][j];
                }
                if(newgrid[i][j] != 0){
                    cellsCovered++;
                    totalSand += newgrid[i][j];
                }
            }
        }
    }
    public static void CopyGrid(int[][] source, int[][] dest)
    {
        for(int i = 0; i < source.length; i++) {
            arraycopy(source[i],0,dest[i],0,source[0].length);
        }
    }
    private int buildInitialGrid(int[][] grid, int rows, int columns, int sandgrains, sandDistribution distrib) {
		int r = (int) Math.floor(rows/2);
		int c = (int) Math.floor(columns/2);
		switch (distrib) {
		case single: {
			grid[r][c] = sandgrains;
			break;
		}
		
		case cylindrical: {
			int sandgrainstotal = 0;
			int baserad;
			
			
			if(rows < columns) {
				baserad = (rows-2)/2;
			} else {
				baserad = (columns-2)/2;
			}
			int height = 0;
			if((sandgrains / (baserad*baserad)) < 4)
				height = baserad;
			else
				height = (int)(sandgrains / (Math.PI*baserad*baserad));
				
			int xoffset = baserad;
			int yoffset = baserad;
			
			for(int x = r - xoffset; x <= (r + xoffset); x++) {
				for(int y = c - yoffset; y <= (c + yoffset); y++) {
					if((x-r)*(x-r)+(y-c)*(y-c) <= baserad*baserad) {
						grid[x][y]=height;
						sandgrainstotal+=height;
						
					}
				}
			}
			sandgrains=sandgrainstotal;
			break;
		}
		case gauss:	{
			break;
			
		}
		
		case spherical: {
			int sandgrainstotal = 0;
			int baserad;
			if(rows < columns) {
				baserad = (rows-2)/2;
			} else {
				baserad = (columns-2)/2;
			}
			int xoffset = baserad;
			int yoffset = baserad;
			int height = 0;
			
			for(int x = r - xoffset; x <= (r + xoffset); x++) {
				for(int y = c - yoffset; y <= (c + yoffset); y++) {
					if((x-r)*(x-r)+(y-c)*(y-c) <= baserad*baserad) {
						
						height = baserad*baserad-(x-r)*(x-r)-(y-c)*(y-c);
						
						if(height < 0)
							height = 0;
						else
							height = (int)Math.sqrt(height);
							
						grid[x][y]=height;
						sandgrainstotal+=height;
						
					}
				}
			}
			sandgrains=sandgrainstotal;
			break;
		}
		case pyramid: {
			
			int pbase = (int)Math.floor(Math.pow(6.0*sandgrains, 1/3.0)+1/(Math.pow(162.0*sandgrains, 1/3.0))-1);
			if((pbase % 2)==0 )
				pbase--;
			if(pbase > rows || pbase > columns) {
				if(rows < columns) {
					pbase = rows-2;
				} else {
					pbase = columns-2;
				}
			}
			int pheight = (pbase + 1)/2;
			int offset_row=0, offset_col=0;
			int pn = (pbase+1)/2;
			sandgrains = (int)(pn/3.0*(4*pn*pn-1));

			for(int i = pheight; i > 0; i--) {
				for(int htop=c-offset_col; htop < c+offset_col+1; htop++) {
					grid[r-offset_row][htop]=i;
				}
				for(int hbottom=c-offset_col; hbottom < c+offset_col; hbottom++) {
					grid[r+offset_row][hbottom]=i;
				}
				for(int vright = r+offset_row; vright > r-offset_row; vright--) {
					grid[vright][c+offset_col]=i;
				}
				for(int vleft = r+offset_row-1; vleft > r-offset_row; vleft--) {
					grid[vleft][c-offset_col]=i;
				}
				offset_col++;
				offset_row++;
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + distrib);
		}
		return sandgrains;
	}
    public int fallSand() {
		int loopCounter = 0;
		this.cellsTopple = this.totalSand;
		CopyGrid(this.grid,this.newgrid);
		while (this.cellsTopple > 3){
			this.topple();
			loopCounter++;
			if((loopCounter%10000) == 0){
				out.println("Iteration: "+ loopCounter +"\tCells covered: "+ cellsCovered +"\tTotal sand: "+ totalSand +"\tAverage sand: "+ totalSand / (float)cellsCovered+"\tCells toppled:"+cellsToppled);
	            }
	        }
		out.println("Iteration: "+ loopCounter +"\tCells covered: "+ cellsCovered +"\tTotal sand: "+ totalSand +"\tAverage sand: "+ totalSand / (float)cellsCovered+"\tCells toppled:"+cellsToppled);
		return loopCounter;
	}

}
