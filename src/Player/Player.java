package Player;
import FileExceptions.BadFileException;
import Structure.Gridline;
import Ship.Ship;
import java.util.*;

public abstract class Player 
{
	protected Gridline gridM;
	protected Gridline gridE;
	protected List<Ship> ship;
	protected String name;
	
	Player(String name)
	{
		this.name = name;
		ship = new ArrayList<>();
		gridM = new Gridline(10,10);	
		gridE = new Gridline(10,10);
		createGridEnem();
	}
	
	public abstract boolean takeAim(Player pers, int x, int y);
	
	public abstract void createGridMine() throws BadFileException;
	
	public void createGridEnem()
	{
		gridE.create();
	}
	
	public Gridline gridlineMine()
	{
		return gridM;
	}
	
	public boolean shot(int x, int y)
	{
		return gridM.shot(x, y);
	}
	
	public String checkBlockM(int x, int y)
	{
		return gridM.checkBlock(x, y);
	}
	public String checkBlockE(int x, int y)
	{
		return gridE.checkBlock(x, y);
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<Ship> getShips()
	{
		return ship;
	}
}
