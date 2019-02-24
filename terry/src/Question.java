import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
	private String question, response;
	private List<String> keywords;
	private boolean answered;

	public Question(String question, List<String> keywords) {
		this.question = question;
		Collections.sort(keywords);
		if(keywords.size() > 0)
			this.keywords = keywords;
		else
			this.keywords = null;
		response = null;
		answered = false;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public void setAnswered() {
		answered = true;
	}
	
	public boolean getAnswered() {
		return answered;
	}
	
	public boolean containsKeyword(String mes) {
		if(keywords == null)
			return false;
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(mes.split("\\s+")));
		Collections.sort(list);
		for(int x = 0; x < list.size(); x++) {
			for(int y = 0; y < keywords.size(); y++) {
				if(list.get(x).toLowerCase().equals(keywords.get(y).toLowerCase()))
					return true;
			}
		}
		return false;
		
	}
	public String toString() {
		String build = "";
		build += question;
		if(response == null)
			return build;
		else {
			build += " > " + response;
			return build;
		}
	}
}
