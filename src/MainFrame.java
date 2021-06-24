import Player.Computer;
import Player.Person;
import Player.Player;
import Ship.ShipCount;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import FileExceptions.BadFileException;
import java.util.*;

public class MainFrame implements ActionListener
{
	private static MainFrame main = new MainFrame();
	
	private JFrame frame;
	private JButton buttonM[][];
	private JPanel panelM;
	private JButton buttonE[][];
	private JPanel panelE;
	private JPanel lowPanel;
	private JPanel highPanel;
	private JTextArea text;
	private Player play1;
	private ShipCount count1;
	private Player play2;
	private ShipCount count2;
	private String infoMess = "Click any button on the left to start the game.";
	private JTable table;
	private String[] columnNames;
	private JScrollPane scrollPane;
	private boolean endGame;

	private MainFrame() {}
	
	public static MainFrame getMainFrame()
	{
		return main;
	}
	
	protected boolean startMain(String protag, String antag)
	{
		play1 = new Person(protag);
		play2 = new Computer(antag);
		try
		{
			play1.createGridMine();
			play2.createGridMine();
		}
		catch(BadFileException e)
		{
			e.printMessage();
			return false;
		}
		initiateComponents();
		frame.setVisible(true);
		return true;
	}
	
	
	private void initiateComponents()
	{
		count1 = new ShipCount(play1);
		count2 = new ShipCount(play2);
		frame = new JFrame("BattleShip");
		buttonM = new JButton[10][10];
		panelM = new JPanel();
		buttonE = new JButton[10][10];
		panelE = new JPanel();
		lowPanel = new JPanel();
		highPanel = new JPanel();
		text = new JTextArea(infoMess,10,25);
		columnNames = new String[] {
				"Length", play1.getName(), play2.getName()
			};
		table = new JTable(tableContent(), columnNames);
		scrollPane = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		createPanelM();
		createPanelE();
		createHighPanel();
		createLowPanel();
		createFrame();
	}
	public Splash startSplash1()
	{
        Splash splash1 = new Splash(1, count2, frame);
		return splash1;
	}
	
	public Splash startSplash2()
	{
        Splash splash2 = new Splash(2, count1, frame);
		return splash2;
	}
	
	private Object[][] tableContent()
	{
		Object[][] data = new Object[7][3];
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		Collections.addAll(sizes, 5, 4, 3, 2, 2, 1, 1);
		
		Iterator<Integer> sizesIt = sizes.iterator();
		Iterator<Boolean> count1It = count1.getDestroyed().iterator();
		Iterator<Boolean> count2It = count2.getDestroyed().iterator();
		for(Object[] obj: data)
		{
			obj[0] = sizesIt.next();
			obj[1] = count1It.next();
			obj[2] = count2It.next();
		}
		return data;
	}
	
	private void createPanelM()
	{
		panelM.setLayout(new GridLayout(10, 10));
		panelM.setPreferredSize(new Dimension(450, 300));
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				buttonM[j][i] = new JButton();
				buttonM[j][i].setText(play1.checkBlockM(j,i));
				buttonM[j][i].setBackground(Color.WHITE);
				buttonM[j][i].setEnabled(false);
				panelM.add(buttonM[j][i]);
			}
		}
	}
	
	private void createPanelE()
	{
		panelE.setLayout(new GridLayout(10, 10));
		panelE.setPreferredSize(new Dimension(450, 300));
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				buttonE[j][i] = new JButton();
				buttonE[j][i].setText(".");
				buttonE[j][i].setBackground(Color.WHITE);
				panelE.add(buttonE[j][i]);
				buttonE[j][i].setActionCommand(String.valueOf(j)+String.valueOf(i));
				buttonE[j][i].addActionListener(this);
			}
		}
	}
	
	private void createHighPanel()
	{
		highPanel.setSize(150,500);
		highPanel.setLayout(new FlowLayout());
		highPanel.add(panelM);
		scrollPane.setPreferredSize(new Dimension(300, 136));
		highPanel.add(scrollPane);
	}
	
	private void createLowPanel()
	{
		lowPanel.add(panelE);
		text.setEditable(false);
		lowPanel.add(text);
	}
	
	private void createFrame()
	{
		frame.setSize(900,700);
		frame.setLayout(new GridLayout(2, 1));
		frame.add(highPanel);
		frame.add(lowPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = frame.getPreferredSize();
		frame.setLocation(screenSize.width/2 - (labelSize.width/2),
		                  screenSize.height/2 - (labelSize.height/2));
	}

	public void actionPerformed(ActionEvent e) {
		if(endGame == true)
			return;
		String command = (e.getActionCommand());
		text.setText(command);
		int x = Integer.parseInt(String.valueOf(command.substring(0,1)));
		int y = Integer.parseInt(String.valueOf(command.substring(1,2)));
		
		if(play1.takeAim(play2, x, y))
			text.setText(play1.getName() + " got a successful shot!\n");
		else
			text.setText(play1.getName() + " missed the shot.\n");
		int updateCount2 = count2.destroyedUpdate();
		if(updateCount2 != -1)
			text.append(play2.getName() + "'s " + updateCount2 + " length ship was destroyed!\n");
		changeButtonE(x, y);
		
		if(play2.takeAim(play1, 0, 0))
			text.append(play2.getName() + " got a successful shot!\n");
		else
			text.append(play2.getName() + " missed the shot.\n");
		int updateCount1 = count1.destroyedUpdate();
		if(updateCount1 != -1)
			text.append(play1.getName() + "'s " + updateCount1 + " length ship was destroyed!\n");
		changeButtonM();
		
		updateTable();
		checkEnd();
	}
	
	private void changeButtonE(int x, int y)
	{
		String txt = play1.checkBlockE(x,y);
		buttonE[x][y].setText(txt);
		buttonE[x][y].setEnabled(false);
		if(txt.equals("X"))
			buttonE[x][y].setBackground(new Color(170,255,153));
		else
			buttonE[x][y].setBackground(new Color(204,247,255));
	}
	private void changeButtonM()
	{
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				String txt = play2.checkBlockE(j,i);
				buttonM[j][i].setText(txt);
				if(txt.equals("X"))
					buttonM[j][i].setBackground(new Color(255,179,204));
				else if(txt.equals("+"))
					buttonM[j][i].setBackground(new Color(234,230,255));
			}
		}
	}
	
	private void updateTable()
	{
		table.setModel(new DefaultTableModel(tableContent(), columnNames));
	}
	
	private void checkEnd()
	{
		if(count1.allDestroyed() && count2.allDestroyed())
		{
			text.append("Game ends in draw!\n");
			endGame = true;
		}
		else if(count1.allDestroyed())
		{
			text.append(play2.getName() + " wins the game!\n");
			endGame = true;
		}
		else if(count2.allDestroyed())
		{
			text.append(play1.getName() + " wins the game!\n");
			endGame = true;
		}
	}
}

