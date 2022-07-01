package Ship;
import Structure.Block;
import java.util.*;

public class Ship 
{
	private List<Block> place;
	public int n;
	public int x;
	public int y;
	public String dir;
	public boolean destroyed;
	private int lives;
	
	public Ship(int n, int x, int y, String dir)
	{
		this.n = n;
		this.x = x;
		this.y = y;		
		this.dir = dir;
		destroyed = false;
		place = new ArrayList<>();
		lives = n;
	}
	
	public void place(Block block)
	{
		place.add(block);
	}
	
	public void shot()
	{
		--lives;
		if(lives == 0)
			destroyed = true;
	}
}
