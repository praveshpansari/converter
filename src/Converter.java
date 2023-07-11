import javax.swing.JFrame;

/**
 * The main driver program for the GUI based conversion program.
 * 
 * @author pravesh
 */
public class Converter {

	public static void main(String[] args) {

		// Create a frame
		JFrame frame = new JFrame("Converter");
		// Set exit on close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create an instance of MainPanel
		MainPanel panel = new MainPanel();
		// Create menu bar using the setupMenu function
		frame.setJMenuBar(panel.setupMenu());
		// Set the window as unresizable
		frame.setResizable(false);
		// Add panel to frame
		frame.getContentPane().add(panel);
		// Set the window size according to components
		frame.pack();
		// Set visibility to true
		frame.setVisible(true);
	}
}
