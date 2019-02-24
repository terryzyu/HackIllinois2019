import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.util.Locale; 
import javax.speech.Central; 
import javax.speech.synthesis.Synthesizer; 
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class runner {
    
    public static ArrayList<String> key = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        // set key word string
        File keyFile = new File("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt");
        Scanner in = new Scanner(keyFile);
        key.add("start");
        key.add("end");
        while(in.hasNext()){
            key.add(in.next());
        }
        
        
        // data structure to store patient data
        HashMap<String, String> dtabase = new HashMap<String, String>();
        
        // set property as Kevin Dictionary 
        System.setProperty("freetts.voices", 
            "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");  

        // Register Engine 
        Central.registerEngineCentral 
            ("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral"); 

        // Create a Synthesizer 
        Synthesizer synthesizer =                                          
            Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));      

        // Allocate synthesizer 
        synthesizer.allocate();         

        // Resume Synthesizer 
        synthesizer.resume();     
    
        // Setting for voice recognition
        Configuration configuration = new Configuration();
        String path = "file:///C:/Users/Kangbok Lee/Desktop/";
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath(path + "3509.dic");
        configuration.setLanguageModelPath(path + "3509.lm");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        synthesizer.speakPlainText("speak now", null);
        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        SpeechResult result;  //Check if recognizer recognized the speech
        while ((result = recognizer.getResult()) != null) {
 
            //Get the recognized speech
            String command = result.getHypothesis();
            //Match recognized speech with our commands
            String[] parts = command.split(" ");

            if(parts[0].equalsIgnoreCase(key.get(0))) {
                for(int i = 1; i < parts.length; i++){
                    if(parts[1].equalsIgnoreCase(key.get(1))){
                        break;
                    }
                    if(key.contains(parts[i])){
                        String temp = "";
                        int j = i + 1;
                        while(!key.contains(parts[j]) && j < parts.length && !parts[j].equalsIgnoreCase(key.get(1))){
                            temp += parts[j] + " ";
                            j++;
                        }
                        dtabase.put(parts[i], temp);
                        if(j< parts.length){
                            if(parts[j].equalsIgnoreCase(key.get(1))){
                                i = j;
                            }
                        }
                        // speaks the given text until queue is empty. 
                        synthesizer.speakPlainText(temp, null);          
                        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY); 
                        
                    }
                }
            }
        }
        // Deallocate the Synthesizer. 
        synthesizer.deallocate();
    }
    
    public static void addKey(String s) throws IOException{
        if(!key.contains(s)){
            key.add(s);
            PrintWriter out = new PrintWriter(new FileWriter("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt", true));
            
            out.print(s + " ");
            out.close();
        }
    }
    
    public static void deleteKey(String s) throws IOException{
        key.remove(s);
        PrintWriter out = new PrintWriter(new FileWriter("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt", false));
        
        for(int i = 2; i < key.size(); i ++){
            out.print(key.get(i)+ " ");
        }
        out.close();
    }
    
    public static double parseIntDouble(String input){
		String result = "";
		double out = 0;
		String[] DIGITS = {"ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE",null};
		String[] TENS = {null, "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY",null};
		String[] TEENS = {"TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};
		String[] MAGNITUDES = {"HUNDRED", "THOUSAND", "MILLION", "POINT"};
		String[] ZERO = {"ZERO", "OH"};
		
		String[] decimal = input.split(MAGNITUDES[3]);//splits by "point" if theres a decimal
		String[] millions = decimal[0].split(MAGNITUDES[2]);//splits by "million"

		for(int i = 0; i < millions.length; i++){
			String[] thousands = millions[i].split(MAGNITUDES[1]);

			for(int j = 0; j < thousands.length; j++){
				int[] triplet = {0, 0, 0};
				StringTokenizer set = new StringTokenizer(thousands[j]);

				if(set.countTokens() == 1){//one token in thousands
					String uno = set.nextToken();
					triplet[0] = 0;
					
					for(int k = 0; k < DIGITS.length; k++){
						
						if(uno.equals(DIGITS[k])){
							triplet[1] = 0;
							triplet[2] = k + 1;
						}
						
						if(uno.equals(TEENS[k])){
							triplet[1] = 1;
							triplet[2] = k;
						}
					}
				}


				else if(set.countTokens() == 2){//two tokens in thousands
				
					String uno = set.nextToken();
					String dos = set.nextToken();
					
					if(dos.equals(MAGNITUDES[0])){//if token is hundreds
					
						for(int k = 0; k < DIGITS.length; k++){
							
							if(uno.equals(DIGITS[k])){
								triplet[0] = k + 1;
								triplet[1] = 0;
								triplet[2] = 0;
							}
						}
					}
					
					else{
						triplet[0] = 0;
						
						for(int k = 0; k < DIGITS.length; k++){
							
							if(uno.equals(TENS[k])){
								triplet[1] = k + 1;
							}
							
							if(dos.equals(DIGITS[k])){
								triplet[2] = k + 1;
							}
						}
					}
				}

				else if(set.countTokens() == 3){//three tokens in thousands
					String uno = set.nextToken();
					String dos = set.nextToken();
					String tres = set.nextToken();
					
					for(int k = 0; k < DIGITS.length; k++){
						
						if(uno.equals(DIGITS[k])){
							triplet[0] = k + 1;
						}
						
						if(tres.equals(DIGITS[k])){
							triplet[1] = 0;
							triplet[2] = k + 1;
						}
						
						if(tres.equals(TENS[k])){
							triplet[1] = k + 1;
							triplet[2] = 0;
						}
					}
				}

				else if(set.countTokens() == 4){//four tokens
					String uno = set.nextToken();
					String dos = set.nextToken();
					String tres = set.nextToken();
					String cuatro = set.nextToken();
					
					for(int k = 0; k < DIGITS.length; k++){
						
						if(uno.equals(DIGITS[k])){
							triplet[0] = k + 1;
						}
						
						if(cuatro.equals(DIGITS[k])){
							triplet[2] = k + 1;
						}
						
						if(tres.equals(TENS[k])){
							triplet[1] = k + 1;
						}
					}
				}
				
				else{
					triplet[0] = 0;
					triplet[1] = 0;
					triplet[2] = 0;
				}

				result = result + Integer.toString(triplet[0]) + Integer.toString(triplet[1]) + Integer.toString(triplet[2]);
			}
			out = Integer.parseInt(result);
		}

		if(decimal.length > 1){  //The number is a decimal
			StringTokenizer decimalDigits = new StringTokenizer(decimal[1]);
			result = result + ".";
			
			while(decimalDigits.hasMoreTokens()){
				String w = decimalDigits.nextToken();

				if(w.equals(ZERO[0]) || w.equals(ZERO[1])){
					result = result + "0";
				}
				
				for(int j = 0; j < DIGITS.length; j++){
					
					if(w.equals(DIGITS[j])){
						result = result + Integer.toString(j + 1);
					}   
				}

			}
			out = Double.parseDouble(result);
		}
		return out;
	}
}