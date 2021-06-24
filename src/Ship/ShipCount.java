package Ship;
import Player.Player;
import java.util.*;

public class ShipCount 
{
	LinkedHashMap<Ship, Boolean> ships = new LinkedHashMap<Ship, Boolean>();
	public int destroyed;
	public boolean allDestroyed;
	
	public ShipCount(Player play)
	{
		play.getShips().forEach( (i) -> {ships.put(i, false);});
		destroyed = 0;
		allDestroyed = false;
	}
	
	public int destroyedUpdate()
	//returns the newly destroyed ships parameters, if nothing is destroyed then -1
	{     
		for(Ship i : ships.keySet())
		{
			if(i.destroyed != ships.get(i))
			{
				ships.put(i, true);
				destroyed = i.n;
				return i.n;
			}
		}
		return -1;
	}
	
	public boolean allDestroyed()
	{
		int count = 0;
		for(boolean i : ships.values())
			if(i == true) count++;
		
		if(count == 7)
		{
			allDestroyed = true;
			return true;
		}
		else
			return false;
	}
	
	public Collection<Boolean> getDestroyed()
	{
		return ships.values();
	}
	
}
