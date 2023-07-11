import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class CurrencyPanel extends JPanel {
	private String[] list;
	private JComboBox<String> currencyList = new JComboBox<String>();
	JLabel resultValue;
	JTextField inputValue;
	private JButton convertButton;
	private Font font, menuFont;
	private Scanner scan;
	private List<Currency> currencies;

	// Add items from file to the combobox
	public void addItems(File file) {
		currencies = new ArrayList<Currency>();
		String error = "";
		boolean hasError = false;
		int lineNo = 0;
		try {
			scan = new Scanner(file, "UTF-8");

			// Read each line and check for errors
			while (scan.hasNextLine()) {
				String[] line = scan.nextLine().split(",");
				lineNo++;
				if (line.length != 3) {
					error += "101 Invalid number of arguments at Line " + lineNo + "\n";
					hasError = true;
				} else {
					try {
						currencies
								.add(new Currency(line[0].trim(), line[2].trim(), Double.parseDouble(line[1].trim())));
					} catch (NumberFormatException e) {
						hasError = true;
						error += "103 Invalid type for Factor at Line " + lineNo + "\n";
					}
				}
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Please enter a valid file.", "File Error", JOptionPane.ERROR_MESSAGE);
		}

		// Show errors if errors found
		if (hasError)
			JOptionPane.showMessageDialog(null, error, "Errors in file", JOptionPane.ERROR_MESSAGE);

		int index = 0;
		list = new String[currencies.size()];

		while (index < currencies.size()) {
			list[index] = currencies.get(index).getName();
			index++;
		}
		// Set the combobox model
		currencyList.setModel(new DefaultComboBoxModel<String>(list));
	}

	public CurrencyPanel() throws FileNotFoundException {

		// Constants for width and height of the panel
		final int WIDTH = 400, HEIGHT = 220;

		// Definition of different font styles
		menuFont = new Font("Calibri", 1, 16);
		font = new Font("Calibri", 0, 18);

		// Set layout, size and background of the panel
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.LIGHT_GRAY);
		setLayout(null); // Set layout to null for using absolute positioning

		// Titled Border
		// Create Titled Border with border style etched and gray color
		Border border = new TitledBorder(new EtchedBorder(), "Currencies", TitledBorder.DEFAULT_POSITION,
				TitledBorder.DEFAULT_JUSTIFICATION, menuFont, Color.GRAY);
		// Set margin around the title border
		// Added empty border of size 8 using the compound border
		setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), border));

		ActionListener listener = new ConvertListener();
		// Displays the available conversion units
		// Set value, listener, font, tool tip and position of combo box
		currencyList.addActionListener(listener); // convert values when option changed
		currencyList.setFont(font);
		currencyList.setToolTipText("Select the Conversion Units");
		currencyList.setBounds(WIDTH / 2 - 150, 40, WIDTH - 100, 28);

		// A prompt label
		// Set value, font and position of input label
		JLabel inputLabel = new JLabel("Enter value:");
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
		add(currencyList);
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

	private class ConvertListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			// Store the inputValue as string with all leading and trailing spaces removed
			String text = inputValue.getText().trim();
			// A format for displaying two decimal digits
			DecimalFormat df = new DecimalFormat("#.##");

			// If a user has given an input
			if (text.isEmpty() == false) {

				try {
					int index = currencyList.getSelectedIndex();
					// Store the input as double
					double value = Double.parseDouble(text);

					// the factor applied during the conversion
					double factor = currencies.get(index).getFactor();

					// symbol
					String symbol = currencies.get(index).getSymbol();

					// Increase the number of conversion each time
					MainPanel.count++;

					// If the conversion units reversed
					if (MainPanel.reverseBox.isSelected())
						// Set corresponding value of result for reverse type after formatting
						resultValue.setText("£" + df.format(value / factor));
					else
						resultValue.setText(symbol + df.format(factor * value));

					// Set the count label to show the count value
					MainPanel.countLabel.setText("Conversions: " + Integer.toString(MainPanel.count));

				} catch (NumberFormatException e) {
					// Display a dialog box showing the error message
					JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				// If the input box is empty
				if (event.getSource() != currencyList && event.getSource() != MainPanel.reverseBox)
					// Display a dialog box showing a error message
					JOptionPane.showMessageDialog(null, "Please input a value.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}