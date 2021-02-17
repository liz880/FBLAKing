/**
 * @author Gabrielle King
 * FBLA State, Coding and Programming
 * @since 10 February 2021
 * @version FBLA Quiz, Class File
 */
package king.FBLA;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


public class Question {
	
	//Static Vars
	static File quizResults = new File("gradedQuiz.txt");
	static Scanner readFile;
	static FileWriter firstEntry;
	static PrintWriter recordQ;
	
	//Fields 
	private int chooseQ;
	private int questionType;
	private String question;
	private String choiceOne;
	private String choiceTwo;
	private String choiceThree;
	private String choiceFour;
	private String correctAns;
		
	//Constructors, Constructor Chaining used
	
	//Partial 1
	public Question(int choose, int type, 
			String que, String correct) {
		
		//Fill-In-The-Blank
		this(choose, type, que, "n/a",
				"n/a", "n/a", "n/a", correct);
	}
		
	//Partial 2
	public Question(int choose, int type, 
			String que, String one, 
			String two, String correct) {
		
		//True-False
		this(choose, type, que, one, two,
				"n/a", "n/a", correct);
	}
	
	//Full
	public Question(int choose, int type, 
				String que, String one, 
				String two, String three, 
				String four, String correct) {
		
		//Multi-Choice & Drop-Down
		super();
		this.chooseQ = choose;
		this.questionType = type;
		this.question = que;
		this.choiceOne = one;
		this.choiceTwo = two;
		this.choiceThree = three;
		this.choiceFour = four;
		this.correctAns = correct;
		
		if (!quizResults.exists()) {
			try {
				quizResults.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	//Getters and Setters
	public int getChooseQ() {
		return this.chooseQ;
	}
	public void setChooseQ(int choose) {
		this.chooseQ = choose;
	}
		
	public int getQuestionType() {
		return this.questionType;
	}
	public void setQuestionType(int type) {
		this.questionType = type;
	}
		
	public String getQuestion() {
		return this.question;
	}
	public void setQuestion(String que) {
		this.question = que;
	}
		
	public String getChoiceOne() {
		return this.choiceOne;
	}
	public void setChoiceOne(String one) {
		this.choiceOne = one;
	}
		
	public String getChoiceTwo() {
		return this.choiceTwo;
	}
	public void setChoiceTwo(String two) {
		this.choiceTwo = two;
	}
		
	public String getChoiceThree() {
		return this.choiceThree;
	}
	public void setChoiceThree(String three) {
		this.choiceThree = three;
	}
		
	public String getChoiceFour() {
		return this.choiceFour;
	}
	public void setChoiceFour(String four) {
		this.choiceFour = four;
	}
		
	public String getCorrectAns() {
		return this.correctAns;
	}
	public void setCorrectAns(String correct) {
		this.correctAns = correct;
	}
	
	//Methods
	public void writeAnswers(String userResponse, int pos) {
		String questionEval = "Question " 
				+ (pos+1) + ": \n"  
				+ Main.questionList.get(pos)
				.getQuestion() + "\n\n"
				+ "Correct Answer: " + Main.questionList
				.get(pos).getCorrectAns() 
				+ "\n" + "Your Response: " 
				+ userResponse + "\n\n\n\n";
		
		try {
			
			if (pos == 0){
				
				//writes to files
				firstEntry = new FileWriter(quizResults);
				firstEntry.write(questionEval);
				firstEntry.close();
				
				//even in 'write', 'PrintWriter' appends
				//have to use 'FileWriter'
				
			} else {
				
				//writes/appends to files
				recordQ = new PrintWriter
						(new FileWriter(quizResults, true));		
				recordQ.append(questionEval);
				
				
				if (pos == 4) {
					String addScore = "Score: " 
					+ Main.totalScore + "/5";
					recordQ.append(addScore);
				}
				
				recordQ.close();
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void readAndDisplayAnswers() {
		ArrayList<JLabel> readResults 
			= new ArrayList<JLabel>();
		
		try {
			readFile = new Scanner(quizResults);
			
			//will only make a pass if it has lines to read
			while (readFile.hasNextLine()) {
				readResults.add(new JLabel
						(readFile.nextLine()));
		    }
			readFile.close();
		} catch (IOException e){
			
			e.printStackTrace();
		}
		
		//All item vars created in one place
		JFrame guiFrame;
		JPanel guiPanel;
		
		guiFrame = new JFrame();
		guiPanel = new JPanel();
		
		for (int x = 0; x < readResults.size(); x++) {
			
			//Style Commands
			readResults.get(x).setForeground(Color.white);
			
			//Add Components
			guiPanel.add(readResults.get(x));
			
		}
		
		guiPanel.setBackground(Color.decode("21147"));
		guiPanel.setBorder(BorderFactory.
				createEtchedBorder());
		guiPanel.setBorder
			(BorderFactory.createEmptyBorder
			(100,100,100,100));
		guiPanel.setLayout(new GridLayout(0,1));
				
		guiFrame.add(guiPanel, BorderLayout.CENTER);	
		guiFrame.setTitle("Quiz Results");
		guiFrame.pack();
				
		//shows the GUI
		guiFrame.setVisible(true);
				
		//stops running when GUI closed
		guiFrame.setDefaultCloseOperation
		(JFrame.EXIT_ON_CLOSE);
	}
	
	//Display Methods 
		
	@Override
	public String toString() {
			
		String str = "Chose: " + this.getChooseQ() + "\n"
			+ "Type: " + this.getQuestionType() + "\n"
			+ "Question: " + this.getQuestion() + "\n"
			+ "1: " + this.getChoiceOne() + "\n"
			+ "2: " + this.getChoiceTwo() + "\n"
			+ "3: " + this.getChoiceThree() + "\n"
			+ "4: " + this.getChoiceFour() + "\n"
			+ "Correct: " + this.getCorrectAns() + "\n";
		return str;
	}
}
