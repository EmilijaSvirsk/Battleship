package Structure;
import Ship.Ship;

public class Block implements Structure
{
	public int x;
	public int y;
	public String show; 
	public boolean placed;
	public boolean shot;
	public Ship ship;
	
	public Block(int x, int y)
	{
		this.x = x;
		this.y = y;
		create();
	}
	
	public void create()
	{
		show = ".";
		placed = false;
		shot = false;
	}

	public void shot(boolean hit)
	{
		
		shot = true;
		if(hit == true)
			show = "X";
		else
			show = "+";
		
		if(placed == true)
			ship.shot();
	}
	
	public void place(Ship ship)
	{
		this.ship = ship;
		placed = true;
		show = "O";
	}
}
