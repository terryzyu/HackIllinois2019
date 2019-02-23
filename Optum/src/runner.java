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
import java.util.Scanner;

public class runner {

    public static void main(String[] args) throws Exception {
        // set key word string
        File keyFile = new File("C:/Users/Kangbok Lee/Documents/NetBeansProjects/HackIllinois2019/Optum/src/test.txt");
        Scanner in = new Scanner(keyFile);
        ArrayList<String> key = new ArrayList<String>();
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
        configuration.setDictionaryPath(path + "9451.dic");
        configuration.setLanguageModelPath(path + "9451.lm");

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
            System.out.println(command);
            String[] parts = command.split(" ");
            if(parts[0].equalsIgnoreCase(key.get(0))) {
                System.out.println(parts[0]);
                for(int i = 2; i < key.size(); i++){
                    System.out.println(parts[1]);
                    if(parts[1].equalsIgnoreCase(key.get(1))){
                        break;
                    }
                    if(parts[1].equalsIgnoreCase(key.get(i))){
                        String temp = "";
                        for(int j = 2; j < parts.length; j ++){
                            if(parts[j] != key.get(1)){
                                temp += parts[j];
                            }
                            else{
                                break;
                            }
                        }
                        dtabase.put(key.get(i), temp);
                        
                        // speaks the given text until queue is empty. 
                        synthesizer.speakPlainText(temp, null);          
                        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY); 
                        break;
                    }
                }
            }

        }
        // Deallocate the Synthesizer. 
        synthesizer.deallocate();

    }
}