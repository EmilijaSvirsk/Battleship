package FileExceptions;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class BadFileException extends Exception
{
	private static final long serialVersionUID = 8040711871203333974L;
	private int status;
	public BadFileException(int status)
	{
		this.status = status;
	}
	
	public void printMessage()
	{
		JFrame frame = new JFrame();
		JTextArea text = new JTextArea("",10,25);
		frame.setSize(200,100);
		text.setEditable(false);
		if(status == 1)
			text.setText("No file found.");
		else if(status == 2)
			text.setText("File's data is incorrect.");
		frame.add(text);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
