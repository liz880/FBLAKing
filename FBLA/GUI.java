/**
 * @author Gabrielle King
 * FBLA State, Coding and Programming
 * @since 10 February 2021
 * @version FBLA Quiz, GUI File 1
 */
package king.FBLA;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI implements ActionListener {
	
	static JFrame frame = new JFrame();
	static ArrayList<JPanel> panels 
		= new ArrayList<JPanel>(); 
	static JLabel required = new JLabel("Required*");
	static JLabel valInc = new JLabel();
	static JButton submitButton = new JButton("SUBMIT");
	static boolean validate;
	
	public GUI() {
		
		//Style Commands
		required.setForeground(Color.white);
		valInc.setForeground(Color.white);
		
		//Adding Components
		panels.get(5).add(valInc);
		panels.get(5).add(submitButton);

	}
	
	//creates red border around components
	static Border invalidInput = 
		BorderFactory.createLineBorder(Color.RED, 5);
			
	//creates (invisible) black border around components
	static Border validInput = 
		BorderFactory.createLineBorder(Color.BLACK, 0);

	/**
	 * Put it in a method so I could control when the window opened
	 *
	 * Main part of the GUI that determines how it responds
	 */
	public void displayGUI() {
		
		/*panel.setBorder(BorderFactory.
				createEmptyBorder(150, 150, 150, 150));*/
		frame.setLayout(new GridLayout(0,1));
		for (int z = 0; z < 6; z++) {
			panels.get(z).setLayout(new GridLayout(0,1));
			panels.get(z).setBackground(Color.decode("21147"));
			frame.add(panels.get(z), BorderLayout.CENTER);
		}
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		submitButton.addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int allAns = 0;
		
		for (int y = 0; y < 5; y++) {
			
			String ansCheck = Main.textBoxes
					.get(Main.ansGetPos.get(y)).getText();
			String multiStep;
			validate = Main.validation(ansCheck);

			switch (Main.questionList
					.get(y).getQuestionType()) {
			
			//use this to get values
			case 1:
				//Validate to see if all ?s answered
				if (validate) {
					 Main.textBoxes
						.get(Main.ansGetPos.get(y)).setBorder(validInput);
					valInc.setText("");
					
					Main.recordAns(Main.questionList
							.get(y), ansCheck);
					Main.questionList.get(y)
							.writeAnswers(ansCheck, y);
				} else {
					valInc.setText("Please answer the "
							+ "question in the selected field(s)");
					 Main.textBoxes
						.get(Main.ansGetPos.get(y)).setBorder(invalidInput);
					allAns++;
				}
					
				break;
			case 2:
				if (validate) {
					 Main.textBoxes
						.get(Main.ansGetPos.get(y)).setBorder(validInput);
					valInc.setText("");
					
					Main.recordAns(Main.questionList
							.get(y), ansCheck);
					Main.questionList.get(y)
					.writeAnswers(ansCheck, y);
				} else {
					valInc.setText("Please answer the "
							+ "question in the selected field(s)");
					 Main.textBoxes
						.get(Main.ansGetPos.get(y)).setBorder(invalidInput);
					allAns++;
				}
				break;
			case 3:
				
				String dropCheck = 
				(String) Main.dropDowns
				.get(Main.ansGetPos.get(y)).getSelectedItem();
				
				validate = Main.validation(dropCheck);
				if (validate) {
					
					Main.dropDowns
					.get(Main.ansGetPos.get(y)).setBorder(validInput);
					valInc.setText("");
					
					Main.recordAns(Main.questionList
							.get(y), ansCheck);
					Main.questionList.get(y)
					.writeAnswers(ansCheck, y);
				} else {
					Main.dropDowns
					.get(Main.ansGetPos.get(y)).setBorder(invalidInput);
					valInc.setText("Please answer the "
							+ "question in the selected field(s)");
					allAns++;
				}
				break;
			default:
				if (validate) {
					 Main.textBoxes
						.get(Main.ansGetPos.get(y)).setBorder(validInput);
					multiStep =  Main.textBoxes
							.get(Main.ansGetPos.get(y)).getText();
					valInc.setText("");
					switch (multiStep) {
						case "a":
							ansCheck = Main.questionList
								.get(y).getChoiceOne();
							break;
						case "b":
							ansCheck = Main.questionList
								.get(y).getChoiceTwo();
							break;
						case "c":
							ansCheck = Main.questionList
								.get(y).getChoiceThree();
							break;
						default:
							ansCheck = Main.questionList
								.get(y).getChoiceFour();
					}
					
					Main.recordAns(Main.questionList
							.get(y), ansCheck);
					Main.questionList.get(y)
							.writeAnswers(ansCheck, y);
				} else {
					valInc.setText("Please answer the "
							+ "question in the selected field(s)");
					 Main.textBoxes
						.get(Main.ansGetPos.get(y)).setBorder(invalidInput);
					
					 allAns++;
				}
			}
		}
		
		if (allAns == 0) {
			Main.questionList.get(0).readAndDisplayAnswers();
		}
	}
}