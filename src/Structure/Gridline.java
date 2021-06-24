package Structure;
import Ship.Ship;

public class Gridline implements Structure
{
	private int x;
	private int y;
	private Block grid[][];
	
	public Gridline(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void create()
	{
		grid = new Block[this.x][this.y];
		for(int i = 0; i < this.x; i++)
		{
			for(int j = 0; j < this.y; j++)
				grid[i][j] = new Block(i,j);
		}
	}
	
	public boolean checkShot(int x, int y) //check if a shot can be done in enemies grid
	{
		if(grid[x][y].shot == false)
			return true;
		else
			return false;
	}
	
	public boolean checkShip(int n, int x, int y, String dir)
	{	
		if(n == 1)
			return !grid[x][y].placed;
		
		if(dir.equals("r") && x+n < 10) //right
		{
			for(int i = 0; i < n; i++)
			{
				if(grid[x+i][y].placed == true)
					return false;
			}
			return true;
		}
		if(dir.equals("d") && y+n < 10) //down
		{
			for(int i = 0; i < n; i++)
			{
				if(grid[x][y+i].placed == true)
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	public String checkBlock(int x, int y)
	{
		return grid[x][y].show;
	}
	
	public void place(Ship ship) //place a ship on the gridline in creation
	{
		int x = ship.x;
		int y = ship.y;
		int n = ship.n;
		String dir = ship.dir; 
		if(n == 1)
		{
			grid[x][y].place(ship);
			ship.place(grid[x][y]);
			return;
		}
		
		if(dir.equals("r")) //right
		{
			for(int i = 0; i < n; i++)
			{
				grid[x+i][y].place(ship);
				ship.place(grid[x+i][y]);
			}
			return;
		}
		if(dir.equals("d")) //down
		{
			for(int i = 0; i < n; i++)
			{
				grid[x][y+i].place(ship);
				ship.place(grid[x][y+i]);
			}
			return;
		}
	}
	
	public boolean shot(int x, int y)
	{
		grid[x][y].shot(grid[x][y].placed);
		return grid[x][y].placed;
	}
	
	public void mark(int x, int y, boolean hit) 
	//mark on the enemies grid the shot according to outcome
	{
		grid[x][y].shot(hit);
	}
}
