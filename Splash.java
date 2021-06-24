import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Ship.ShipCount;

class Splash extends Thread implements ActionListener
{
	static volatile boolean ready = true;
	private int index;
	private volatile ShipCount ship;
	
	private JWindow window;
	private Timer timerImg;
	private Timer timerWait;
	private JLabel label;
    
	public Splash(int index, ShipCount ship, Frame frame)
    {
    	this.index = index;
    	this.ship = ship;
    	timerImg = new Timer(1000, this);
    	timerWait = new Timer(1000, this);
    	window = new JWindow(frame);
        label = new JLabel();
        window.getContentPane().add(label, BorderLayout.CENTER);
        window.setSize(500,300);
    }
	
	public void run()
	{
		while(ship.allDestroyed == false)
		{
			if(ship.destroyed != 0)
			{
				runImage(ship.destroyed);
				ship.destroyed = 0;
			}
		}
	}
    
    public void runImage(int imgIndex)
    {
    	synchronized(ship)
    	{
	    	if(index == 1)
	    	{
	    		label.setIcon(new ImageIcon("src/Images/First/"+imgIndex+".png"));
	    		label.repaint();
	    	}
	    	else
	    	{
	    		label.setIcon(new ImageIcon("src/Images/Second/"+imgIndex+".png"));
	    		label.repaint();
	    	}
	    	
	    	Dimension screenSize =
	    	          Toolkit.getDefaultToolkit().getScreenSize();
	    	        Dimension labelSize = label.getPreferredSize();
	    	        window.setLocation(screenSize.width/2 - (labelSize.width/2),
	    	                    screenSize.height/2 - (labelSize.height/2));
	    	
	    	if(ready == false)
	    	{
	    		timerWait.start();
	    	}
	    	 if(ready == true)
	    	{
	    		ready = false;
		    	window.setVisible(true);
		    	timerImg.start();
	    	}
    	}
    }
    
    public synchronized void actionPerformed(ActionEvent e) 
    {
    	if(timerWait.isRunning() && ready == true)
    	{
    		ready = false;
    		window.setVisible(true);
    		timerWait.stop();
    		timerImg.start();
    	}
    	else if(timerImg.isRunning())
    	{
    		timerImg.stop();
    		timerWait.stop();
    		window.setVisible(false);
    		ready = true;
    	}
      }
}