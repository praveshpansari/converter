import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * The main graphical panel used to display conversion components.
 * 
 * This is the starting point for the assignment.
 * 
 * The variable names have been deliberately made vague and generic, and most
 * comments have been removed.
 * 
 * You may want to start by improving the variable names and commenting what the
 * existing code does.
 * 
 * @author pravesh
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	private Font font, menuFont, bFont;
	private JButton clearButton;
	static JCheckBox reverseBox;
	static JLabel countLabel;
	static int count = 0;
	UnitsPanel unitPanel;
	CurrencyPanel currencyPanel;

	// Function creating and returning a menu bar
	public JMenuBar setupMenu() {

		// Initialize the menu bar
		JMenuBar menuBar = new JMenuBar();

		// The file menu
		// Set value, shortcut and font for the file menu
		JMenu fileMenu = new JMenu("File");
		// Icon from LAF repo JAVA
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.setFont(menuFont);

		// The help menu
		// Set value, shortcut and font for the help menu
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		// Icon from LAF repo JAVA
		helpMenu.setFont(menuFont);

		// The exit menu item
		// Set value, font, tool tip, shortcut, icon and listener for the exit item
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setFont(menuFont);
		exitItem.setToolTipText("Exit the Program");
		exitItem.setMnemonic(KeyEvent.VK_E);
		// Anonymous class used for the listener to terminate the program
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Displays a prompt to close the program with two options
				if (JOptionPane.showConfirmDialog(null, "Exit the program?", "Exit", JOptionPane.YES_NO_OPTION) == 0)
					// Terminates the program if yes is selected
					System.exit(0);
			}
		});
		exitItem.setIcon(UIManager.getIcon("InternalFrame.closeIcon")); // Icon from InternalFrame close button

		// The load menu item
		// Set value, font, tool tip, shortcut, icon and listener for the exit item
		JMenuItem loadItem = new JMenuItem("Load");
		loadItem.setFont(menuFont);
		loadItem.setToolTipText("Load a file");
		loadItem.setIcon(new ImageIcon("icons/New24.gif"));
		loadItem.setMnemonic(KeyEvent.VK_L);
		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				currencyPanel.addItems(file);
			}
		});

		// The about menu item
		// Set value, font, tool tip, shortcut, icon and listener for the about item
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setFont(menuFont);
		aboutItem.setToolTipText("About the Program");
		aboutItem.setMnemonic(KeyEvent.VK_A);
		// Anonymous class used to display an about dialog
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ActionEvent) {
				// Display dialog box with the about information
				JOptionPane.showMessageDialog(null,
						"Converter\nA converter program consisting of several metric and currency conversion units.\nVersion 1.2\nCopyright \u00a9 2018-2019 Pravesh Pansari. All rights reserved.",
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// Icon from LAF repo JAVA
		aboutItem.setIcon(new ImageIcon("icons/About24.gif"));

		// Add menus to menu bar and items to menus
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(loadItem);
		fileMenu.add(exitItem);
		helpMenu.add(aboutItem);

		return menuBar;
	}

	public MainPanel() {

		final int WIDTH = 400, HEIGHT = 560;

		menuFont = new Font("Calibri", 1, 16);
		bFont = new Font("Calibri", 1, 17);
		font = new Font("Calibri", 0, 18);

		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// Set font of dialog boxes using UIManager
		UIManager.put("OptionPane.messageFont", font);
		UIManager.put("OptionPane.buttonFont", bFont);

		unitPanel = new UnitsPanel();
		unitPanel.setBounds(0, 0, WIDTH, 225);

		try {
			currencyPanel = new CurrencyPanel();
			currencyPanel.addItems(new File("currency.txt"));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Please enter a valid file.", "File Error", JOptionPane.ERROR_MESSAGE);
			currencyPanel.addItems(new File(""));
		}
		

		currencyPanel.setBounds(0, 225, WIDTH, 225);
		// The button to reset everything
		// Set value, font, focus, listener, tool tip and position of clear button
		clearButton = new JButton("Clear");
		clearButton.setFont(font);
		clearButton.setFocusable(false);
		// Used anonymous class to reset values
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unitPanel.clear();
				currencyPanel.clear();
				count = 0;
				countLabel.setText("Conversions: 0");
			}
		});
		clearButton.setToolTipText("Clear the Results");
		clearButton.setBounds(WIDTH - 134, 500, WIDTH / 4 + 10, 36);

		// Check box to reverse conversion
		// Set value, font, listener, tool tip and position of reverse Box
		reverseBox = new JCheckBox("Reverse conversion");
		reverseBox.setFont(bFont);
		reverseBox.setToolTipText("Reverse the Conversion Units");
		reverseBox.setIconTextGap(10); // Set the gap between icon and text
		reverseBox.setBackground(null); // Remove background
		reverseBox.setBounds(WIDTH / 4 - 75, 470, 200, 30);

		// The number of conversions performed
		// Set value, font, tool tip and position of the count label
		countLabel = new JLabel("Conversions: 0");
		countLabel.setFont(bFont);
		countLabel.setToolTipText("Number of Conversions Done");
		countLabel.setBounds(WIDTH / 4 - 75, 510, 200, 30);

		// Add the objects to the main panel
		// Unit Panel
		add(unitPanel);
		// Currency Conversion Panel
		add(currencyPanel);
		// Reverse button
		add(reverseBox);
		// Clear Button
		add(clearButton);
		// Count label
		add(countLabel);
	}

}