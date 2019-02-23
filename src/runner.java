import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class runner {

	public static void main(String[] args) throws Exception {

		Configuration configuration = new Configuration();
		String path = "file:///C:/Users/gg/Desktop/VOICE/";
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath(path + "2803.dic");
		configuration.setLanguageModelPath(path + "2803.lm");
		
		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
		// Start recognition process pruning previously cached data.
		System.out.println("Just start");
		recognizer.startRecognition(true);
		System.out.println("After");
		SpeechResult result;  //Check if recognizer recognized the speech
        while ((result = recognizer.getResult()) != null) {
 
            //Get the recognized speech
            String command = result.getHypothesis();
            //Match recognized speech with our commands
            if(command.equalsIgnoreCase("open file manager")) {
                System.out.println(command);
            } else if (command.equalsIgnoreCase("close file manager")) {
                System.out.println(command);
            } else if (command.equalsIgnoreCase("open browser")) {
                System.out.println(command);
            } else if (command.equalsIgnoreCase("close browser")) {
                System.out.println(command);
            }
        }

	}
}