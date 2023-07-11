import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
public class UnitsPanel extends JPanel {

	// List of conversion units
	private final static String[] list = { "Miles/Nautical Miles", "Acres/Hectares", "Mph/Kmh", "Yard/Meters",
			"Celsius/Fahrenheit", "Degrees/Radians" };
	// Input, labels, options, counter and result
	JTextField inputValue;
	JLabel resultValue;
	private JLabel inputLabel;
	private JComboBox<String> conversionList;
	private JButton convertButton;
	private Font font, menuFont, bFont;

	// Constructor for the panel
	public UnitsPanel() {

		// Constants for width and height of the panel
		final int WIDTH = 400, HEIGHT = 220;

		// Definition of different font styles
		menuFont = new Font("Calibri", 1, 16);
		bFont = new Font("Calibri", 1, 17);
		font = new Font("Calibri", 0, 18);

		// Set layout, size and background of the panel
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.LIGHT_GRAY);
		setLayout(null); // Set layout to null for using absolute positioning

		// Set font of dialog boxes using UIManager
		UIManager.put("OptionPane.messageFont", font);
		UIManager.put("OptionPane.buttonFont", bFont);

		// Titled Border
		// Create Titled Border with border style etched and gray color
		Border border = new TitledBorder(new EtchedBorder(), "Metrics", TitledBorder.DEFAULT_POSITION,
				TitledBorder.DEFAULT_JUSTIFICATION, menuFont, Color.GRAY);
		// Set margin around the title border
		// Added empty border of size 8 using the compound border
		setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), border));

		// Initialize a action listener for conversion
		ActionListener listener = new ConvertListener();

		// Displays the available conversion units
		// Set value, listener, font, tool tip and position of combo box
		conversionList = new JComboBox<String>(list);
		conversionList.addActionListener(listener); // convert values when option changed
		conversionList.setFont(font);
		conversionList.setToolTipText("Select the Conversion Units");
		conversionList.setBounds(WIDTH / 2 - 150, 45, WIDTH - 100, 28);

		// A prompt label
		// Set value, font and position of input label
		inputLabel = new JLabel("Enter value:");
		inputLabel.setFont(font);
		inputLabel.setBounds(WIDTH / 2 - 150, 95, 150, 30);

		// The text field for the input value
		// Set value, font, listener, border, tool tip and position of the text field
		inputValue = new JTextField(5);
		inputValue.setFont(font);
		inputValue.addActionListener(listener);
		inputValue.setBorder(null);
		inputValue.setToolTipText("Input a Number");
		inputValue.setBounds(WIDTH / 2 - 50, 95, 80, 25);

		// A label for calculated value
		// Set value, font, tool tip and position of result label
		resultValue = new JLabel("---");
		resultValue.setFont(font);
		resultValue.setToolTipText("Converted Result");
		resultValue.setBounds(WIDTH / 2 + 100, 95, WIDTH / 2, 30);

		// The button used for conversion
		// Set value, font, focus, listener, tool tip and position of button
		convertButton = new JButton("Convert");
		convertButton.setFont(font);
		convertButton.setFocusable(false);
		convertButton.addActionListener(listener); // convert values when pressed
		convertButton.setToolTipText("Convert Input Value");
		convertButton.setBounds(WIDTH / 2 - 55, 145, WIDTH / 4 + 10, 36);

		// Add all the components to the panel
		add(conversionList);
		add(inputLabel);
		add(inputValue);
		add(resultValue);
		add(convertButton);
	}

	public void clear() {
		// Reset input, result, count
		inputValue.setText(null);
		resultValue.setText("---");
	}

	public void invoke() {
		new ConvertListener().actionPerformed(new ActionEvent(this, 2, "reversed"));
	}

	// A listener class implementing the ActionListener interface
	private class ConvertListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			// Store the inputValue as string with all leading and trailing spaces removed
			String text = inputValue.getText().trim();

			// A format for displaying two decimal digits
			DecimalFormat df = new DecimalFormat("#.##");

			// If a user has given an input
			if (text.isEmpty() == false) {

				try {
					// Store the input as double
					double value = Double.parseDouble(text);

					// the factor applied during the conversion
					double factor = 0;

					// the offset applied during the conversion.
					double offset = 0;

					// Setup the correct factor/offset values depending on required conversion
					switch (conversionList.getSelectedIndex()) {

					case 0: // miles/nautical miles
						factor = 1 / 1.151;
						break;

					case 1: // acres/hectares
						factor = 1 / 2.471;
						break;

					case 2: // mph/kmh
						factor = 1.609;
						break;

					case 3: // yards/metres
						factor = 1 / 1.094;
						break;

					case 4: // celsius/fahrenheit
						factor = 9 / 5.0;
						offset = 32;
						break;

					case 5: // degrees/radians
						factor = Math.PI / 180;
						break;
					}

					// Increase the number of conversion each time
					MainPanel.count++;

					// If the conversion units reversed
					if (MainPanel.reverseBox.isSelected())
						// Set corresponding value of result for reverse type after formatting
						resultValue.setText(df.format((value / factor - offset)));
					else
						// Set corresponding value of result for normal type after formatting
						resultValue.setText(df.format((factor * value + offset)));

					// Set the count label to show the count value
					MainPanel.countLabel.setText("Conversions: " + Integer.toString(MainPanel.count));

					// Catch the NumberFormatException
				} catch (NumberFormatException e) {
					// Display a dialog box showing the error message
					JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				// If the input box is empty
				if (event.getSource() != conversionList && event.getSource() != MainPanel.reverseBox)
					// Display a dialog box showing a error message
					JOptionPane.showMessageDialog(null, "Please input a value.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
