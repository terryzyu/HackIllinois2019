import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;




public class runner {

	public static void main(String[] args) throws Exception {
		String path = System.getProperty("user.dir");
		// String path = "";
		QuestionParser parse = new QuestionParser();
		ArrayList<Question> questions = parse.populate(path + "\\painQ.txt");
		System.out.println(questions.toString());
		Thread.sleep(3000);
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("file:///" + path + "\\pain.dic");
		configuration.setLanguageModelPath("file:///" + path + "\\pain.lm");

		StringMetric metric = StringMetrics.cosineSimilarity();

		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
		//LiveSpeechRecognizer internal = new LiveSpeechRecognizer(configuration);

		// Start recognition process pruning previously cached data.
		// Start Recognition Process (The bool parameter clears the previous cache if
		// true)
		recognizer.startRecognition(true);

		// Creating SpeechResult object
		SpeechResult result;

		// Check if recognizer recognized the speech
		while ((result = recognizer.getResult()) != null) {

			// Get the recognized speech
			String command = result.getHypothesis();
			System.out.println(command);
			for (int x = 0; x < questions.size(); x++) {
				Question question = questions.get(x);
				double simu = metric.compare(command.toLowerCase(), question.getQuestion().toLowerCase());
				System.out.println(simu);
				if (simu > 0.50 && !question.getAnswered() && question.containsKeyword(command)) {
					System.out.println("ENTERED INTO THE RESPONSE");
					while ((result = recognizer.getResult()) != null) {
						// Get the recognized speech
						String answer = result.getHypothesis();
						questions.get(x).setResponse(answer);
						break;

					}
					break;
				}
			}
			
			System.out.println(questions.toString());
		}
	}

}
