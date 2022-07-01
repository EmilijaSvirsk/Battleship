package Player;
import Ship.Ship;
import java.util.Random;

import FileExceptions.BadFileException;

public class Computer extends Player 
{

	public Computer(String name)
	{
		super(name);
	}
	
	public void createGridMine() throws BadFileException
	{
		gridM.create();
		Random random = new Random();
		int shipN[] = {5, 4, 3, 2, 2, 1, 1};
		for(int i:shipN)
		{
			boolean next = false;
			int x = 0;
			int y = 0;
			String dir[] = {"r", "d"};
			int dirN = 0;
			
			while(next == false)
			{
				x = random.nextInt(10);
				y = random.nextInt(10);
				dirN = random.nextInt(2);
				next = gridM.checkShip(i, x, y, dir[dirN]);
			}
			Ship temp = new Ship(i, x, y, dir[dirN]);
			ship.add(temp);
			gridM.place(temp);
		}
	}
	
	public boolean takeAim(Player pers, int x, int y)
	{
		boolean shot = false;
		Random random = new Random();
		boolean next = false;
		while(next == false)
		{
			x = random.nextInt(10);
			y = random.nextInt(10);
			if(gridE.checkShot(x, y) == true)
			{
				shot = pers.shot(x, y);
				gridE.mark(x, y, shot);
				next = true;
			}
		}
		return shot;
	}
}
