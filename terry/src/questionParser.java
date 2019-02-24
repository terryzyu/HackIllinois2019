import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionParser {
	
	public ArrayList<Question> populate(String file) {
		ArrayList<Question> questions = new ArrayList<Question>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String question;
			while((question = in.readLine()) != null) {
				int keywordIndex = question.toLowerCase().indexOf("keyword");
				Question pair; //default assumes no keyword(s)
				
				ArrayList<String> keywords = new ArrayList<String>();
				if(keywordIndex > 0) {
					String words = question.substring(keywordIndex + 9);
					keywords.addAll(Arrays.asList(words));
					question = question.substring(0, keywordIndex - 1);
					pair = new Question(question, keywords);
				}
				else
					pair = new Question(question, keywords);
				questions.add(pair);
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return questions;
	}

}
