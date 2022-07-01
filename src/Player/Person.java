package Player;
import Ship.Ship;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import FileExceptions.BadFileException;

public class Person extends Player 
{
	
	public Person(String name)
	{
		super(name);
	}
	
	public void createGridMine() throws BadFileException
	{
		String fl = "src/ships.txt";
		gridM.create();
		try 
		{
			FileReader fr = new FileReader(fl);
			BufferedReader read = new BufferedReader(fr);
			String line;
			String coord[] = new String[4];
			for(int i = 0; i < 7; i++)
			{
				line = read.readLine();
				if(line.length() != 7)
				{
					fr.close();
					read.close();
					throw new BadFileException(2);
				}
				coord = line.split(";");
				try
				{
					int n = Integer.parseInt(coord[0]);
					int x = Integer.parseInt(coord[1]);
					int y = Integer.parseInt(coord[2]);
					String dir = coord[3];
					if(n > 5 || x > 9 || y > 9 ||
					   (!dir.equals("r") && !dir.equals("d") && !dir.equals("0")))
					{
						fr.close();
						read.close();
						throw new BadFileException(2);
					}
					Ship temp = new Ship(n, x, y, dir);
					ship.add(temp);
					gridM.place(temp);
				}
				catch(NullPointerException n)
				{
					fr.close();
					read.close();
					throw new BadFileException(2);
				}
			}
			fr.close();
			read.close();
		} catch (IOException e) {
			throw new BadFileException(1);
		}
	}
	
	public boolean takeAim(Player comp, int x, int y)
	{
		boolean shot = comp.shot(x, y);
		gridE.mark(x, y, shot);
		return shot;
	}
}
