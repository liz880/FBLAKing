/**
 * @author Gabrielle King
 * FBLA State, Coding and Programming
 * @since 10 February 2021
 * @version FBLA Quiz, Main File
 */
package king.FBLA;
import java.util.*;
import javax.swing.*;

import king.troubleshoot.GUITwo;

import java.awt.Color;
import java.sql.*;

public class Main {

	static GUITwo guiTwo;
	static GUI guiOne;
	static Connection dbConnect;
	static List<Question> questionList = 
			new ArrayList<Question>();
	static List<Integer> ansGetPos = 
			new ArrayList<Integer>();
	static ArrayList<JTextField> textBoxes = 
			new ArrayList<JTextField>();
	static ArrayList<JComboBox> dropDowns = 
			new ArrayList<JComboBox>();
	static int totalScore = 0;
	static boolean answered;
	
	public static void setQuiz() {
		JTextField fillField;
		int dropCount = 0;
		JLabel format;
		
		for (int b = 0; b < 5; b++) {
			switch (Main.questionList
					.get(b).getQuestionType()) {
				case 1:
					
					//Fill-In-The-Blank
					format = new JLabel(Main.questionList
							.get(b).getQuestion());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel("Type the answer"
							+ " in the Text Box below. "
							+ "Capitalize all non-article words "
							+ "and do not use numbers or special "
							+ "characters");
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					fillField = new JTextField();
					fillField.setBounds(5, 5, 5, 5);
					textBoxes.add(fillField);
					GUI.panels.get(b).add(textBoxes.get(textBoxes.size()-1));
					GUI.panels.get(b).setForeground(Color.WHITE);
					
					ansGetPos.add(textBoxes.size()-1);
					break;
				case 2:
					
					//True-False
					format = new JLabel(Main.questionList
							.get(b).getQuestion());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel(Main.questionList
							.get(b).getChoiceOne());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel(Main.questionList
							.get(b).getChoiceTwo());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					fillField = new JTextField();
					fillField.setBounds(5, 5, 5, 5);
					textBoxes.add(fillField);
					format = new JLabel("Type the answer in the "
							+ "Text Box below as "
							+ "it is written above.");
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					GUI.panels.get(b).add(textBoxes.get(textBoxes.size()-1));
					
					ansGetPos.add(textBoxes.size()-1);
					
					break;
				case 3:
					//Drop-Down
					String[] fillTable = {"", Main.questionList
							.get(b).getChoiceOne(), Main.questionList
							.get(b).getChoiceTwo(), Main.questionList
							.get(b).getChoiceThree(), Main.questionList
							.get(b).getChoiceFour()};
					
					format = new JLabel(Main.questionList
							.get(b).getQuestion());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel("Select the answer "
							+ "in the drop down below");
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					dropDowns.add(new JComboBox(fillTable));
					GUI.panels.get(b).add(dropDowns.get(dropCount));
					
					ansGetPos.add(dropCount);
					dropCount++;
					break;
				default:
					
					//Multi-choice
					format = new JLabel(Main.questionList
							.get(b).getQuestion());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel(Main.questionList
							.get(b).getChoiceOne());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel(Main.questionList
							.get(b).getChoiceTwo());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel(Main.questionList
							.get(b).getChoiceThree());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					format = new JLabel(Main.questionList
							.get(b).getChoiceFour());
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					
					format = new JLabel("Type the answer choice "
							+ "you select "
							+ "as 'a', 'b', 'c', or 'd' in the "
							+ "Text Box below.");
					format.setForeground(Color.WHITE);
					GUI.panels.get(b).add(format);
					textBoxes.add(new JTextField());
					
					GUI.panels.get(b).add(textBoxes.get(textBoxes.size()-1));
					ansGetPos.add(textBoxes.size()-1);
			}
		}
		
	}
	public static boolean validation(String strAns) {
		
		if(strAns.equals("")) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public static void recordAns(Question checkAns, 
			String userAns) {
		String sqlUpdate;
		PreparedStatement alterData;
		
		try {
			
			//This is normally stored in an external resource file
			dbConnect = DriverManager.getConnection
					("jdbc:mysql://0.0.0.0:3306/quiz", 
							"mysql_user", "password");
			
			//Adds user's answer to the table
			sqlUpdate = "UPDATE fbla_quiz "
	                + "SET userAnswer = ? "
	                + "WHERE chooseQ = " + checkAns.getChooseQ();
			alterData = dbConnect.prepareStatement(sqlUpdate);
			alterData.setString(1, userAns);
			alterData.executeUpdate();
			
			sqlUpdate = "UPDATE fbla_quiz "
	                + "SET qScore = ? "
	                + "WHERE chooseQ = " + checkAns.getChooseQ();
			alterData = dbConnect.prepareStatement(sqlUpdate);
			
			if (userAns.equals(checkAns.getCorrectAns())) {
				alterData.setInt(1, 1);
				alterData.executeUpdate();
				totalScore++;
			} else {
				alterData.setInt(1, 0);
				alterData.executeUpdate();
			}
			
			alterData.close();
			dbConnect.close();
			
			
		} catch (SQLException e) {};
	}
	
	/**
	 * @param startQuiz variable used to trigger
	 * 	GUI and its methods
	 * @param getQs int that selects what question to
	 *  send to the GUI
	 * @param chosen stores values, makes sure there are 
	 * 	no repeated questions
	 */
	public static void main(String[] args) {
		for (int c = 0; c < 6; c++) {
			guiOne.panels.add(new JPanel());
		}
		
		guiOne = new GUI();
		int counter = 0;
		int getQs;
		List<Integer> chosen = new ArrayList<Integer>();
		
		Statement task;
		ResultSet results;
		String query;
		
		do {
			getQs = (int) (Math.random()*50);
			
			if (!chosen.contains(getQs)) {
				chosen.add(getQs);
			} 
			
		//Is a 'do-while' so the integer to stop is equal
		//to the desired size
		} while (chosen.size() < 5);
		
		/**
		 * Selection Sort that puts integers 
		 * values in least -> greatest to
		 * select the questions from database
		 */
		int fill;
		int index;
		for (int pos = 1; pos < chosen.size(); pos++) {
			fill  = chosen.get(pos);
			index = pos;
			
			while ((index > 0) && (fill < chosen.get(index - 1))){
				chosen.set(index, chosen.get(index - 1));
				index--;
			}
			chosen.set(index, fill);
		}

		/**
		 * Grabs the db data, uses it to
		 * create 'Question's, and adds 
		 * these values to an ArrayList
		*/
		try {
			dbConnect = DriverManager.getConnection
					("jdbc:mysql://0.0.0.0:3306/quiz", 
							"mysql_user", "password");
			
			query = "SELECT * FROM fbla_quiz";
			task = dbConnect.createStatement();
			results = task.executeQuery(query);
			
			while (counter < chosen.size()) {
				
				//moves the pointer down a row 
				results.next();
				if (results.getInt("chooseQ") 
						== chosen.get(counter)) {

					/**
					 * Looks at the type to format the 
					 * field values
					*/
					switch (results.getInt("questionType")) {
					
						//calls constructors based on question type
						case 1:
							//fill-in-the-blank
							questionList.add(new Question
								(results.getInt("chooseQ"), 
								results.getInt("questionType"), 
								results.getString("question"), 
								results.getString
									("correctAnswer")));
							break;
						case 2:
							//True-False
							questionList.add(new Question
								(results.getInt("chooseQ"), 
								results.getInt("questionType"), 
								results.getString("question"), 
								results.getString("choiceOne"),
								results.getString("choiceTwo"),
								results.getString
									("correctAnswer")));
							break;
						default:
							//Drop-Downs and Multi-Choice
							questionList.add(new Question
								(results.getInt("chooseQ"), 
								results.getInt("questionType"), 
								results.getString("question"), 
								results.getString("choiceOne"),
								results.getString("choiceTwo"),
								results.getString("choiceThree"),
								results.getString("choiceFour"),
								results.getString
									("correctAnswer")));
					}
					
					counter++;
				}
				
			}
			dbConnect.close();
		} catch (SQLException e) {};
		
		setQuiz();
		guiOne.displayGUI();
	}
}