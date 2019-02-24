import java.util.ArrayList;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;




public class runner {

	public static void main(String[] args) throws Exception {
		String path = System.getProperty("user.dir");
		String testCase = "grip"; //pain or grip
		// String path = "";
		QuestionParser parse = new QuestionParser();
		ArrayList<Question> questions = parse.populate(path + "\\" + testCase + "Q.txt");
		System.out.println(questions.toString());
		Thread.sleep(3000);
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("file:///" + path + "\\" + testCase + ".dic"); //pain, grip
		configuration.setLanguageModelPath("file:///" + path + "\\" + testCase + ".lm");

		StringMetric metric = StringMetrics.cosineSimilarity();

		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
		//LiveSpeechRecognizer internal = new LiveSpeechRecognizer(configuration);

		// Start recognition process pruning previously cached data.
		// Start Recognition Process (The bool parameter clears the previous cache if
		// true)
		recognizer.startRecognition(true);

		// Creating SpeechResult object
		SpeechResult result;
		boolean allAnswered = false;
		int answered = 0;
		// Check if recognizer recognized the speech
		while ((result = recognizer.getResult()) != null && !allAnswered) {

			// Get the recognized speech
			String command = result.getHypothesis();
			System.out.println(command); //Prints what it thinks it heard
			for (int x = 0; x < questions.size(); x++) { //Loop through each question
				Question question = questions.get(x);
				
				//Compute how similar the question is to the sentence it heard
				//Necessary if there's a lot of surrounding noise LIKE WHERE WE ARE
				double simu = metric.compare(command.toLowerCase(), question.getQuestion().toLowerCase());
				System.out.println(simu); //Display statistics
				
				//Similarity must be at least (in this case 50%), question must not be answered and contains a keyword
				if (simu > 0.50 && !question.getAnswered() && question.containsKeyword(command)) {
					System.out.println("ENTERED INTO THE RESPONSE");
					//Next input will immediately be registered as the answer
					while ((result = recognizer.getResult()) != null) {
						// Get the recognized speech
						String answer = result.getHypothesis();
						questions.get(x).setResponse(answer);
						answered++;
						break;
					}
					break;
				}
				if(answered == questions.size()) //Exit everything if every question has been answered
					allAnswered = true;
				
			}
			
			System.out.println(questions.toString()); //Print out questions and their respective answers
		}
	}

}
